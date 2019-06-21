package it.unibs.ingegneria.informatica.gioco.oca;

import it.unibs.fp.mylib.InputDati;
import java.util.ArrayList;

public class Main {
	
	private static final String MESSAGGIO_POZZO_2 = "Per poter uscire dovrai prima terminare la sottoMappa ... ";
	private static final String MESSAGGIO_POZZO = "La tua pedina è incorsa in una casella Pozzo, ed è stata catapultata in una sottoMappa ...";
	//messaggi vari che utilizzo per condurre il gioco
	private static final String MESSAGGIO_TORNA_ALLA_PARTENZA = "Che peccato! Hai beccato una casella Speciale, che ti ha riportato alla partenza!";
	private static final String MESSAGGIO_BIVIO2 = "Essendo il tuo tiro <5, ha preso il percprsp meno vantaggioso ... (sad life) ...";
	private static final String MESSAGGIO_BIVIO1 = "Essendo il tuo tiro >= 5, ha preso il percorso più vantaggioso per vincere ...";
	private static final String MESSAGGIO_PEDINA = "La tua pedina ha incontrato ";
	private static final String MESSAGGIO_NOME = "\nInserisci il tuo nome : ";
	private static final String MESSAGGIO_PRIMO = "\nComplimenti, sei in testa!";
	private static final String MESSAGGIO_LANCIO_2 = "\nComplimenti! Hai fatto un ottimo lancio: è uscito ";
	private static final String MESSAGGIO_LANCIO_1 = "\nHai fatto ";
	private static final String MESSAGGIO_DADO = " è il tuo turno! Premi invio per lanciare il dado: ";
	private static final String MESSAGGIO_INIZIO_PARTITA = "\nLa partita è iniziata!\nBuon divertimento";
	private static final String MESSAGGIO_GIOCATORI_2 = "\nOttimo!\nOra inserisci il/i nomi del giocatore/i \nInseriscili già nell'ordine in cui giocherete la partita ...";
	private static final String MESSAGGIO_CASELLE = "La plancia principale è formata da 150 caselle ...";
	private static final String MESSAGGIO_BENVENUTO = "Ciao! Benvenuto nel gioco dell'oca Evolution";
	private static final String MESSAGGIO_GIOCATORI = "In quanti siete a giocare? ";
	private static final int NUMERO_CASELLE = 150;

	public static void messaggioSaluto () {
		
		System.out.println(MESSAGGIO_BENVENUTO);
		return;
		
	}
	
	//metodo che mi crea una partita a partire dal numero di caselle
	//io ho scelto di usarne 150
	public static Partita settaPartita (int numeroCaselle) {
		
		int numeroGiocatori = InputDati.leggiIntero("\n" + MESSAGGIO_GIOCATORI , 1, 4);
		System.out.println(MESSAGGIO_GIOCATORI_2);
		ArrayList<Giocatore> arrayGiocatori= new ArrayList<>();
		TabelloneGioco tabellonePartita = new TabelloneGioco(numeroCaselle);
		
		if (numeroGiocatori == 1) {
			
			String nome = InputDati.leggiStringaNonVuota(MESSAGGIO_NOME);
			Giocatore giocatore = new Giocatore(nome, tabellonePartita);
			arrayGiocatori.add(giocatore);
			
			Partita partita = new Partita(arrayGiocatori);

			return partita;
			
		}
		
		for (int i=0; i<numeroGiocatori; i++) {
			
			String nomeGiocatore = InputDati.leggiStringaNonVuota("\nInserisci il nome del " + (i+1) + "° giocatore: ");
			Giocatore nuovoGiocatore = new Giocatore(nomeGiocatore, tabellonePartita);
			arrayGiocatori.add(nuovoGiocatore);
		}
		
		return new Partita(arrayGiocatori);
	}
	
	
	//messaggio per lancio. Si complimenta se ha effettuato un buon lancio
	public static void messaggioLancio (int tiro) {
		
		switch (tiro) {
		
		case 1: case 2: case 3: case 4:
			System.out.println(MESSAGGIO_LANCIO_1 + tiro);
			break;
			
		case 5: case 6:
			System.out.println(MESSAGGIO_LANCIO_2 + tiro);
			break;
		
		}
	}
	
