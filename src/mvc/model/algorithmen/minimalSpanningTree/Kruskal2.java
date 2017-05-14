package mvc.model.algorithmen.minimalSpanningTree;

import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import mvc.model.algorithmen.shortestPath.BreadthFirstSearch;
import utility.Printer;


public class Kruskal2 extends MinimalSpanningTree{
	
	private LinkedList<Edge> sortedEdges;
	private List<Edge> resultF;
	private Graph minimalSpanningTree;
	private BreadthFirstSearch bfs;


	//TODO DieseVersion NUR AUF UNGERICHTETE!, also aus einem directed muss ein undirected werden
	// TEST gegen die standard implementierung
	// Es muss also in ein undirected konveritert werden, ansonsten ist das Ergebnis falsch
	// result Graph ok?
	// Oder noch result Edge list, bzw. List<Edge> getResult();
	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();
		this.initKruskal();


		for(int i = 0; i < this.sortedEdges.size(); i++){
			Edge nextEdge = this.sortedEdges.get(i);
						
		
			if(!this.isCircle(nextEdge)){
			this.resultF.add(nextEdge);	
			this.minimalSpanningTree.addEdge(nextEdge.getId(), nextEdge.getNode0().toString(), nextEdge.getNode1().toString(), nextEdge.isDirected());
			this.minimalSpanningTree.getEdge(nextEdge.getId().toString()).setAttribute("weight", (Integer)nextEdge.getAttribute("weight"));
			this.minimalSpanningTree.getEdge(nextEdge.getId().toString()).setAttribute("ui.label", nextEdge.getAttribute("ui.label").toString());
			
			}
					
	
		}		
		
		Printer.promptTestOut(this, this.minimalSpanningTree.getEdgeSet().toString());
		Printer.promptTestOut(this, this.resultF.toString());
		
		
		
		long timeEnd = System.currentTimeMillis();
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));
		System.out.println(this.getEdgeWeightes());
		return minimalSpanningTree;
		
	}
	


	
	private void initKruskal(){		
		this.minimalSpanningTree = new MultiGraph("Kruskal - minimalSpanningTree");
		
		/*
		 * Der Minimale Spanning Tree eines Graphen besitzt alle Knoten des ausgangsgraphen, aber keine Kreise
		 */
		for(Node node: this.getGraph().getEachNode()){
			this.minimalSpanningTree.addNode(node.getId().toString());
			this.minimalSpanningTree.getNode(node.getId()).setAttribute("ui.label", node.getAttribute("ui.label").toString());
			
			for(Edge edge : this.minimalSpanningTree.getNode(node.getId()).getEachLeavingEdge()){
				System.out.println(node.toString() + " ->: " + edge.toString());
			}
			
		}
		
		this.sortedEdges = new LinkedList<Edge>(this.getGraph().getEdgeSet());
		this.sortedEdges.sort((e1,e2) -> ((Integer)e1.getAttribute("weight")).compareTo(e2.getAttribute("weight")));
		
		this.resultF = new LinkedList<Edge>();
		
		this.bfs = new BreadthFirstSearch();
		
		
	}
	
	private boolean isCircle(Edge edge){
		Node source = minimalSpanningTree.getNode(edge.getSourceNode().getId());
		Node target = this.minimalSpanningTree.getNode(edge.getTargetNode().getId());
		System.out.println(minimalSpanningTree.getEdgeSet().toString());
		if(((this.bfs.calculate(minimalSpanningTree, source, target).isEmpty()) || (this.bfs.calculate(minimalSpanningTree, target, source).isEmpty()) ) && !edge.getSourceNode().equals(edge.getTargetNode())){
			return false;
		} else {
			return true;
		}		
		
			
	}
	
	public int getEdgeWeightes(){
		return this.resultF.stream().map(e1 -> (Integer)e1.getAttribute("weight")).reduce(0, (e1,e2) -> e1.intValue()+e2.intValue());
		
	}


	@Override
	public String toString() {
		return "Kruskal2";
	}
	
	

}
