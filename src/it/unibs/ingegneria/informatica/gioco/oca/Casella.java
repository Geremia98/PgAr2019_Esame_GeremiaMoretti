package it.unibs.ingegneria.informatica.gioco.oca;

import java.util.ArrayList;

public class Casella {
	
	private int id;
	
	private ArrayList<Casella> caselleAdiacenti = new ArrayList<Casella>();
	
	private TabelloneGioco sottoMappa;
	
	private Tipo tipoCasella;

	public Casella(int id, ArrayList<Casella> caselleAdiacenti, TabelloneGioco sottoMappa, Tipo tipoCasella) {
		super();
		this.id = id;
		this.caselleAdiacenti = caselleAdiacenti;
		this.sottoMappa = sottoMappa;
		this.tipoCasella = tipoCasella;
	}
	
	public Casella () {
		
		this (0, new ArrayList<>(), null, Tipo.INIZIO);
	}
	
	public Casella (int id) {
		
		this (id, new ArrayList<>(), null, Tipo.STANDARD);
	}
	
	public void aggiungiVicina (Casella casellaDaAggiungere) {
		
		if (id == casellaDaAggiungere.getId() || casellaDaAggiungere.getId() < 0)
			return;
		
		if (!caselleAdiacenti.contains(casellaDaAggiungere)) {
			caselleAdiacenti.add(casellaDaAggiungere);
			casellaDaAggiungere.caselleAdiacenti.add(this);
			return;
		}
	}
	
	public void rimuoviVicina (Casella casellaDaRimuovere) {
		
		if (!caselleAdiacenti.contains(casellaDaRimuovere)) 
			return;
		
		caselleAdiacenti.remove(casellaDaRimuovere);
		casellaDaRimuovere.caselleAdiacenti.remove(this);
		return;
			
			
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Casella> getCaselleAdiacenti() {
		return caselleAdiacenti;
	}

	public void setCaselleAdiacenti(ArrayList<Casella> caselleAdiacenti) {
		this.caselleAdiacenti = caselleAdiacenti;
	}

	public TabelloneGioco getSottoMappa() {
		return sottoMappa;
	}

	public void setSottoMappa(TabelloneGioco sottoMappa) {
		this.sottoMappa = sottoMappa;
	}

	public Tipo getTipoCasella() {
		return tipoCasella;
	}

	public void setTipoCasella(Tipo tipoCasella) {
		this.tipoCasella = tipoCasella;
	}
	
	
	
	
	

	
	
	
	

}
