package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event implements Comparable<Event>{

	public enum EventType {
		NEW_CLIENT, TAVOLO_LIBERATO
	}
	
	private LocalDateTime time;
	private EventType type;
	private int num_persone;
	private int num_persone_tavolo;
	private float tolleranza;
	
	/**
	 * @param time
	 * @param type
	 * @param num_persone
	 * @param durata
	 * @param tolleranza
	 */
	public Event(LocalDateTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
		this.num_persone_tavolo=0;
		this.num_persone=0;
	}

	public int getNum_persone_tavolo() {
		return num_persone_tavolo;
	}

	public void setNum_persone_tavolo(int num_persone_tavolo) {
		this.num_persone_tavolo = num_persone_tavolo;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public int getNum_persone() {
		return num_persone;
	}

	public void setNum_persone(int num_persone) {
		this.num_persone = num_persone;
	}


	public float getTolleranza() {
		return tolleranza;
	}

	public void setTolleranza(float tolleranza) {
		this.tolleranza = tolleranza;
	}

	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);
	}

	@Override
	public String toString() {
		switch(this.getType()) {
		case TAVOLO_LIBERATO:
			return time + ", " + type +" da: "+this.num_persone_tavolo+" persone";
		case NEW_CLIENT:
		return time + ", NUOVO GRUPPO, da: " + num_persone+" persone";
		
		}
		return null;
	}
	
}
