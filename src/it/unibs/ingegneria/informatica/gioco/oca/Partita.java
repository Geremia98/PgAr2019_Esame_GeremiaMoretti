package it.unibs.ingegneria.informatica.gioco.oca;

import java.util.ArrayList;

public class Partita {
	
	private ArrayList<Giocatore> giocatori;	
	
	public Partita(ArrayList<Giocatore> giocatori) {
		super();
		this.giocatori = giocatori;
	}

	public ArrayList<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(ArrayList<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}
	
	//metodo boolean che mi ritorna se almeno un giocatore ha vinto
	public boolean almenoUnoHaVinto () {
		
		int numGiocatori = giocatori.size();
		
		for (int i = 0; i<numGiocatori; i++) {
			
			if (giocatori.get(i).getCasellaAttuale().getId() == giocatori.get(i).getPlanciaAttuale().getListaCaselle().size()-1) 
				return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	

}
