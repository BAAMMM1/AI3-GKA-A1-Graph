package mvc.model.algorithmen;

import java.util.List;

import org.graphstream.graph.Edge;

// TODO zwei Varianten, minimalen Spannbaum und maximalen Spannbaum
// https://www.youtube.com/watch?v=GJ17vvqY6aE
public class Kruskal extends Algorithmus<Edge> {
	

	@Override
	protected void procedure() {
		this.initKruskal();
		
		
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
