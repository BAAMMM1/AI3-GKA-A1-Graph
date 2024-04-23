package dev.graumann.graphsearch.mvc.model.algorithmen;

import org.graphstream.graph.Graph;

public class Algorithm {
	
	private Graph graph;
	
	protected Graph getGraph() {
		return graph;
	}
	
	protected void setGraph(Graph graph){
		this.graph = graph;
	}

}
