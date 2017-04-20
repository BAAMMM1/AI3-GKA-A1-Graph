package model.algorithmusSystem.breadthFirstSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.AbstractNode;

import model.algorithmusSystem.Algorithmus;
import utility.Printer;

public class BreadthFirstSearch2 extends Algorithmus{
	
	private Graph graph;
	private List<Node> queueList;
	private List<Node> nodes;
	private List<Edge> edges;
	int lambda;
	
	public BreadthFirstSearch2(Graph graph){
		this.graph = graph;
		this.edges = new ArrayList<Edge>();
		edges.addAll(graph.getEdgeSet());
		
		System.out.println(edges.toString());
		
		this.nodes = new ArrayList<Node>();
		nodes.addAll(graph.getNodeSet());
		
		System.out.println(nodes.toString());
		
	}
	
	
	public Graph stpAlgorithmus(Node source, Node target){
		System.out.println("Starte BFS Algorithmus:");
		this.initializeBFS();
		
		// Hinweg
		this.queueList.add(source);		
		this.setLambdaToNode(source, 0);
		
		while(!this.queueList.isEmpty()){
			Node toLookAt = queueList.get(0);			
			queueList.remove(0);
			
			System.out.println("Analysiere: " + toLookAt.toString());
			
			if(toLookAt == target){
				System.out.println("BFS Target ereicht!");
				break;
			}
			
			List<Node> nachbarn = this.notVisitedNeighbuorsOfNode(toLookAt);
			queueList.addAll(nachbarn);
			
			int bfsCount = toLookAt.getAttribute("BFS");
			for(int i = 0; i < nachbarn.size(); i++){				
	        	this.setLambdaToNode(nachbarn.get(i), (bfsCount + 1));
			}
			
			
        	System.out.println("BFS Warteschlange: " + queueList.toString());
		}
		
		
		
		
		
		// Zurückweg
				List<Node> path = new ArrayList<Node>();
				path.add(target);
				int bfslambda = target.getAttribute("BFS");
				
				int counter = 0;
				while(bfslambda > 0 ){					
					path.add(getNextSmallerBFS(path.get(counter)));					
					counter++;
					bfslambda = path.get(counter).getAttribute("BFS");
				}
				
				System.out.println(path.toString());
				
				
				
				return graph;
		
	}
	
	
	private Node getNextSmallerBFS(Node node) {
        Iterator<Node> nodeIterator = node.getNeighborNodeIterator();
        int nodeBFS = node.getAttribute("BFS");
        while (nodeIterator.hasNext()) {
            Node nachbar = nodeIterator.next();
            int nachbarBFS = nachbar.getAttribute("BFS");
            if (nachbarBFS == nodeBFS-1) {
                return nachbar;
            }
        }
        return null;
    }
	
	
	
	
	public void initializeBFS(){
		queueList = new ArrayList<Node>();
		lambda = 0;
		
		// -1 = no visist
		for (Node node : graph.getEachNode()) {
			node.setAttribute("BFS", -1);
		}
            
		// Raustrennen des labes falls BFS schon einmal auf diesen Graphangewendet wurde
		for (Node node : graph.getEachNode()) {
			System.out.println(node.getAttribute("ui.label").toString().indexOf("λ"));
			if(node.getAttribute("ui.label").toString().indexOf("λ") != -1){
				node.setAttribute("ui.label", node.getAttribute("ui.label").toString().substring(node.getAttribute("ui.label").toString().indexOf("λ")+2, node.getAttribute("ui.label").toString().indexOf(")")));
			}
			
		}
            
		
	}
	
	private Node setLambdaToNode(Node node, int lambda){
		node.setAttribute("BFS", lambda);
		node.setAttribute("ui.label", "λ(" + node.getAttribute("ui.label") +") = " + lambda);
		System.out.println(node.toString() + " setzte lambda: " + node.getAttribute("BFS"));
		// Hier noch color?
		return node;
	}
	
