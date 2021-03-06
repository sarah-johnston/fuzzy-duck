package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class to handle connecting to other players.
 */
public final class Connection {

	/**
	 * All 'connect' commands should have three space-separated arguments.
	 */
	private static final int CONNECT_ARG_LENGTH = 3;
	
	/**
	 * Amount of time to go to sleep for.
	 */
	private static final int SLEEPYTIME = 200;
	
	/**
	 * Main method for the Connection class.
	 * @param args arguments 
	 * @throws IOException Might throw an IOException
	 */
	public static void main(final String[] args) throws IOException {

		System.out.println("Let's play a game! "
				+ "To start multiplayer mode, enter 'multi <port>'. "
				+ "Then, to connect to another instance of Fuzzy Duck, enter "
				+ "'connect <IP> <port>'.");
		Connection st = new Connection();
		st.startSender();
	}

	/**
	 * List of places to write to (all the connected game clients). 
	 */	
	private List<BufferedWriter> writers = new ArrayList<BufferedWriter>();

	/**
	 * Start the game client (allow it to send messages out).
	 */
	public void startSender() {
		try {
			writers.add(new BufferedWriter(
					new OutputStreamWriter(System.out, "UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		(new Thread() {
			@Override
			public void run() {
				try (Scanner input = new Scanner(System.in, "UTF-8")) {
					while (true) {
						String value = 
								input.nextLine().toLowerCase(Locale.ENGLISH);
						handleInput(value);
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Handle user input.
	 * @param value User input value (String).
	 * @throws IOException Might throw an IOException
	 * @throws InterruptedException Might throw an InterruptedException.
	 */
	private synchronized void handleInput(final String value)
			throws IOException, InterruptedException {
		String[] values = value.split("\\s+");

		if (values[0].equals("multi") && values.length == 2) {
			startServer(Integer.parseInt(values[1]));
		} else
		// check to see if we're adding a new connection
		if (values[0].equals("connect") 
				&& values.length == CONNECT_ARG_LENGTH) {
			// if we are then connect
			writers.add(
					connectToPlayer(values[1], Integer.parseInt(values[2])));
		} else {
			// now write to everywhere we know about
			for (BufferedWriter writer : writers) {
				writer.write(value);
				writer.newLine();
				writer.flush();
				Thread.sleep(SLEEPYTIME);
			}
		}
	}

	/**
	 * Start the server (for receiving messages from game clients).
	 * @param port The port on which to start the server.
	 */
	public void startServer(final int port) {
		(new Thread() {
			@Override
			public void run() {
				try (ServerSocket ss = new ServerSocket(port)) {
					while (true) {
						Socket s = ss.accept();
						// new thread for each socket
						(new Thread() {
							@Override
							public void run() {
								// reading the things
								try {
									BufferedReader in = new BufferedReader(
											new InputStreamReader(
													s.getInputStream(), 
													"UTF-8"));

									String line = null;
									while ((line = in.readLine()) != null) {
										handleInput(line);
									}
								} catch (IOException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Connect to another player's game client.
	 * @param address The player's IP address.
	 * @param port The port on which to connect.
	 * @return BufferedWriter for that game client (to write to).
	 */
	public static BufferedWriter connectToPlayer(
			final String address, final int port) {
		BufferedWriter writer = null;
		try (Socket s = new Socket(address, port)) {
			writer = new BufferedWriter(
					new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
			System.out.println("Connected successfully!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
}
