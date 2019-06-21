package it.unibs.ingegneria.informatica.gioco.oca;

import java.util.ArrayList;

import it.unibs.fp.mylib.EstrazioniCasuali;

public class Giocatore {
	
	private static final int NUMERO_CASELLE_POZZO = 10;

	//nome giocatore
	private String nome;
	
	//tiene traccia del numero di mosse effettuate
	private int mosseEffettuate;
	
	//casella attuale in cui stazione la pedina del giocatore
	private Casella casellaAttuale;

	//la plancia attuale del giocatore
	//variabile che viene usata nel caso implementi la casella Speciale Pozzo
	private TabelloneGioco planciaAttuale;

	//costruttore completo
	public Giocatore(String nome, int mosseEffettuate, Casella casellaAttuale, TabelloneGioco planciaAttuale) {
		super();
		this.nome = nome;
		this.mosseEffettuate = mosseEffettuate;
		this.casellaAttuale = casellaAttuale;
		this.planciaAttuale = planciaAttuale;
	}
	
	//costruttore a partire solo dal nome e dalla tabella principale
	public Giocatore (String nome, TabelloneGioco planciaAttuale) {
		
		this (nome, 0, planciaAttuale.getListaCaselle().get(0), planciaAttuale);
	}

	//vari getters e setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getMosseEffettuate() {
		return mosseEffettuate;
	}

	public void setMosseEffettuate(int mosseEffettuate) {
		this.mosseEffettuate = mosseEffettuate;
	}

	public Casella getCasellaAttuale() {
		return casellaAttuale;
	}

	public void setCasellaAttuale(Casella casellaAttuale) {
		this.casellaAttuale = casellaAttuale;
	}

	public TabelloneGioco getPlanciaAttuale() {
		return planciaAttuale;
	}

	public void setPlanciaAttuale(TabelloneGioco planciaAttuale) {
		this.planciaAttuale = planciaAttuale;
	}

	//semplice estrazione casuale
	public int tiraDado () {
		
		int valoreDado = EstrazioniCasuali.estraiIntero(1, 6);
		return valoreDado;
	}
	
	
	//questo metodo varia a seconda della casella che la pedina del giocatore incontra
	//ritorna un imtero, per tenere traccia di tutte le caselle speciali, modificando il vettore arrayInfo
	//in questo modo tengo conto di tutte le attività fatte dalla mia pedina
	public ArrayList<Integer> avanza (int valoreDelDado) {
		
		ArrayList<Integer> arrayInfo = new ArrayList<>();
		int bivio = 0;
		int scala = 0;
		arrayInfo.add(bivio);
		arrayInfo.add(scala);
	
		mosseEffettuate++;
		
		for (int i=0; i<valoreDelDado; i++) {
			
			//se è la casella inizio avanzerà sull'unica casella possibile
			if (casellaAttuale.getTipoCasella() == Tipo.INIZIO) {
				
				Casella casella1 = casellaAttuale.getCaselleAdiacenti().get(0);
				setCasellaAttuale(casella1);
				
			// se è una casella standard avanzerà sulla casella con id maggiore della sua
			} else if (casellaAttuale.getTipoCasella()== Tipo.STANDARD) {
				
				Casella casella1 = casellaAttuale.getCaselleAdiacenti().get(0);
				Casella casella2 = casellaAttuale.getCaselleAdiacenti().get(1);
				
				if (casella1.getId() > casella2.getId()) {
					setCasellaAttuale(casella1);
				} else
				setCasellaAttuale(casella2);
				
			//se è una casella di tipo bivio, la direzione che prenderà la pedina dipende dal lancio fatto
			} else if (casellaAttuale.getTipoCasella() == Tipo.BIVIO) {
				
				Casella casella2 = casellaAttuale.getCaselleAdiacenti().get(1);
				Casella casella3 = casellaAttuale.getCaselleAdiacenti().get(2);
				bivio++;
				
				Casella maggiore;
				Casella minore;
				
				if (casella2.getId() > casella3.getId()) {
					maggiore = casella2;
					minore = casella3;
				} else {
					maggiore = casella3;
					minore = casella2;
					}
				
				if (scegliDirezione(valoreDelDado) > 0) {
					setCasellaAttuale(maggiore);
				} else
					setCasellaAttuale(minore);
				
				//se è una casella di tipo fine ritorna -1, ed esco dal ciclo for, perchè non posso andare ulteriormente avanti
			} else if (casellaAttuale.getTipoCasella() == Tipo.FINE && planciaAttuale.getListaCaselle().get(0).getId() == 0) {
						bivio=  -1;
						arrayInfo.set(0, bivio);
						return arrayInfo;
					
			} else if (casellaAttuale.getTipoCasella() == Tipo.FINE && planciaAttuale.getListaCaselle().get(0).getId() != 0) {
				
				setCasellaAttuale(planciaAttuale.getListaCaselle().get(0));
				
				//se è una casella di tipo Partenza, mi setta la posizione attuale alla partenza,ma solo se la pedina
				//ci finisce sopra come ultima mossa
			} else if (casellaAttuale.getTipoCasella() == Tipo.SPECIALE_PARTENZA) {
				
				if (i ==valoreDelDado-1) {
				Casella casellaPartenza = planciaAttuale.getListaCaselle().get(0);
				setCasellaAttuale(casellaPartenza);
				} else {
					
					Casella casella1 = casellaAttuale.getCaselleAdiacenti().get(0);
					Casella casella2 = casellaAttuale.getCaselleAdiacenti().get(1);
					
					if (casella1.getId() > casella2.getId()) {
						setCasellaAttuale(casella1);
					} else
					setCasellaAttuale(casella2);
				}
				
				//se è una casella di tipo Scala, viene portato avanti di un numero casuale, compreso tra 1 e 5
				//essendo a tutti gli effetti come un tiro di dado, richiamo la stessa funzione avanza();
				//non funge
			} else if (casellaAttuale.getTipoCasella() == Tipo.SCALA) {
				
				int numeroCasuale = EstrazioniCasuali.estraiIntero(1, 5);
				scala = numeroCasuale;
				
				avanza(numeroCasuale);
				
				//questa parte è l'ultima che ho iniziato, implementa codice che gestisce la casella Pozzo
			} if (casellaAttuale.getTipoCasella() == Tipo.POZZO) {
				
				TabelloneGioco sottoMappa = new TabelloneGioco(casellaAttuale, NUMERO_CASELLE_POZZO);
				casellaAttuale = sottoMappa.getListaCaselle().get(0);
				planciaAttuale = sottoMappa;
				
			}
				
		}
		
		arrayInfo.set(0, bivio);
		arrayInfo.set(1, scala);
		return arrayInfo;
		
	}
	
	public int scegliDirezione (int valoreDelDado) {
		
		if (valoreDelDado >4) {
			return 1;
		} else return -1;
		
		
	}
	
	
	
	
	
	

}
