package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class TestSimulatore {

	public static void main(String[] args) {
		Simulator sim=new Simulator();
		
		Map<Integer, Integer> tavoli=new HashMap<>();
		tavoli.put(4, 5); //5 tavoli da 4 posti
		tavoli.put(6, 4);
		tavoli.put(8, 4);
		tavoli.put(10, 2);
		sim.setTavoli(tavoli, 15);
		int d=(int)(Math.random()*10)+1;
		sim.setClientFrequency(Duration.of(d, ChronoUnit.MINUTES));
		
		sim.run();
		
		Statistiche s=sim.getStatistiche();
		System.out.println(s);

	}

}
