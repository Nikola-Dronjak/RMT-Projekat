package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

	public static LinkedList<ClientHandler> onlineUsers = new LinkedList<>();

	public static void main(String[] args) {
		int port = 9000;
		ServerSocket serverSoket = null;
		Socket soketZaKomunikaciju = null;
		try {
			serverSoket = new ServerSocket(port);
			while (true) {
				System.out.println("Èekam na konekciju...");
				soketZaKomunikaciju = serverSoket.accept();
				System.out.println("Došlo je do konekcije!");
				ClientHandler klijent = new ClientHandler(soketZaKomunikaciju);
				onlineUsers.add(klijent);
				klijent.start();
			}
		} catch (IOException e) {
			System.out.println("Greška prilikom pokretanja servera.");
		}
	}
}
