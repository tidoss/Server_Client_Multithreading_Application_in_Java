package assignment6;

import java.io.*;
import java.net.*;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public void run() {
		while (true){
		System.out.flush();									
		try {							
			Socket echoSocket = new Socket(hostname, TicketServer.PORTA);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			out.println("Ticket requested.");
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			while(in.readLine() == null){
			}
			System.out.println("Your seat has been assigned, " + threadname);
			System.out.println();
			echoSocket.close();
			break;
		} catch (Exception e) {
		}
		try {
			Socket echoSocket = new Socket(hostname, TicketServer.PORTB);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			out.println("Ticket requested.");
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			while(in.readLine() == null){
			}
			System.out.println("Your seat has been assigned, " + threadname);
			System.out.println();
			echoSocket.close();
			break;
			}
		catch (Exception e) {
			}
		}
	}
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";
	
	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name);
	}

	TicketClient() {
		this("localhost", "unnamed client");
	}

	void requestTicket() {
		tc.run();
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}