	private List<Node> notVisitedNeighbuorsOfNode(Node node){
		System.out.println("Suche Nachbarn von: " + node.toString());
		List<Node> nachbarn = new ArrayList<Node>();
		Iterator<Edge> edgeIterator = node.getLeavingEdgeIterator();
		
		while (edgeIterator.hasNext()) {
			
			Edge nextEdge = edgeIterator.next();
            Node nextNode;
            
            // TODO Warum geht das hier so mit ungerichtet und gerichtet
            //nextNode = nextEdge.getTargetNode();
            if (node != nextEdge.getNode1()){
            	nextNode = nextEdge.getNode1();
            } else {
            	nextNode = nextEdge.getNode0();
            }
                
           
            int nextNodeBFS = nextNode.getAttribute("BFS");
            if (nextNodeBFS == -1){
            	nachbarn.add(nextNode);
            } else {
            	//System.out.println(nextNode + " wurde breits besucht");
            }
		}
		
		System.out.println("Not visited Nachbarn von " + node.toString() + " " + nachbarn.toString());
		return nachbarn;
	}

	
	
	public String shortestPath(String sourceNode, String targetNode){
		
		Node source = null;
		Node target = null;
		
		// BFS Initialisierung
		
		for(int i = 0; i <= nodes.size() -1 ; i++){
			nodes.get(i).setAttribute("BFS", "not visit");			
		}
	
		System.out.println(sourceNode);
		for(int i = 0; i <= nodes.size()-1 ; i++){
			if(nodes.get(i).getId().toString().equals(sourceNode)){
				source = nodes.get(i);
				System.out.println("-------------" + nodes.get(i).getAttribute("BFS").toString());
				nodes.get(i).setAttribute("BFS", 0);
				System.out.println("-------------" + nodes.get(i).getAttribute("BFS").toString());
			}
		}
		
		for(int i = 0; i <= nodes.size()-1 ; i++){
			if(nodes.get(i).getId().toString().equals(targetNode)){
				target = nodes.get(i);
			}
		}
		
		System.out.println("Source Node: " + source);
		System.out.println("Target Node: " + target);
		
		List<Node> queue = new ArrayList<Node>();
		queue.add(source);
		
		// Hin
		boolean run = true;
		while((!queue.isEmpty()) && run){
			Node toLook = queue.get(0);
			
			System.out.println("Look at Vertex: " + toLook.toString());
			queue.remove(0);
			
			for(int i = 0; i <= edges.size() -1 ; i++){
			
				if(edges.get(i).getSourceNode() == toLook){
					AbstractNode targetNode2 = edges.get(i).getTargetNode();
					
					if(targetNode2.getAttribute("BFS") == "not visit"){
						System.out.println("added to queue: " + targetNode2.toString());
						queue.add(targetNode2);
						int bfs = toLook.getAttribute("BFS");
						targetNode2.setAttribute("BFS", bfs+1);
						
						System.out.println("Vertex: " + targetNode2.toString() + " BFS = " + targetNode2.getAttribute("BFS"));
						
						if(targetNode2 == target){
							System.out.println("target achieved!");
							run = false;
							break;							
						}						
					}
				}				
			}
		}
		
		// Und zurück
		List<Node> path = new ArrayList<Node>();
		path.add(target);
		int counter = 0;
		
		
		int highestBFS = target.getAttribute("BFS");
		while(highestBFS != 0){
			for(int i = 0; i <= nodes.size() -1 ; i++) {
				if(nodes.get(i).getAttribute("BFS") == "not visit"){
					continue;
				} else {
					int bfs = nodes.get(i).getAttribute("BFS");
					if( bfs == (highestBFS-1) && this.isThereAEdgeBetweenThisNodes(nodes.get(i), path.get(counter))){
						path.add(nodes.get(i));
						highestBFS--;
						counter++;
					}
				}
				
				
				
			}
		}
		
		
		System.out.println(path.toString());
		
		
		return null;
	}
	
	
	public boolean isThereAEdgeBetweenThisNodes(Node bfsNode, Node targetNode){
		boolean toReturn = false;
		
		for(int i = 0; i <= edges.size() -1 ; i++){
			if(edges.get(i).getSourceNode() == bfsNode && edges.get(i).getTargetNode() == targetNode){
				toReturn = true;
			}
		}
		
		return toReturn;
	}
	
	
	
	
}


