package assignment6;

import java.io.*;
import java.net.*;
	
public class TicketServer {
	static int PORTA;
	static int PORTB;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;
	
	public static void start(int portNumA, BatesRecitalHall a) throws IOException {
		PORTA = portNumA;																			
		Runnable serverThreadA = new ThreadedTicketServer(portNumA,"A", a);
		Thread t = new Thread(serverThreadA);
		t.start();
	}
	
	public static void start(int portNumA, int portNumB, BatesRecitalHall a) throws IOException {
		PORTA = portNumA;
		PORTB = portNumB;
		Runnable serverThreadA = new ThreadedTicketServer(portNumA,"A", a);
		Thread t = new Thread(serverThreadA);
		t.start();
		Runnable serverThreadB = new ThreadedTicketServer(portNumB,"B", a);
		Thread t1 = new Thread(serverThreadB);
		t1.start();
	}
	
}

class ThreadedTicketServer implements Runnable {
	
	String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;
	BatesRecitalHall passed;
	int portNum;
	String serverName;

	public ThreadedTicketServer(int port, String name, BatesRecitalHall a) {
		passed = a;
		portNum = port;
		serverName = name;
	}
	
	@Override
	public void  run() {
		while(true){																							// Both servers run the same way
			ServerSocket serverSocket;
			try {
					serverSocket = new ServerSocket(portNum);
					Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					String clientRequest = in.readLine();
					if (clientRequest.equals("Request for a ticket")){
						System.out.println("Server " + serverName + "; " + passed.bestFreeSeat());
						out.println("Done");
						try {
							Thread.sleep(100);																			// How long the user will see the message
						} 
						catch (InterruptedException e) { }
					}
					in.close();
					out.close();
					clientSocket.close();
					serverSocket.close();
				} 
			catch (IOException e) { }
		}
	}
}