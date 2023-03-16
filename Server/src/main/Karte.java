package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Karte {

	ClientHandler ch;

	public Karte(ClientHandler client) {
		ch = client;
	}

	int brojObicnihKarata = 20;
	int brojVIPKarata = 5;

	public int vratiBrojObicnihKarata() {
		int brojObicnihKarata = 0;
		String pom = "";
		try (FileReader fr = new FileReader("karte.txt"); BufferedReader br = new BufferedReader(fr)) {
			String prviRed = br.readLine();
			pom = prviRed;
			brojObicnihKarata = Integer.parseInt(pom.substring(pom.indexOf("Broj obicnih karata: ") + 20).trim());
		} catch (Exception e) {
//			ch.clientOutput.println("Gre�ka prilikom rada sa fajlovima: " + e.getMessage());
		}
		return brojObicnihKarata;
	}

	private int vratiBrojVIPKarata() {
		int brojVIPKarata = 0;
		try (FileReader fr = new FileReader("karte.txt"); BufferedReader br = new BufferedReader(fr)) {
			String linijaTeksta = "";
			while (true) {
				String pom = br.readLine();
				if (pom == null) {
					break;
				} else {
					linijaTeksta = linijaTeksta + pom + " ";
				}
			}
			brojVIPKarata = Integer
					.parseInt(linijaTeksta.substring(linijaTeksta.indexOf("Broj VIP karata: ") + 16).trim());
		} catch (Exception e) {
			ch.clientOutput.println("Gre�ka prilikom rada sa fajlovima: " + e.getMessage());
		}
		return brojVIPKarata;
	}

	public void prikaziStanje() {
		File f = new File("C:\\Users\\sinis\\Desktop\\RMT domaci\\Server\\karte.txt");
		if (f.exists() && !f.isDirectory()) {
			ch.clientOutput.println("Broj raspolo�ivih obi�nih karata: " + vratiBrojObicnihKarata());
			ch.clientOutput.println("Broj raspolo�ivih VIP karata: " + vratiBrojVIPKarata());
		} else {
			try (FileWriter fw = new FileWriter("karte.txt", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw)) {
				pw.println("Broj obicnih karata: " + brojObicnihKarata);
				pw.println("Broj VIP karata: " + brojVIPKarata);
				ch.clientOutput.println("Broj raspolo�ivih obi�nih karata: " + brojObicnihKarata);
				ch.clientOutput.println("Broj raspolo�ivih VIP karata: " + brojVIPKarata);
			} catch (Exception e) {
				ch.clientOutput.println("Gre�ka prilikom rada sa fajlovima: " + e.getMessage());
			}
		}
	}

	public void azuriranjeFajla(int brojObicnihKarata) {
		String[] redovi = null;
		File fajlZaModifikovanje = new File("karte.txt");
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fajlZaModifikovanje));
			String linijaTeksta = "";
			while (true) {
				String pom = reader.readLine();
				if (pom == null) {
					break;
				} else {
					linijaTeksta = linijaTeksta + pom + System.lineSeparator();
				}
			}
			redovi = linijaTeksta.split("\n");
			String izmena = "";
			String temp = "";
			for (int i = 0; i < redovi.length; i++) {
				if (redovi[i].contains("Broj obicnih karata: ")) {
					temp = redovi[i];
					redovi[i] = redovi[i].replaceAll("Broj obicnih karata: " + vratiBrojObicnihKarata(),
							"Broj obicnih karata: " + brojObicnihKarata);
					izmena = redovi[i];
					break;
				}
			}
			String noviTekst = linijaTeksta.replaceAll(temp, izmena);
			writer = new FileWriter(fajlZaModifikovanje);
			writer.write(noviTekst);
		} catch (

		Exception e) {
			ch.clientOutput.println("Gre�ka prilikom �itanja iz fajla: " + e.getMessage());
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (Exception e) {
				ch.clientOutput.println("Gre�ka prilikom �itanja iz fajla: " + e.getMessage());
			}
		}
	}
}
