package dev.graumann.graphsearch.model.algorithmen.minimalSpanningTree;

public class MinimalSpanningTreeFactory {

	public static MinimalSpanningTree getInstance(String typ) {

		if (typ.equals("Kruskal")) {
			return new Kruskal();
			
		} else if (typ.equals("Prim")){
			return new Prim();	
			
		} else {
			throw new IllegalArgumentException("algortihmus not found");
			
		}
		
	}

}
