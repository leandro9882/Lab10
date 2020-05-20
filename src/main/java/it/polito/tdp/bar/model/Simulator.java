package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {

	private PriorityQueue<Event> queue = new PriorityQueue<>();
	// PARAMETRI DI SIMULAZIONE
	private int N_T = 10; // numero tavoli
	private Map<Integer, Integer> TMap; // tipo di tavoli
	private Duration T_IN; // intervallo tra i clienti
	private int N_P; // numero persone
	private double soglia=Math.random();

//	private LocalTime oraApertura=LocalTime.of(8, 00);
//	private LocalTime oraChiusura=LocalTime.of(24, 00);

	// MODELLO DEL MONDO
	private int nTavoli; // tavoli liberi

	// VALORI DA CALCOLARE
	private int numero_totale_clienti;
	private int numero_clienti_soddisfatti;
	private int numero_clienti_insoddisfatti;

	// METODI PER IMPOSTARE I PARAMETRI
	public void setTavoli(Map<Integer, Integer> tavoli, int numTavoli) {
		this.N_T = numTavoli;
		this.TMap = new HashMap<>(tavoli);
	}

	public void setClientFrequency(Duration d) {
		this.T_IN = d;
	}

	public void setPeopleNumber(int n) {
		this.N_P = n;
	}

	// METODI PER RESTITUIRE
	public Statistiche getStatistiche() {
		Statistiche s = new Statistiche(numero_totale_clienti, numero_clienti_soddisfatti,
				numero_clienti_insoddisfatti);
		return s;
	}

	// SIMULAZIONE
	public void run() {
		// preparazione iniziale(mondo e coda eventi)
		this.nTavoli = this.N_T;
		this.numero_totale_clienti = 0;
		this.numero_clienti_insoddisfatti = 0;
		this.numero_clienti_soddisfatti = 0;
 
		this.queue.clear();
		LocalDateTime oraArrivoCliente = LocalDateTime.of(2020, 1, 1, 0, 00);
		do {
			Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT);
			e.setNum_persone((int)(Math.random()*10)+1);
			this.queue.add(e);
			this.numero_totale_clienti++;
			oraArrivoCliente = oraArrivoCliente.plus(this.T_IN);
		} while (this.numero_totale_clienti < 2000);

		// ESECUZIONE VERA E PROPRIA
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll(); // estraggo in ordire temporale
			System.out.println(e);
			this.processEvent(e);

		}
	}

	private void processEvent(Event e) {
		boolean trovato=false;
		switch(e.getType()) {
		case NEW_CLIENT:
			if(nTavoli>0) {
				//cliente soddisfatto,tavolo preso
				//Ma quale tavolo prende? Quanti sono loro?
				int numPersoneTavolo=e.getNum_persone();
				if(numPersoneTavolo<5) {
					if(this.TMap.get(4)!=0 && numPersoneTavolo>=0.5*4) {
					e.setNum_persone_tavolo(4);
					this.TMap.replace(4, this.TMap.get(4)-1);
					trovato=true;
					}
					else if(this.TMap.get(6)!=0 && numPersoneTavolo>=0.5*6) {
						e.setNum_persone_tavolo(6);
						this.TMap.replace(6, this.TMap.get(6)-1);
						trovato=true;
					}
					else if(this.TMap.get(8)!=0 && numPersoneTavolo>=0.5*8) {
						e.setNum_persone_tavolo(8);
						this.TMap.replace(8, this.TMap.get(8)-1);
						trovato=true;
					}
				}
				else if(numPersoneTavolo>4 && numPersoneTavolo<7) {
						if(this.TMap.get(6)!=0) {
							e.setNum_persone_tavolo(6);
							this.TMap.replace(6, this.TMap.get(6)-1);
							trovato=true;
						}
						else if(this.TMap.get(8)!=0 ) {
							e.setNum_persone_tavolo(8);
							this.TMap.replace(8, this.TMap.get(8)-1);
							trovato=true;
						}
						else if(this.TMap.get(10)!=0 ) {
							e.setNum_persone_tavolo(10);
							this.TMap.replace(10, this.TMap.get(10)-1);
							trovato=true;
						}
				}
				else if(numPersoneTavolo>6 && numPersoneTavolo<9) {
					if(this.TMap.get(8)!=0) {
						e.setNum_persone_tavolo(8);
						this.TMap.replace(8, this.TMap.get(8)-1);
						trovato=true;
					}
					else if(this.TMap.get(10)!=0) {
						e.setNum_persone_tavolo(10);
						this.TMap.replace(10, this.TMap.get(10)-1);
						trovato=true;
					}
			}
				else if(numPersoneTavolo>8 && numPersoneTavolo<=10) {
					if(this.TMap.get(10)!=0) {
						e.setNum_persone_tavolo(10);
						this.TMap.replace(10, this.TMap.get(10)-1);
						trovato=true;
					}
			}
				
			
				
				
				//1) aggiorna modello del mondo 
				if(trovato) {
				nTavoli--;
				//TMap devo rimuovere il tavolo che è stato preso
				//2) aggiorna risultati
				
				this.numero_clienti_soddisfatti++;
				
				//3) genera nuovi eventi,quando lascia il tavolo
				int durataTavolo=(int)(Math.random()*61)+60; //[60,120]
				Duration durata=Duration.of(durataTavolo, ChronoUnit.MINUTES);
				Event nuovo=new Event(e.getTime().plus(durata),EventType.TAVOLO_LIBERATO);
				if(e!=null && e.getNum_persone_tavolo()!=0)
				nuovo.setNum_persone_tavolo(e.getNum_persone_tavolo());
				this.queue.add(nuovo);
				}
				else { //se il tavolo non viene trovato deve andare al bancone
					float tolleranza=(float) Math.random(); // va 0.0 a 0.9
					if(soglia>tolleranza) {
						//cliente va via
						this.numero_clienti_insoddisfatti++;
					}
					else {
						//cliente rimane al banco soddisfatto
						this.numero_clienti_soddisfatti++;
					}
				}
			}else {
				int tolleranza=(int) Math.random();
				if(Math.random()>tolleranza) {
					//cliente va via
					this.numero_clienti_insoddisfatti++;
				}
				else {
					//cliente rimane al banco soddisfatto
					this.numero_clienti_soddisfatti++;
				}
			}
			
			break;
			
		case TAVOLO_LIBERATO:
			if(this.TMap.get(e.getNum_persone_tavolo())!=null) {
				this.TMap.replace(e.getNum_persone_tavolo(), this.TMap.get(e.getNum_persone_tavolo())+1);
			}
			this.nTavoli++;

			break;
		}
}}