	//metodo che stampa a video la situazione postTiro
	//stampa la casella attuale, di quante caselle è avanzato, stampa se è in testa alla classifica
	//anche se è a parimerito con un altro giocatore
	public static void stampaSituazionePostTiro (int tiro, Partita partita, Giocatore giocatoreCheHaTirato) {
		
		System.out.println("La tua pedina è avanzata di " + tiro + " caselle");
		
		if (giocatoreCheHaTirato.getCasellaAttuale().getId() == 0) {
			
			System.out.println(MESSAGGIO_TORNA_ALLA_PARTENZA);
		}
		System.out.println("Ti trovi sulla casella numero " + giocatoreCheHaTirato.getCasellaAttuale().getId());
		
		int primo=0;
		for (Giocatore giocatore : partita.getGiocatori()) {
			
			if (giocatoreCheHaTirato.getCasellaAttuale().getId() >= giocatore.getCasellaAttuale().getId())
				primo++;
		}
		
		if (primo == partita.getGiocatori().size())
			System.out.println(MESSAGGIO_PRIMO);
	}

	//main
	public static void main(String[] args) {
		
		messaggioSaluto();
		System.out.println("\n" +MESSAGGIO_CASELLE);
		
		Partita partita = settaPartita(NUMERO_CASELLE);
		System.out.println(MESSAGGIO_INIZIO_PARTITA);
		
		
		//continuo a far tirare i giocatori fino a che uno raggiunge la casella fine 
		while (!partita.almenoUnoHaVinto()) {
			
			for (Giocatore giocatore : partita.getGiocatori()) {
				
				InputDati.leggiStringa("\n" + giocatore.getNome() + MESSAGGIO_DADO);
				
				int tiro = giocatore.tiraDado();
				messaggioLancio(tiro);
				TabelloneGioco  iniziale = giocatore.getPlanciaAttuale();
				
				//un vettore che mi tiene traccia di cosa è successo nel metodo avanza, in particolare:
				//index = 0 -> bivi che ha oltrepassato
				//index = 1 -> Scale (casella Speciale)
				
				ArrayList<Integer> infoAvanzamento = giocatore.avanza(tiro);
				
				//switch per visualizzare a video la scelta fatta dalla pedina del giocatore
				//la posizione 0 è relativa ai bivi
				switch (infoAvanzamento.get(0)) {
				
				case 0: 
					stampaSituazionePostTiro(tiro, partita, giocatore);
					break;
					
				case -1:
					System.out.println("La partita è finita!\n" + giocatore.getNome() + "è arrivato alla fine dell'oca.\nNumero mosse: " + giocatore.getMosseEffettuate());
					
				default:
					System.out.println(MESSAGGIO_PEDINA + infoAvanzamento.get(0) + " bivio/i");
					
					if (tiro>=5) {
						System.out.println(MESSAGGIO_BIVIO1);
						stampaSituazionePostTiro(tiro, partita, giocatore);
					} else {
						System.out.println(MESSAGGIO_BIVIO2);
						stampaSituazionePostTiro(tiro, partita, giocatore);
					}
					break;
				}
				
				if (infoAvanzamento.get(1) != 0) {
					
					System.out.println("La tua pedina ha attivato una casella Scala, ed è avanzata di " + infoAvanzamento.get(1) + " caselle/a");
				}
				
				if (iniziale != giocatore.getPlanciaAttuale()) {
					
					System.out.println(MESSAGGIO_POZZO);
					System.out.println(MESSAGGIO_POZZO_2);
				}
				
				//alla fine di ogni ciclo resetto il vettore, in modo che alla prossima giocata sia come nuovo
				infoAvanzamento.clear();
			}
			
		}  //fine while
		

	}

}
