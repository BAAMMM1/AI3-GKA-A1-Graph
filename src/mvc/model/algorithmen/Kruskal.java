package mvc.model.algorithmen;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import utility.Printer;

public class Kruskal extends Algorithmus {
	

	@Override
	protected Graph procedure() {
		this.initKruskal();
		
		return this.getGraph();
	}
	
	private void initKruskal(){
		
		
	}

	@Override
	public String toString() {
		return "Kruskal";
	}
	
	

}
