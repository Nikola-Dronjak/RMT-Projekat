package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrovaniKorisnik {

	ClientHandler ch;

	public RegistrovaniKorisnik(ClientHandler client) {
		ch = client;
	}

	String korisnickoIme;
	String sifra;
	String ime;
	String prezime;
	String JMBG;
	String email;
	int brojObicnihKarata = 0;
	int brojVIPKarata = 0;

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

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

	public int getBrojObicnihKarata() {
		return brojObicnihKarata;
	}

	public void setBrojObicnihKarata(int brojObicnihKarata) {
		this.brojObicnihKarata = brojObicnihKarata;
	}

	public int getBrojVIPKarata() {
		return brojVIPKarata;
	}

	public void setBrojVIPKarata(int brojVIPKarata) {
		this.brojVIPKarata = brojVIPKarata;
	}

	@Override
	public String toString() {
		return "Korisnicko ime: " + korisnickoIme + "\n�ifra: " + sifra + "\nIme: " + ime + "\nPrezime: " + prezime
				+ "\nJMBG: " + JMBG + "\nEmail: " + email + "\nBroj obicnih karata: " + brojObicnihKarata
				+ "\nBroj VIP karata: " + brojVIPKarata;
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

	private boolean validirajKorisnickoIme(String korisnickoIme) {
		if (korisnickoIme == null || korisnickoIme.isEmpty() || daLiPostojiKorisnickoIme(korisnickoIme) == true) {
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

	private boolean validirajBrojVIPKarata(String brojKarata) {
		if (brojKarata == null || brojKarata.isEmpty()) {
			return false;
		}
		for (int i = 0; i < brojKarata.length(); i++) {
			if (!Character.isDigit(brojKarata.charAt(i))) {
				return false;
			}
		}
		int karte = Integer.parseInt(brojKarata);
		if (karte < 0 || karte > 2) {
			return false;
		}
		return true;
	}

	private boolean daLiPostojiKorisnickoIme(String korisnickoIme) {
		try (FileReader fr = new FileReader("korisnici.txt"); BufferedReader br = new BufferedReader(fr)) {
			String linijaTeksta = "";
			String username = null;
			while (true) {
				String pom = br.readLine();
				if (pom == null) {
					break;
				} else {
					linijaTeksta = linijaTeksta + pom + " ";
					if (linijaTeksta.contains("Korisnicko ime:")) {
						username = linijaTeksta.substring(linijaTeksta.indexOf("Korisnicko ime:") + 15).trim();
						linijaTeksta = "";
					}
					if (username.equals(korisnickoIme)) {
						return true;
					}
				}
			}
		} catch (IOException e) {
//			ch.clientOutput.println("Gre�ka prilikom �itanja iz fajla: " + e.getMessage());
		}
		return false;
	}

	private boolean daLiPostojiSifra(String sifra) {
		try (FileReader fr = new FileReader("korisnici.txt"); BufferedReader br = new BufferedReader(fr)) {
			String linijaTeksta = "";
			String password = null;
			while (true) {
				String pom = br.readLine();
				if (pom == null) {
					break;
				} else {
					linijaTeksta = linijaTeksta + pom + " ";
					if (linijaTeksta.contains("�ifra:")) {
						password = linijaTeksta.substring(linijaTeksta.indexOf("�ifra:") + 6).trim();
						linijaTeksta = "";
					}
					if (password != null && password.equals(sifra)) {
						return true;
					}
				}
			}
		} catch (IOException e) {
//			ch.clientOutput.println("Gre�ka prilikom �itanja iz fajla: " + e.getMessage());
		}
		return false;
	}

	private int brojPorucenihKarata(String username, String password) {
		int brojKarata = 0;
		String[] sviKorisnici = null;
		try (FileReader fr = new FileReader("korisnici.txt"); BufferedReader br = new BufferedReader(fr)) {
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
				if (sviKorisnici[i].contains(username) && sviKorisnici[i].contains(password)) {
					brojKarata = Integer
							.parseInt(sviKorisnici[i].substring(sviKorisnici[i].indexOf("Broj obicnih karata:") + 20,
									sviKorisnici[i].indexOf("Broj VIP karata:")).trim());
					break;
				}
			}
		} catch (Exception e) {
			ch.clientOutput.println("Gre�ka prilikom �itanja iz fajla: " + e.getMessage());
		}
		return brojKarata;
	}

	private int brojPorucenihVIPKarata(String username, String password) {
		int brojKarata = 0;
		String[] sviKorisnici = null;
		try (FileReader fr = new FileReader("korisnici.txt"); BufferedReader br = new BufferedReader(fr)) {
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
				if (sviKorisnici[i].contains(username) && sviKorisnici[i].contains(password)) {
					brojKarata = Integer.parseInt(
							sviKorisnici[i].substring(sviKorisnici[i].indexOf("Broj VIP karata: ") + 16).trim());
					break;
				}
			}
		} catch (Exception e) {
			ch.clientOutput.println("Gre�ka prilikom �itanja iz fajla: " + e.getMessage());
		}
		return brojKarata;
	}

	private int vratiBrojObicnihKarata() {
		int brojObicnihKarata = 0;
		String pom = "";
		try (FileReader fr = new FileReader("karte.txt"); BufferedReader br = new BufferedReader(fr)) {
			String prviRed = br.readLine();
			pom = prviRed;
			brojObicnihKarata = Integer.parseInt(pom.substring(pom.indexOf("Broj obicnih karata: ") + 20).trim());
		} catch (Exception e) {
			ch.clientOutput.println("Gre�ka prilikom rada sa fajlovima: " + e.getMessage());
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

	public void registracijaKorisnika() throws IOException {
		ch.clientOutput.println("Unesite korsni�ko ime: ");
		do {
			String unosKorisnickogImena = ch.clientInput.readLine();
			if (validirajKorisnickoIme(unosKorisnickogImena) == true) {
				setKorisnickoIme(unosKorisnickogImena);
				break;
			} else {
				ch.clientOutput.println(
						"Gre�ka prilikom unosa korisni�kog imena. Molimo vas da ponovo unesete korisni�ko ime: ");
			}
		} while (true);
		ch.clientOutput.println("Unesite �ifru: ");
		do {
			String unosSifre = ch.clientInput.readLine();
			if (unosSifre != null && !unosSifre.isEmpty()) {
				setSifra(unosSifre);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa �ifre. Molimo vas da ponovo unesete �ifru: ");
			}
		} while (true);
		ch.clientOutput.println("Unesite ime: ");
		do {
			String unosImena = ch.clientInput.readLine();
			if (unosImena != null && !unosImena.isEmpty()) {
				setIme(unosImena);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa imena. Molimo vas da ponovo unesete ime:");
			}
		} while (true);
		ch.clientOutput.println("Unesite prezime: ");
		do {
			String unosPrezimena = ch.clientInput.readLine();
			if (unosPrezimena != null && !unosPrezimena.isEmpty()) {
				setPrezime(unosPrezimena);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa prezimena. Molimo vas da ponovo unesete prezime: ");
			}
		} while (true);
		ch.clientOutput.println("Unesite JMBG: ");
		do {
			String unosJMBGa = ch.clientInput.readLine();
			if (validirajJMBG(unosJMBGa) == true) {
				setJMBG(unosJMBGa);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa JMBG-a. Molimo vas da ponovo unesete JMBG: ");
			}
		} while (true);
		ch.clientOutput.println("Unesite email: ");
		do {
			String unosEmaila = ch.clientInput.readLine();
			if (validirajEmail(unosEmaila) == true) {
				setEmail(unosEmaila);
				break;
			} else {
				ch.clientOutput.println("Gre�ka prilikom unosa email-a. Molimo vas da ponovo unesete email: ");
			}
		} while (true);
		ch.clientOutput.println("Uspe�no ste se registrovali.");
	}

	public void prijavljivanjeKorisnika() throws IOException {
		do {
			ch.clientOutput.println("Unesite va�e korisni�ko ime: ");
			String korisnickoIme = ch.clientInput.readLine();
			ch.clientOutput.println("Unesite va�u �ifru: ");
			String sifra = ch.clientInput.readLine();
			if (daLiPostojiKorisnickoIme(korisnickoIme) == true && daLiPostojiSifra(sifra) == true) {
				ch.clientOutput.println("Uspe�no ste se prijavili.");
				boolean kraj = false;
				while (kraj == false) {
					ch.clientOutput.println("Izaberite vrstu karata koju �elite da poru�ite: ");
					ch.clientOutput.println("a) Rezervi�i obu�ne karte.");
					ch.clientOutput.println("b) Rezervi�i VIP karte.");
					ch.clientOutput.println("c) Otka�i rezervacviju obi�nih karata.");
					ch.clientOutput.println("d) Otka�i rezervacviju VIP karata.");
					ch.clientOutput.println("e) Kraj rada.");
					String izbor = ch.clientInput.readLine();
					switch (izbor) {
					case "a)": {
						int pom = vratiBrojObicnihKarata();
						int pom2 = rezervisanjeObicnihKarata(korisnickoIme, sifra);
						if (pom - pom2 < 0) {
							ch.clientOutput.println("Nema dovoljno karata na raspolaganju.");
						} else {
							if (brojPorucenihKarata(korisnickoIme, sifra) < 4) {
								azuriranjeFajla(korisnickoIme, sifra, pom2 + brojPorucenihKarata(korisnickoIme, sifra));
								pom -= pom2;
								azuriranjeKarata(pom);
								ch.clientOutput.println("Uspe�no ste poru�ili: " + pom2 + " obi�ne karte.");
							} else {
								ch.clientOutput.println("Dozvoljeno je poru�iti maksimalno 4 karte.");
								break;
							}
						}
						break;
					}
					case "b)": {
						int pom = vratiBrojVIPKarata();
						int pom2 = rezervisanjeVIPKarata(korisnickoIme, sifra);
						if (pom - pom2 < 0) {
							ch.clientOutput.println("Nema dovoljno karata na raspolaganju.");
						} else {
							if (brojPorucenihVIPKarata(korisnickoIme, sifra) < 2) {
								azuriranjeFajlaVIP(korisnickoIme, sifra,
										pom2 + brojPorucenihVIPKarata(korisnickoIme, sifra));
								pom -= pom2;
								azuriranjeKarataVIP(pom);
								ch.clientOutput.println("Uspe�no ste poru�ili: " + pom2 + " VIP karte.");
							} else {
								ch.clientOutput.println("Dozvoljeno je poru�iti maksimalno 4 karte.");
								break;
							}
						}
						break;
					}
					case "c)": {
						int pom = vratiBrojObicnihKarata();
						int pom2 = otkazivanjeObicnihKarata(korisnickoIme, sifra);
						azuriranjeFajla(korisnickoIme, sifra, brojPorucenihKarata(korisnickoIme, sifra) - pom2);
						pom += pom2;
						azuriranjeKarata(pom);
						ch.clientOutput.println("Uspe�no ste otkazali: " + pom2 + " obi�ne karte.");
						break;
					}
					case "d)": {
						int pom = vratiBrojVIPKarata();
						int pom2 = otkazivanjeVIPKarata(korisnickoIme, sifra);
						azuriranjeFajlaVIP(korisnickoIme, sifra, brojPorucenihVIPKarata(korisnickoIme, sifra) - pom2);
						pom += pom2;
						azuriranjeKarataVIP(pom);
						ch.clientOutput.println("Uspe�no ste otkazali: " + pom2 + " VIP karte.");
						break;
					}
					case "e)": {
						kraj = true;
						break;
					}
					default:
						ch.clientOutput.println("Pogre�an unos.");
						break;
					}
				}
				break;
			} else {
				ch.clientOutput.println("Ne postoji korisnik sa unetim korisni�kim imenom i �ifrom. Poku�ajte ponovo.");
			}
		} while (true);
	}

	private int rezervisanjeObicnihKarata(String username, String password) throws IOException {
		int brojKarata = 0;
		ch.clientOutput.println("Koliko karata �elite da poru�ite?");
		do {
			String unosKarata = ch.clientInput.readLine();
			if (validirajBrojKarata(unosKarata)) {
				brojKarata = Integer.parseInt(unosKarata);
				if (brojPorucenihKarata(username, password) + brojKarata > 4) {
					ch.clientOutput.println(
							"Ne mo�ete poru�iti vi�e od 4 karte. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
				} else {
					setBrojObicnihKarata(brojKarata);
					break;
				}
			} else {
				ch.clientOutput.println(
						"Gre�ka prilikom unosa broja karata. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
			}
		} while (true);
		return brojKarata;
	}

	private int rezervisanjeVIPKarata(String username, String password) throws IOException {
		int brojKarata = 0;
		ch.clientOutput.println("Koliko karata �elite da poru�ite?");
		do {
			String unosKarata = ch.clientInput.readLine();
			if (validirajBrojVIPKarata(unosKarata)) {
				brojKarata = Integer.parseInt(unosKarata);
				if (brojPorucenihVIPKarata(username, password) + brojKarata > 2) {
					ch.clientOutput.println(
							"Ne mo�ete poru�iti vi�e od 2 karte. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
				} else {
					setBrojObicnihKarata(brojKarata);
					break;
				}
			} else {
				ch.clientOutput.println(
						"Gre�ka prilikom unosa broja karata. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
			}
		} while (true);
		return brojKarata;
	}

	private int otkazivanjeObicnihKarata(String username, String password) throws IOException {
		int brojKarata = 0;
		ch.clientOutput.println("Koliko karata �elite da otka�ete?");
		do {
			String unosKarata = ch.clientInput.readLine();
			if (validirajBrojKarata(unosKarata)) {
				brojKarata = Integer.parseInt(unosKarata);
				if (brojKarata > brojPorucenihKarata(username, password)) {
					ch.clientOutput.println(
							"Mo�ete otkazati samo manje ili onoliko karata koliko ste poru�ili. Molimo vas da ponovo unesete broj karata koji �elite da otka�ete: ");
				} else {
					break;
				}
			} else {
				ch.clientOutput.println(
						"Gre�ka prilikom unosa broja karata. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
			}
		} while (true);
		return brojKarata;
	}

	private int otkazivanjeVIPKarata(String username, String password) throws IOException {
		int brojKarata = 0;
		ch.clientOutput.println("Koliko karata �elite da otka�ete?");
		do {
			String unosKarata = ch.clientInput.readLine();
			if (validirajBrojVIPKarata(unosKarata)) {
				brojKarata = Integer.parseInt(unosKarata);
				if (brojKarata > brojPorucenihVIPKarata(username, password)) {
					ch.clientOutput.println(
							"Mo�ete otkazati samo manje ili onoliko karata koliko ste poru�ili. Molimo vas da ponovo unesete broj karata koji �elite da otka�ete: ");
				} else {
					break;
				}
			} else {
				ch.clientOutput.println(
						"Gre�ka prilikom unosa broja karata. Molimo vas da ponovo unesete broj karata koji �elite da poru�ite: ");
			}
		} while (true);
		return brojKarata;
	}

	private void azuriranjeFajla(String username, String password, int brojObicnihKarata) {
		String[] sviKorisnici = null;
		File fajlZaModifikovanje = new File("korisnici.txt");
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
				if (sviKorisnici[i].contains("Korisnicko ime: " + username)
						&& sviKorisnici[i].contains("�ifra: " + password)) {
					temp = sviKorisnici[i];
					sviKorisnici[i] = sviKorisnici[i].replaceAll(
							"Broj obicnih karata: " + brojPorucenihKarata(username, password),
							"Broj obicnih karata: " + brojObicnihKarata);
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

	private void azuriranjeFajlaVIP(String username, String password, int brojObicnihKarata) {
		String[] sviKorisnici = null;
		File fajlZaModifikovanje = new File("korisnici.txt");
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
				if (sviKorisnici[i].contains("Korisnicko ime: " + username)
						&& sviKorisnici[i].contains("�ifra: " + password)) {
					temp = sviKorisnici[i];
					sviKorisnici[i] = sviKorisnici[i].replaceAll(
							"Broj VIP karata: " + brojPorucenihVIPKarata(username, password),
							"Broj VIP karata: " + brojObicnihKarata);
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

	private void azuriranjeKarata(int brojObicnihKarata) {
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

	private void azuriranjeKarataVIP(int brojVIPKarata) {
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
				if (redovi[i].contains("Broj VIP karata: ")) {
					temp = redovi[i];
					redovi[i] = redovi[i].replaceAll("Broj VIP karata: " + vratiBrojVIPKarata(),
							"Broj VIP karata: " + brojVIPKarata);
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
