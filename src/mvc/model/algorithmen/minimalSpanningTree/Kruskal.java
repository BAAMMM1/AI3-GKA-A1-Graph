package mvc.model.algorithmen.minimalSpanningTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

// TODO zwei Varianten, minimalen Spannbaum und maximalen Spannbaum
// https://www.youtube.com/watch?v=GJ17vvqY6aE
public class Kruskal extends MinimalSpanningTree{
	
	List<Node> besuchteKnoten;
	List<Edge> edges;
	List<List<Node>> list;
	Graph minimalSpanningTree;
	List<Node> nodes;


	protected Graph procedure() {
		this.initKruskal();
		
			
		
		Printer.promptTestOut(this, "" + this.isCyclic(this.getGraph()));
		
		Printer.promptTestOut(this, this.getGraph().getNodeSet().toString());
		Printer.promptTestOut(this, this.getGraph().getEdgeSet().toString());
		
		while(gebildeIstNichtZusammenhaengen()){
			Edge edge = this.getLowestWeightEdge();
			this.addEdgeNodesToBesuchtListe(edge);
		}
		
		Printer.promptTestOut(this, this.minimalSpanningTree.getNodeSet().toString());
		Printer.promptTestOut(this, this.minimalSpanningTree.getEdgeSet().toString());
		
		
		
		
		return minimalSpanningTree;
		
	}
	
	// TODO Auslagern in die Superclass
	// Nur für ungerichtete?
    boolean isCyclicUtil(int i, boolean visited[], int parent)
    {
        // Mark the current node as visited
        visited[i] = true;
        Node node;
        LinkedList<Node> temp = new LinkedList<Node>();
        // Recur for all the vertices adjacent to this vertex
       // Iterator<Integer> it = adj[v].iterator();
        Iterator<Node> it = nodes.get(i).getNeighborNodeIterator();
        //Printer.prompt(this, "Schaue Nachbarn von: " + nodes.get(i) + " an");
        while (it.hasNext())
        {
        	
            node = it.next();            
            //Printer.prompt(this, "Sehe Nachbar: " + node.toString() + " an");
 
            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited[nodes.indexOf(node)])
            {
            	//Printer.prompt(this, "Rufe auf: isCyclicUtil(" + nodes.indexOf(node) + " , " + " " + visited + " , " + i + ") bei Node: " + node.toString());
            	if (isCyclicUtil(nodes.indexOf(node), visited, i)){
                	//Printer.prompt(this, "If return true: " + node.toString());
                	temp.add(node);
                   return true;
	            } else {
	            	//Printer.prompt(this, "If return false: " + node.toString());
	            }
            
            // TODO Hier irgendwo die Mengen an Kreise in einem Graph bestimmen?!
 
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            } else if (nodes.indexOf(node) != parent){
            	//Printer.prompt(this, "return true because: " + "nodes.indexOf(node) " + nodes.indexOf(node) + " != " + parent + " Node: " + node.toString());
                return true;
            }
           
        }
        
