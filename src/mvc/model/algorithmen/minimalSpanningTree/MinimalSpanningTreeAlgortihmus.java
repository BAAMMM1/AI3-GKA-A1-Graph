package mvc.model.algorithmen.minimalSpanningTree;

import org.graphstream.graph.Graph;

import mvc.model.algorithmen.Algorithmus;
import utility.Printer;

public abstract class MinimalSpanningTreeAlgortihmus extends Algorithmus{
	
	
	
	public Graph calculate(Graph graph) {
		Printer.prompt(this, "-------------------------------------");
																		
		Printer.prompt(this, "compute minimal spanning tree algorithmus");		

		return this.procedure();
	}
	
	protected abstract Graph procedure();

}
