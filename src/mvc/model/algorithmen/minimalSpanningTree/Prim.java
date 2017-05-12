package mvc.model.algorithmen.minimalSpanningTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

public class Prim extends MinimalSpanningTree {

	private Graph minimalSpanningTree;
	private List<Node> nodesNotInGraphT;
	private List<Node> nodesInGraphT;
	private PriorityQueue<Node> queue;

	private static final Integer OMEGA = 1337;

	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();
		this.initPrim();

		while (this.graphTHasNotAllNodes()) {
			Node nextNode = this.queue.remove();
			System.out.println(nextNode.toString());
			Edge e = nextNode.getAttribute("primEdge");
			System.out.println(e.toString());
			//Edge e = this.getEdgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT();

			this.addEdgeAndNodeToT(nextNode, e);
			
			this.calculateNodeEdges();
			System.out.println(this.queue.toString());
		}

		long timeEnd = System.currentTimeMillis();
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));

		System.out.println(minimalSpanningTree.getNodeSet().size());
		for(Node node: minimalSpanningTree.getNodeSet()){			
			System.out.println(node.toString());
		}
		
		for(Edge edge: minimalSpanningTree.getEdgeSet()){
			System.out.println(edge.toString());
		}
		
		return minimalSpanningTree;

	}

	private void initPrim() {
		this.minimalSpanningTree = new MultiGraph("Prim");
		this.nodesInGraphT = new LinkedList<Node>();
		this.nodesNotInGraphT = new LinkedList<Node>();
		
		this.nodesNotInGraphT.addAll(this.getGraph().getNodeSet());
		

		this.addNodeToResult(this.getRandomStartNode());
		this.queue = new PriorityQueue<Node>(nodesNotInGraphT.size(),
				((e1, e2) -> ((Integer) e1.getAttribute("primWeight")).compareTo(e2.getAttribute("primWeight"))));
	
		this.calculateNodeEdges();
		this.queue.addAll(nodesNotInGraphT);
		System.out.println(this.queue.toString());
		// Comparator übergeben
		// this.queue = new PriorityQueue<Node>();


	}
	
	private void calculateNodeEdges(){
		if(!nodesNotInGraphT.isEmpty()){
			this.queue = new PriorityQueue<Node>(nodesNotInGraphT.size(),
					((e1, e2) -> ((Integer) e1.getAttribute("primWeight")).compareTo(e2.getAttribute("primWeight"))));
		}
		

		for (Node node : nodesNotInGraphT) {
			System.out.println("Ermittel Edges for: " + node.toString());

			Edge lowestEdge;
			Iterator<Edge> it = node.getEdgeIterator();
			lowestEdge = null;

			while (it.hasNext()) {
				Edge nextEdge = it.next();
				
				if((nodesInGraphT.contains(nextEdge.getSourceNode())) || (nodesInGraphT.contains(nextEdge.getTargetNode()))){
					if(lowestEdge == null){
						lowestEdge = nextEdge;
					} else if ((Integer)nextEdge.getAttribute("weight") < (Integer)lowestEdge.getAttribute("weight")) {
						lowestEdge = nextEdge;
					}
				}				
			}
			
			if(lowestEdge != null){
				System.out.println("LowestEdge for: " + node.toString() + " = " + lowestEdge.toString());
				node.addAttribute("primWeight", (Integer)lowestEdge.getAttribute("weight"));
				node.addAttribute("primEdge", lowestEdge);
			} else {
				System.out.println("No lowestEdge for: " + node.toString());
				node.addAttribute("primWeight", (Integer) OMEGA);
			}
			System.out.println("Node primWeight: " + node.getAttribute("primWeight"));
		}
		
		for(int i = 0; i < this.nodesNotInGraphT.size(); i++){
			System.out.println(nodesNotInGraphT.get(i).toString() + " primWeight: " + nodesNotInGraphT.get(i).getAttribute("primWeight"));
		}

		queue.addAll(nodesNotInGraphT);
		

		
	}

	private void addNodeToResult(Node node) {
		Printer.promptTestOut(this, "Füge: " + node.toString() + " zum Result hinzu");
		this.nodesInGraphT.add(node);
		this.nodesNotInGraphT.remove(node);

		this.minimalSpanningTree.addNode(node.getId());
		this.minimalSpanningTree.getNode(node.getId()).setAttribute("ui.label",
				node.getAttribute("ui.label").toString());

	}

	private boolean graphTHasNotAllNodes() {
		return !(this.minimalSpanningTree.getNodeSet().size() == this.getGraph().getNodeSet().size());
	}



	private void addEdgeAndNodeToT(Node node, Edge edge) {
	
		Node source;
		Node target;
		
		if(edge.getSourceNode() == node){
			source = node;
			target = edge.getTargetNode();
		} else {
			source = edge.getSourceNode();
			target = node;
		}
		
		
		if (!this.nodesInGraphT.contains(source)) {
			this.addNodeToResult(source);
		} else {
			this.addNodeToResult(target);
		}
		source = this.minimalSpanningTree.getNode(source.getId());
		target = this.minimalSpanningTree.getNode(target.getId());
		
		this.minimalSpanningTree.addEdge(edge.getId().toString(), source, target);
		this.minimalSpanningTree.getEdge(edge.getId().toString()).addAttribute("weight",
				(Integer)edge.getAttribute("weight"));
		this.minimalSpanningTree.getEdge(edge.getId().toString()).addAttribute("ui.label",
				edge.getAttribute("ui.label").toString());
		
	}

	private Node getRandomStartNode() {
		Random random = new Random();
		Node randomStart = this.nodesNotInGraphT.get(random.nextInt(this.nodesNotInGraphT.size()));
		return randomStart;
	}

	@Override
	public String toString() {
		return "Prima";
	}

}
