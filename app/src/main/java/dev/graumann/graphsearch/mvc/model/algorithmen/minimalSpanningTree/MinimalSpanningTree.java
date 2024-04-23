package dev.graumann.graphsearch.mvc.model.algorithmen.minimalSpanningTree;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import dev.graumann.graphsearch.mvc.model.algorithmen.Algorithm;
import dev.graumann.graphsearch.mvc.model.exceptions.IllegalDirectedGraph;
import dev.graumann.graphsearch.mvc.model.exceptions.IllegalWeightedGraph;
import dev.graumann.graphsearch.utility.Printer;
public abstract class MinimalSpanningTree extends Algorithm{
	
	
	
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
		
		// Prüfen auf gewichtete Graphen -> Muss gesetzt sein und darf nich kleiner als 1 sein
		for(Edge edge : graph.getEdgeSet()){
			if(edge.getAttribute("weight") == null){
				throw new IllegalWeightedGraph("Jede Kante muss gewichtet sein");
			}
			if((int)edge.getAttribute("weight") < 1){
				throw new IllegalWeightedGraph("Gewichtung darf nicht 0 oder negativ sein");
			}
		}
	}
	
	private void initAlgorithmus(Graph graph) {
		this.setGraph(graph);
		
	}

	protected abstract Graph procedure();

}
