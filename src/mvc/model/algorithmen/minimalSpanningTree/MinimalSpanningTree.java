package mvc.model.algorithmen.minimalSpanningTree;

import org.graphstream.graph.Graph;

import mvc.model.algorithmen.Algorithmus;
import utility.Printer;

public abstract class MinimalSpanningTree extends Algorithmus{
	
	
	
	public Graph calculate(Graph graph) {		
		Printer.prompt(this, "-------------------------------------");
																		
		Printer.prompt(this, "compute minimal spanning tree algorithmus");	
		
		this.initAlgorithmus(graph);

		return this.procedure();
	}
	
	private void initAlgorithmus(Graph graph) {
		this.setGraph(graph);
		
	}

	protected abstract Graph procedure();

}
