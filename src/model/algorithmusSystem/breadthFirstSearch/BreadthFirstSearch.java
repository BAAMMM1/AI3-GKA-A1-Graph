package model.algorithmusSystem.breadthFirstSearch;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.AbstractNode;

import model.algorithmusSystem.Algorithmus;

public class BreadthFirstSearch extends Algorithmus{
	
	private Graph graph;
	private List<AbstractNode> nodes;
	private List<AbstractEdge> edges;
	
	public BreadthFirstSearch(Graph graph){
		this.graph = graph;
		this.edges = new ArrayList<AbstractEdge>();
		edges.addAll(graph.getEdgeSet());
		
		System.out.println(edges.toString());
		
		this.nodes = new ArrayList<AbstractNode>();
		nodes.addAll(graph.getNodeSet());
		
		System.out.println(nodes.toString());
		
	}
	
	
	public String shortestPath(String sourceNode, String targetNode){
		
		AbstractNode source = null;
		AbstractNode target = null;
		
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
		
		List<AbstractNode> queue = new ArrayList<AbstractNode>();
		queue.add(source);
		
		// Hin
		boolean run = true;
		while((!queue.isEmpty()) && run){
			AbstractNode toLook = queue.get(0);
			
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
		List<AbstractNode> path = new ArrayList<AbstractNode>();
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
	
	
	public boolean isThereAEdgeBetweenThisNodes(AbstractNode bfsNode, AbstractNode targetNode){
		boolean toReturn = false;
		
		for(int i = 0; i <= edges.size() -1 ; i++){
			if(edges.get(i).getSourceNode() == bfsNode && edges.get(i).getTargetNode() == targetNode){
				toReturn = true;
			}
		}
		
		return toReturn;
	}

}


