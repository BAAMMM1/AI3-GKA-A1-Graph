package dev.graumann.graphsearch.mvc.model.algorithmen;

import dev.graumann.graphsearch.mvc.model.algorithmen.shortestPath.Dijkstra;

public class AlgorithmFactory {

	
	public static Algorithm getInstance(String typ){
		
		if(typ.equals("Dijkstra")){
			return new Dijkstra();
		} else {
			
		}
		return null;
		
	}

}
