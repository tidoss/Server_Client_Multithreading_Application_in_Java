package assignment6;


import java.util.Random;


public class TestTicketOffice {

	public static int score = 0;
	
	// Base test; tests 1 customer w/ 1 box office
	//@Test
	public void basicServerTest() {
		BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
		try {
			TicketServer.start(16789, newBatesRecitalHall);
		} 
		catch (Exception e) {  }
		TicketClient client = new TicketClient();
		client.requestTicket();

	}
	
	// Base test; tests 2 customers w/ 2 box offices
	//@Test
	public void testServerCachedHardInstance() {
		BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
		
		try {
			TicketServer.start(16790, 16791, newBatesRecitalHall);
		} 
		catch (Exception e) { 
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		TicketClient client2 = new TicketClient("localhost", "c2");
		client1.requestTicket();
		client2.requestTicket();
		
	}
	
	// Base test; tests 3 customers w/ 1 box office
	//@Test
	public void twoNonConcurrentServerTest() {
		BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
		
		try {
			TicketServer.start(16791, 16792, newBatesRecitalHall);
		} 
		catch (Exception e) {  }
		
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
	}

	// Base test; tests 3 customer w/ 2 box offices
	//@Test
	public void twoConcurrentServerTest() {
		BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
		
		try {
			TicketServer.start(16792, 16973, newBatesRecitalHall);
		} 
		catch (Exception e) { }
		
		final TicketClient c1 = new TicketClient("conc1");
		final TicketClient c2 = new TicketClient("conc2");
		final TicketClient c3 = new TicketClient("conc3");
		Thread t1 = new Thread() {
			public void run() {
				c1.requestTicket();
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				c2.requestTicket();
			}
		};
		Thread t3 = new Thread() {
			public void run() {
				c3.requestTicket();
			}
		};
		t1.start();
		t2.start();
		t3.start();
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/******************************************************************************
	* customServerTest1
	* Purpose: Tests 500 customers w/ 2 box offices                                             
	******************************************************************************/	
	
	//@Test
		public void customServerTest1() {
			BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
			
			try {
				TicketServer.start(16789, 16790, newBatesRecitalHall);
			} 
			catch (Exception e) {  }
			
			TicketClient [] c = new TicketClient[500];
			for (int i = 0; i < 500; i++){
				c[i] = new TicketClient();
			}
			for (int i = 0; i < 500; i++){
				c[i].requestTicket();
			}
		}
	
	/******************************************************************************
	* customServerTest2
	* Purpose: Tests 500 customers w/ 1 box office                                             
	******************************************************************************/	
		
	//@Test
	public void customServerTest2() {
		BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
		
		try {
			TicketServer.start(16789, newBatesRecitalHall);
		} 
		catch (Exception e) {  }
		
		TicketClient [] c = new TicketClient[500];
		for (int i = 0; i < 500; i++){
			c[i] = new TicketClient();
		}
		for (int i = 0; i < 500; i++){
			c[i].requestTicket();
		}
	}
	
	/******************************************************************************
	* customServerTest3
	* Purpose: Tests to see if the queue is kept when new ticket clients added                                                           
	******************************************************************************/
	
	//@Test
	public void customServerTest3() {
		BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
		try {
			TicketServer.start(16792, 16973, newBatesRecitalHall);
		} 
		catch (Exception e) {  }
		
		TicketClient [] a = new TicketClient[50];
		TicketClient [] b = new TicketClient[50];
		TicketClient [] c = new TicketClient[50];
		for (int i = 0; i < 50; i++){
			a[i] = new TicketClient("Jaibock");
			b[i] = new TicketClient("Fatima");
			c[i] = new TicketClient("Sreejon");
		}
		for (int i = 0; i < 50; i++){
			a[i].requestTicket();
			b[i].requestTicket();
			c[i].requestTicket();
		}
	}
	
	/******************************************************************************
	* customServerTest4                                             
	* Purpose: Creates a randomly generated test case w/ 2 box offices                                                          
	******************************************************************************/
	
		public void customServerTest4() {
			BatesRecitalHall newBatesRecitalHall = new BatesRecitalHall();
			
			try {
				TicketServer.start(16789, 16790, newBatesRecitalHall);
			} 
			catch (Exception e) {
				
			}
			
			while(true){
				Random rand = new Random();
				int  n = rand.nextInt(50) + 1;
				TicketClient [] c = new TicketClient[n];
				for (int i = 0; i < n; i++){
					c[i] = new TicketClient("random");
				}
				for (int i = 0; i <n; i++){
					c[i].requestTicket();
				}
			}		
		}
}