        list.add(temp);
        //Printer.prompt(this, "return false");
        return false;
    }
 
    // Returns true if the graph contains a cycle, else false.
    boolean isCyclic(Graph graph)
    {
    	this.nodes = new LinkedList<Node>(graph.getNodeSet());
        // Mark all the vertices as not visited and not part of
        // recursion stack
        boolean visited[] = new boolean[nodes.size()];
        for (int index = 0; index < nodes.size(); index++){
            visited[index] = false;
        }
 
        // Call the recursive helper function to detect cycle in
        // different DFS trees
        for (int i = 0; i < nodes.size(); i++){
        	
            if (!visited[i]){ // Don't recur for u if already visited
            	//Printer.prompt(this, "look at: " + nodes.get(i));
                if (isCyclicUtil(i, visited, -1)){               
                    return true;
                }
            }
        }
        return false;
    }
	
	/*
	// TODO Auslagern in die Superclass
	// Nur für ungerichtete?
    boolean isCyclicUtil(int i, boolean visited[], int parent)
    {
        // Mark the current node as visited
        visited[i] = true;
        Node node;
 
        // Recur for all the vertices adjacent to this vertex
       // Iterator<Integer> it = adj[v].iterator();
        Iterator<Node> it = nodes.get(i).getNeighborNodeIterator();
        Printer.prompt(this, "Schaue Nachbarn von: " + nodes.get(i) + " an");
        while (it.hasNext())
        {
            node = it.next();            
            Printer.prompt(this, "Sehe Nachbar: " + node.toString() + " an");
 
            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited[nodes.indexOf(node)])
            {
                if (isCyclicUtil(nodes.indexOf(node), visited, i)){
                	Printer.prompt(this, "If return true: " + node.toString());
                    return true;
	            } else {
	            	Printer.prompt(this, "If return false: " + node.toString());
	            }
            
            // TODO Hier irgendwo die Mengen an Kreise in einem Graph bestimmen?!
 
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            } else if (nodes.indexOf(node) != parent){
            	Printer.prompt(this, "return true because: " + "nodes.indexOf(node) " + nodes.indexOf(node) + " != " + parent);
                return true;
            }
        }
        return false;
    }
 
    // Returns true if the graph contains a cycle, else false.
    boolean isCyclic(Graph graph)
    {
        // Mark all the vertices as not visited and not part of
        // recursion stack
        boolean visited[] = new boolean[nodes.size()];
        for (int index = 0; index < nodes.size(); index++){
            visited[index] = false;
        }
 
        // Call the recursive helper function to detect cycle in
        // different DFS trees
        for (int i = 0; i < nodes.size(); i++)
            if (!visited[i]){ // Don't recur for u if already visited
            	Printer.prompt(this, "look at: " + nodes.get(i));
                if (isCyclicUtil(i, visited, -1)){
                    return true;
                }
            }
        return false;
    }
    */
    
    
	
	private boolean gebildeIstNichtZusammenhaengen(){
		if(this.besuchteKnoten.containsAll(this.getGraph().getNodeSet())){
			Printer.promptTestOut(this, "Gebilde ist zusammenhängend");
			return false;
		} else {
			return true;
		}
		
	}
	
	private void initKruskal(){
		this.besuchteKnoten = new LinkedList<Node>();
		this.edges = new ArrayList<Edge>();
		this.list = new LinkedList<List<Node>>();
		this.minimalSpanningTree = new MultiGraph("Kruskal");
		
		
	}

	private void addEdgeNodesToBesuchtListe(Edge edge) {
		this.besuchteKnoten.add(edge.getNode0());
		this.besuchteKnoten.add(edge.getNode1());
		
		
	}

	private Edge getLowestWeightEdge() {
		Edge nextEdge;
		
		nextEdge = this.getGraph().getEdgeSet().stream()
				.min((e1, e2) -> ((Integer) e1.getAttribute("weight"))
						.compareTo((Integer)(e2.getAttribute("weight"))))
				.orElse(null);
		
		
		//this.getGraph().removeEdge(nextEdge);
		this.getGraph().getEdgeSet().remove(nextEdge);
		
		if(minimalSpanningTree.getNode(nextEdge.getNode0().getId()) == null){
			minimalSpanningTree.addNode(nextEdge.getNode0().getId());
			minimalSpanningTree.getNode(nextEdge.getNode0().getId()).setAttribute("ui.label", nextEdge.getNode0().getId());
		}
		if(minimalSpanningTree.getNode(nextEdge.getNode1().getId()) == null){
			minimalSpanningTree.addNode(nextEdge.getNode1().getId());
			minimalSpanningTree.getNode(nextEdge.getNode1().getId()).setAttribute("ui.label", nextEdge.getNode1().getId());
		}
		this.minimalSpanningTree.addEdge(nextEdge.getId(), nextEdge.getNode0().toString(), nextEdge.getNode1().toString());
		
		System.out.println("--------------->: " + this.isCyclic(minimalSpanningTree));
		if(this.isCyclic(minimalSpanningTree)){
			minimalSpanningTree.removeEdge(nextEdge.getId());
		}
		
		this.edges.add(nextEdge);
		

		// remove edge from graph
		
		//Printer.promptTestOut(this, nextEdge.toString());
		
		// Achtung, es darf kein Zyklus entstehen
		
		return nextEdge;
	}

	@Override
	public String toString() {
		return "Kruskal";
	}
	
	

}
