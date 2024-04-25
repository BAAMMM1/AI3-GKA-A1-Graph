package dev.graumann.graphsearch.model.algorithmen.eulerTour;

public class EulerTourFactory {

	public static EulerTour getInstance(String typ) {

		if (typ.equals("Hierholzer")) {
			return new Hierholzer();
			
		} else if (typ.equals("Fleury")){
			return new Fleury();	
			
		} else {
			throw new IllegalArgumentException("algortihmus not found");
			
		}
		
	}

}
