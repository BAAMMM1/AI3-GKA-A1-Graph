package mvc.model.algorithmen.minimalSpanningTree;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import mvc.model.algorithmen.Algorithmus;
import mvc.model.exceptions.IllegalDirectedGraph;
import mvc.model.exceptions.IllegalWeightedGraph;
import utility.Printer;
public abstract class MinimalSpanningTree extends Algorithmus{
	
	
	
	public Graph calculate(Graph graph) {		
		this.validArguments(graph);
		
		Printer.prompt(this, "-------------------------------------");
																		
		Printer.prompt(this, "compute minimal spanning tree algorithmus");	
		
		this.initAlgorithmus(graph);

		return this.procedure();
	}
	
	private void validArguments(Graph graph){
		/*
		 * Graph darf nich directed sein
		 */
		if(graph.getEdge(0).isDirected()){
			throw new IllegalDirectedGraph("Error: MinimalSpanningTree calculation only for undirected Graph");
		}
		
		// Prüfen auf gewichtete Graphen
		for(Edge edge : graph.getEdgeSet()){
			if(edge.getAttribute("weight") == null){
				throw new IllegalWeightedGraph("Jede Kante muss gewichtet sein");
			}
			if((int)edge.getAttribute("weight") < 1){
				throw new IllegalWeightedGraph("Gewichtung darf nicht 0 oder negativ sein");
			}
		}
		
		
		//TODO prüfen auf zusammenhängigkeit vor der Durchführung
		
	}
	
	private void initAlgorithmus(Graph graph) {
		this.setGraph(graph);
		
	}

	protected abstract Graph procedure();

}
