package it.unibs.ingegneria.informatica.gioco.oca;

import java.util.ArrayList;

public class TabelloneGioco {
	
	private ArrayList<Casella> listaCaselle = new ArrayList<>();

	public TabelloneGioco (ArrayList<Casella> listaCaselle) {
		
		super();
		this.listaCaselle = listaCaselle;
	}
	
	public TabelloneGioco (int numeroCaselle) {
		
		creaTabellone(numeroCaselle);
	}
	
	public TabelloneGioco (Casella casellaPozzo, int numeroCaselle) {
		
		//come Casella iniziale metto la casella Pozzo, così una volta finita la sottoMappa avrò la traccia di quest'utlima
		creaPozzo(numeroCaselle, casellaPozzo);
		
	}

	public ArrayList<Casella> getListaCaselle() {
		return listaCaselle;
	}

	public void setListaCaselle(ArrayList<Casella> listaCaselle) {
		this.listaCaselle = listaCaselle;
	}
	
	//il tabellone base è formato da 150 caselle, alcuni bivi, e alcune caselle speciali
	public void creaTabellone (int numeroCaselle) {
		
		creaBase(numeroCaselle);
		
		Casella casella14 = listaCaselle.get(14);
		Casella casella22 = listaCaselle.get(22);
		Casella casella21 = listaCaselle.get(21);
		Casella casella31 = listaCaselle.get(31);
		
		casella14.setTipoCasella(Tipo.BIVIO);
		
		casella14.aggiungiVicina(casella22);
		casella21.aggiungiVicina(casella31);
		casella21.rimuoviVicina(casella22);
	
		Casella casella48 = listaCaselle.get(48);
		Casella casella39 = listaCaselle.get(39);
		Casella casella54 = listaCaselle.get(54);
		Casella casella70 = listaCaselle.get(70);
		Casella casella65 = listaCaselle.get(65);
		Casella casella69 = listaCaselle.get(69);
		Casella casella78 = listaCaselle.get(78);
		Casella casella47 = listaCaselle.get(47);
		
		Casella casella55 = listaCaselle.get(55);
		Casella casella79 = listaCaselle.get(79);
		Casella casella96 = listaCaselle.get(96);
		
		Casella casella95 = listaCaselle.get(95);
		Casella casella124 = listaCaselle.get(124);
	
		
		casella48.aggiungiVicina(casella39);
		casella47.aggiungiVicina(casella54);
		casella47.rimuoviVicina(casella48);
		
		casella70.aggiungiVicina(casella65);
		casella69.aggiungiVicina(casella78);
		casella69.rimuoviVicina(casella70);
		
		casella54.aggiungiVicina(casella79);
		casella31.aggiungiVicina(casella55);
		casella54.rimuoviVicina(casella55);
		
		casella79.aggiungiVicina(casella96);
		casella95.aggiungiVicina(casella124);
		casella95.rimuoviVicina(casella96);
		
		casella31.setTipoCasella(Tipo.BIVIO);
		casella39.setTipoCasella(Tipo.BIVIO);
		casella79.setTipoCasella(Tipo.BIVIO);
		casella65.setTipoCasella(Tipo.BIVIO);
//		
//		listaCaselle.get(3).setTipoCasella(Tipo.SCALA);                                      qui alcune caselle speciali che ho provato ad aggiungere ...
		listaCaselle.get(13).setTipoCasella(Tipo.SPECIALE_PARTENZA);                         //sono commentate perchè manderebbero in crash il programma
		listaCaselle.get(130).setTipoCasella(Tipo.SPECIALE_PARTENZA);
//		listaCaselle.get(10).setTipoCasella(Tipo.POZZO);
		
	}

	/**
	 * @param numeroCaselle
	 */
	private void creaBase (int numeroCaselle) {
		
		for (int i=0; i<numeroCaselle; i++) {
			
			Casella nuovaCasella = new Casella(i);
			
			if (i==0)
				nuovaCasella.setTipoCasella(Tipo.INIZIO);
			
			if (i==numeroCaselle -1)
				nuovaCasella.setTipoCasella(Tipo.FINE);
			
			
			listaCaselle.add(nuovaCasella);
			
			if (i!=0) {
				
				nuovaCasella.aggiungiVicina(listaCaselle.get(i-1));
			}
				
		}
	}
	
	/**
	 * @param numeroCaselle
	 * @param casellaPozzo
	 */
	private void creaBase (int numeroCaselle, Casella casellaPozzo) {
		
		for (int i=0; i<numeroCaselle; i++) {
			
			Casella nuovaCasella = new Casella(i);
			
			if (i==0)
				nuovaCasella.setTipoCasella(Tipo.INIZIO);
			
			if (i==numeroCaselle -1)
				nuovaCasella.setTipoCasella(Tipo.FINE);
			
			
			listaCaselle.add(nuovaCasella);
			
			if (i!=0) {
				
				nuovaCasella.aggiungiVicina(listaCaselle.get(i-1));
			}
				
		}
		
		//alla casella iniziale della sottomappa metto l'id della casellaPozzo
		listaCaselle.get(0).setId(casellaPozzo.getId());
	}
	
	//è una semplice sottoMappa.
	//lunghezza = 10 e alla casella tre una casella speciale.
	public void creaPozzo (int numeroCaselle, Casella casellaPozzo) {
		
		creaBase(numeroCaselle, casellaPozzo);
		
		listaCaselle.get(3).setTipoCasella(Tipo.SPECIALE_PARTENZA);
		
		
	}
	
	
	
	

}
