package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
	BufferedReader clientInput = null;
	PrintStream clientOutput = null;
	Socket soketZaKomunikaciju = null;
	String korisnickoIme;

	public ClientHandler(Socket soket) {
		soketZaKomunikaciju = soket;
	}

	public void run() {
		String poruka;
		GuestKorisnik guest = new GuestKorisnik(this);
		Karte karte = new Karte(this);
		RegistrovaniKorisnik registrovaniKorisnik = new RegistrovaniKorisnik(this);
		int brojKarata = karte.vratiBrojObicnihKarata();
		boolean kraj = false;
		try {
			clientInput = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			clientOutput = new PrintStream(soketZaKomunikaciju.getOutputStream());
			clientOutput.println("Dobrodo�li");
			while (kraj == false) {
				clientOutput.println("Izaberite neku od opcija unosom brojeva od 1 do 4: ");
				clientOutput.println("1) Proveri stanje karata.");
				clientOutput.println("2) Rezervi�i kartu.");
				clientOutput.println("3) Registruj se.");
				clientOutput.println("4) Uloguj se.");
				clientOutput.println("5) Kraj rada.");
				poruka = clientInput.readLine();
				switch (poruka) {
				case "1)": {
					karte.prikaziStanje();
					break;
				}
				case "2)": {
					int pom = guest.porucivanjeKarti();
					if (brojKarata - pom < 0) {
						clientOutput.println("Nema dovoljno karti na raspolaganju.");
					} else {
						clientOutput.println("Uspe�no ste kupili: " + pom + " karata.");
						guest.upisivanjeUFajl();
						brojKarata -= pom;
						karte.azuriranjeFajla(brojKarata);
					}
					break;
				}
				case "3)": {
					registrovaniKorisnik.registracijaKorisnika();
					clientOutput.println(registrovaniKorisnik);
					try (FileWriter fw = new FileWriter("korisnici.txt", true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter pw = new PrintWriter(bw)) {
						pw.println(registrovaniKorisnik);
						pw.println("------------------------------");
					} catch (Exception e) {
						System.out.println(
								"Gre�ka prilikom pravljenja ili dodavanja u tekstualni fajl: " + e.getMessage());
					}
					break;
				}
				case "4)": {
					registrovaniKorisnik.prijavljivanjeKorisnika();
					break;
				}
				case "5)": {
					kraj = true;
					break;
				}
				default:
					clientOutput.println("Pogre�an unos.");
					break;
				}
			}
			Server.onlineUsers.remove(this);
			soketZaKomunikaciju.close();
		} catch (IOException e) {
			Server.onlineUsers.remove(this);
		}
	}
}
