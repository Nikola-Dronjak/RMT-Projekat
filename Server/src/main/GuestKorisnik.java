package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GuestKorisnik {

	ClientHandler ch;

	public GuestKorisnik(ClientHandler client) {
		ch = client;
	}

	private String ime;
	private String prezime;
	private String JMBG;
	private String email;
	private int brojKarata;

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBrojKarata() {
		return brojKarata;
	}

	public void setBrojKarata(int brojKarata) {
		this.brojKarata = brojKarata;
	}

	@Override
	public String toString() {
		return "Ime: " + ime + "\nPrezime: " + prezime + "\nJMBG: " + JMBG + "\nEmail: " + email + "\nBroj karata: "
				+ brojKarata;
	}

	private boolean validirajJMBG(String JMBG) {
		if (JMBG == null || JMBG.isEmpty() || JMBG.length() != 13) {
			return false;
		}
		for (int i = 0; i < JMBG.length(); i++) {
			if (!Character.isDigit(JMBG.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	private boolean validirajEmail(String email) {
		if (email == null || email.isEmpty() || !email.contains("@")) {
			return false;
		}
		return true;
	}

	private boolean validirajBrojKarata(String brojKarata) {
		if (brojKarata == null || brojKarata.isEmpty()) {
			return false;
		}
		for (int i = 0; i < brojKarata.length(); i++) {
			if (!Character.isDigit(brojKarata.charAt(i))) {
				return false;
			}
		}
		int karte = Integer.parseInt(brojKarata);
		if (karte < 0 || karte > 4) {
			return false;
		}
		return true;
	}

	private boolean daLiPostojiJMBG(String JMBG) {
		try (FileReader fr = new FileReader("rezervacije.txt"); BufferedReader br = new BufferedReader(fr)) {
			String linijaTeksta = "";
			String jmbg = "";
			while (true) {
				String pom = br.readLine();
				if (pom == null) {
					break;
				} else {
					linijaTeksta = linijaTeksta + pom + " ";
					if (linijaTeksta.contains("JMBG:")) {
						jmbg = linijaTeksta.substring(linijaTeksta.indexOf("JMBG:") + 5).trim();
						linijaTeksta = "";
					}
					if (jmbg.equals(JMBG)) {
						return true;
					}
				}
			}
		} catch (IOException e) {
		}
		return false;
	}

	private int brojPorucenihKarata(String JMBG) {
		int brojKarata = 0;
		String[] sviKorisnici = null;
		try (FileReader fr = new FileReader("rezervacije.txt"); BufferedReader br = new BufferedReader(fr)) {
			String linijaTeksta = "";
			while (true) {
				String pom = br.readLine();
				if (pom == null) {
					break;
				} else {
					linijaTeksta = linijaTeksta + pom + " ";
				}
			}
			sviKorisnici = linijaTeksta.split("------------------------------");
			for (int i = 0; i < sviKorisnici.length; i++) {
				if (sviKorisnici[i].contains(JMBG)) {
					brojKarata = Integer
							.parseInt(sviKorisnici[i].substring(sviKorisnici[i].indexOf("Broj karata:") + 12).trim());
					break;
				}
			}
		} catch (Exception e) {
		}
		return brojKarata;
	}

	public int vratiBrojObicnihKarata() {
		int brojObicnihKarata = 0;
		String pom = "";
		try (FileReader fr = new FileReader("karte.txt"); BufferedReader br = new BufferedReader(fr)) {
			String prviRed = br.readLine();
			pom = prviRed;
			brojObicnihKarata = Integer.parseInt(pom.substring(pom.indexOf("Broj obicnih karata: ") + 20).trim());
		} catch (Exception e) {
		}
		return brojObicnihKarata;
	}

	public void upisivanjeUFajl() {
		File f = new File("C:\\Users\\sinis\\Desktop\\RMT domaci\\Server\\rezervacije.txt");
		if (f.exists() && !f.isDirectory() && daLiPostojiJMBG(JMBG)) {
			azuriranjeFajla(ime, prezime, JMBG, email, brojKarata);
			azuriranjeKarata(brojKarata);
		} else {
			try (FileWriter fw = new FileWriter("rezervacije.txt", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw)) {
				pw.println("Ime: " + ime);
				pw.println("Prezime: " + prezime);
				pw.println("JMBG: " + JMBG);
				pw.println("email: " + email);
				pw.println("Broj karata: " + brojKarata);
				pw.println("------------------------------");
				azuriranjeKarata(brojKarata);
			} catch (Exception e) {
				System.out.println("Gre�ka prilikom pravljenja tekstualnog fajla: " + e.getMessage());
			}
		}
	}

	private void azuriranjeFajla(String ime, String prezime, String JMBG, String email, int brojKarata) {
		String[] sviKorisnici = null;
		File fajlZaModifikovanje = new File("rezervacije.txt");
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
			sviKorisnici = linijaTeksta.split("------------------------------");
			String izmena = "";
			String temp = "";
			for (int i = 0; i < sviKorisnici.length; i++) {
				if (sviKorisnici[i].contains("Ime: " + ime) && sviKorisnici[i].contains("Prezime: " + prezime)
						&& sviKorisnici[i].contains("email: " + email) && sviKorisnici[i].contains("JMBG: " + JMBG)) {
					temp = sviKorisnici[i];
					sviKorisnici[i] = sviKorisnici[i].replaceAll("Broj karata: " + brojPorucenihKarata(JMBG),
							"Broj karata: " + brojKarata);
					izmena = sviKorisnici[i];
					break;
				}
			}
			String noviTekst = linijaTeksta.replaceAll(temp, izmena);
			writer = new FileWriter(fajlZaModifikovanje);
			writer.write(noviTekst);
		} catch (Exception e) {
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

	public void azuriranjeKarata(int brojObicnihKarata) {
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

	public int porucivanjeKarti() throws IOException {
		int ukupnoKarata = 0;
		ch.clientOutput.println("Unesite ime:");
		do {
			String unosImena = ch.clientInput.readLine();
			if (unosImena != null && !unosImena.isEmpty()) {
				setIme(unosImena);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa imena. Molimo vas da ponovo unesete ime:");
			}
		} while (true);
		ch.clientOutput.println("Unesite prezime:");
		do {
			String unosPrezimena = ch.clientInput.readLine();
			if (unosPrezimena != null && !unosPrezimena.isEmpty()) {
				setPrezime(unosPrezimena);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa prezimena. Molimo vas da ponovo unesete prezime: ");
			}
		} while (true);
		ch.clientOutput.println("Unesite JMBG:");
		do {
			String unosJMBGa = ch.clientInput.readLine();
			if (validirajJMBG(unosJMBGa) == true) {
				setJMBG(unosJMBGa);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa JMBG-a. Molimo vas da ponovo unesete JMBG: ");
			}
		} while (true);
		ch.clientOutput.println("Unesite email:");
		do {
			String unosEmaila = ch.clientInput.readLine();
			if (validirajEmail(unosEmaila) == true) {
				setEmail(unosEmaila);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa email-a. Molimo vas da ponovo unesete email: ");
			}
		} while (true);
		ch.clientOutput.println("Koliko karata �elite da poru�ite?");
		do {
			String unosKarata = ch.clientInput.readLine();
			if (validirajBrojKarata(unosKarata)) {
				int brojKarata = Integer.parseInt(unosKarata);
				if (brojPorucenihKarata(getJMBG()) + brojKarata > 4) {
					ch.clientOutput.println(
							"Ne mo�ete poru�iti vi�e od 4 karte. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
				} else {
					setBrojKarata(brojKarata + brojPorucenihKarata(getJMBG()));
					ukupnoKarata = brojKarata;
					break;
				}
			} else {
				ch.clientOutput.println(
						"Gre�ka prilikom unosa broja karata. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
			}
		} while (true);
		return ukupnoKarata;
	}
}
