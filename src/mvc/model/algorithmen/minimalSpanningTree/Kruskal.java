package mvc.model.algorithmen.minimalSpanningTree;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

// TODO zwei Varianten, minimalen Spannbaum und maximalen Spannbaum
// https://www.youtube.com/watch?v=GJ17vvqY6aE
public class Kruskal extends MinimalSpanningTreeAlgortihmus{
	


	protected Graph procedure() {
		this.initKruskal();
		
		return null;
		
	}
	
	private void initKruskal(){
		
		Edge start = this.getLowestWeightEdge();
		this.addEdgeNodesToBesuchtListe(start);
		
		Edge nextEdge = this.getLowestWeightEdge();
		
		
	}

	private void addEdgeNodesToBesuchtListe(Edge start) {
		// TODO Auto-generated method stub
		
	}

	private Edge getLowestWeightEdge() {
		
		// Achtung, es darf kein Zyklus entstehen
		
		return null;
	}

	@Override
	public String toString() {
		return "Kruskal";
	}
	
	

}
