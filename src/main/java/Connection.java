package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Connection {

	public static void main(String[] args) throws IOException {

		System.out.println("Let's play a game! " + "To start multiplayer mode, enter 'multi <port>'. "
				+ "Then, to connect to another instance of Fuzzy Duck, enter 'connect <IP> <port>'.");
		Connection st = new Connection();
		st.startSender();
	}

	// list of places to write to
	List<BufferedWriter> writers = new ArrayList<BufferedWriter>();

	public void startSender() {
		writers.add(new BufferedWriter(new OutputStreamWriter(System.out)));
		(new Thread() {
			@Override
			public void run() {
				try (Scanner input = new Scanner(System.in)) {
					while (true) {
						String value = input.nextLine().toLowerCase();
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

	private synchronized void handleInput(String value) throws IOException, InterruptedException {
		String[] values = value.split("\\s+");

		if (values[0].equals("multi") && values.length == 2) {
			startServer(Integer.parseInt(values[1]));
		} else
		// check to see if we're adding a new connection
		if (values[0].equals("connect") && values.length == 3) {
			// if we are then connect
			writers.add(connectToPlayer(values[1], Integer.parseInt(values[2])));
		} else {
			// now write to everywhere we know about
			for (BufferedWriter writer : writers) {
				writer.write(value);
				writer.newLine();
				writer.flush();
				Thread.sleep(200);
			}
		}
	}

	public void startServer(int port) {
		(new Thread() {
			@Override
			public void run() {
				try(ServerSocket ss = new ServerSocket(port)) {
					while (true) {
						Socket s = ss.accept();
						//new thread for each socket
						(new Thread() {
							@Override
							public void run() {
								//reading the things
								try {
									BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

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

	public static BufferedWriter connectToPlayer(String IP, int port) {
		BufferedWriter writer = null;
		try(Socket s = new Socket(IP, port)) {
			writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			System.out.println("Connected successfully!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
}
