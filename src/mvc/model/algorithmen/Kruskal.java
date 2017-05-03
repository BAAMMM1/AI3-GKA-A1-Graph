package mvc.model.algorithmen;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import utility.Printer;

public class Kruskal extends Algorithmus {
	
	private Graph graph;
	private Node source;
	private Node target;
	
	public Kruskal(Graph graph){
		this.graph = graph;
	}

	@Override
	protected Graph procedure(Node source, Node target) {
		this.initKruskal(source, target);
		
		return this.graph;
	}
	
	private void initKruskal(Node source, Node target){
		Printer.promptTestOut(this, "initialize");
		Printer.promptTestOut(this, "set source: " + source.toString());
		Printer.promptTestOut(this, "set target: " + target.toString());
		this.source = source;
		this.target = target;
		
	}

	@Override
	public String toString() {
		return "Kruskal";
	}
	
	

}
