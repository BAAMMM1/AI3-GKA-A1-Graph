package mvc.model.algorithmusSystem.Dijkstra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import mvc.model.algorithmusSystem.Algorithmus;
import utility.Printer;

public class Dijkstra extends Algorithmus {
	
	private Graph graph;
	private Node source;
	private Node target;
	private List<Node> path;
	
	private static final int INFINITY = 999;
	
	public Dijkstra(Graph graph){
		this.graph = graph;
	}
	
	public Graph runStart(Node source, Node target){
		this.source = source;
		this.target = target;
		
		Printer.prompt(this, "Starte Dijkstra-Algorithmus");
		Printer.prompt(this, "source: " + source.toString() + " target: " + target.toString());
		this.initializeDijkstra();
		
		
		do{
			Printer.prompt(this, "--------------------------------------------");
			/*
			 * Suche unter den Ecken vi mit OKi = false eine Ecke vh mit dem kleinsten Wert von Entfi
			 */
			Node mitKleinsterEntfernungVH = this.sucheNode();
			Printer.prompt(this, "Knoten mit kleinster Entfernung: " + mitKleinsterEntfernungVH.toString());
			/*
			 * Setze OKh := true. (Wieso?)
			 */
			this.setOKTrue(mitKleinsterEntfernungVH);
			
			/*
			 * Für alle Ecken vj mit OKj = false, für die die Kante vhvj existiert:
					– Falls gilt Entfj > Entfh + lhj dann
					∗ Setze Entfj := Entfh + lhj
					∗ Setze Vorgj := h
			 */
			List<Node> neighbours = this.getNeighboursOfNode(mitKleinsterEntfernungVH);
			Printer.prompt(this, "Nachbarn: " + neighbours.toString());
			List<Node> validNeighbours = this.validNeighbours(neighbours);
			Printer.prompt(this, "ValidNachbarn: " + validNeighbours.toString());
			
			int EntfernungVH = mitKleinsterEntfernungVH.getAttribute("Entfernung");
			Printer.prompt(this, "EntfernungVH: " + EntfernungVH);
			
			for(int i = 0; i < validNeighbours.size(); i++){
				Printer.prompt(this, "Bin hier");
				int validNeighbourEntfernung = validNeighbours.get(i).getAttribute("Entfernung");
				Printer.prompt(this, "ValideNachbar: " + validNeighbours.get(i) + " Kosten: " + String.valueOf(validNeighbourEntfernung));
				
				this.promptHere();
				int kosten = this.costBetweenTwoNodes(mitKleinsterEntfernungVH, validNeighbours.get(i));
				
				if(validNeighbourEntfernung > (EntfernungVH + kosten)){
					Printer.prompt(this, validNeighbours.get(i) + " Entfernung größer - setzt Entfernung und Vorgänger");
					
					this.setEntfernung(validNeighbours.get(i), (EntfernungVH + kosten));
					
					validNeighbours.get(i).setAttribute("Vorg", mitKleinsterEntfernungVH.getId());
					Printer.prompt(this, "Setzte Vorgänger: " + mitKleinsterEntfernungVH.getId());
				}
			}
			
			
			
		} while (this.thereIsAfalseNodes());
		
		
		for(Node node : graph.getEachNode()){
			if(node.getAttribute("Vorg") != null){
				System.out.println("Knoten: " + node.toString() + " Vorgäner: " + node.getAttribute("Vorg").toString());
			}
			
		}
		// Ermittlung des günstigsten Weg
		
		boolean run = true;
		String nextNode = target.getAttribute("Vorg");		
		path = new ArrayList<Node>();
		if(nextNode == null){
			run = false;
		} else {
			List<Node> temp = new ArrayList<Node>(graph.getNodeSet());			
			path.add(target);
		}	

		while(run){
			Printer.prompt(this, "NextNode: " + nextNode);
			Printer.prompt(this, source.getId());
			if(nextNode != source.getId()){
				
				if(graph.getNode(nextNode).getAttribute("Vorg") != null){
					String nextNodeVorgänger = graph.getNode(nextNode).getAttribute("Vorg");
					if(nextNodeVorgänger != null){
						path.add(graph.getNode(nextNode));
						nextNode = graph.getNode(nextNode).getAttribute("Vorg");
					} else {
						Printer.prompt(this, "Vorg = null");
						//path ungültig?
						
					}
				} else {
					run = false;
					break;
				}
				
				
			} else {
				path.add(graph.getNode(nextNode));
				run = false;
			}
			
		}
		
		// Pfad ermitteln
		// Liste umdrehen
		if(!path.isEmpty()){
			List<Node> tempList = new ArrayList<Node>();
			for(int i = path.size() - 1; i >= 0 ; i--){
				tempList.add(path.get(i));
			}
			
			path = tempList;
			Printer.prompt(this, path.toString());
		}
		
		
			
		
		
		
		
		
		return graph;
		
	}
	
	public List<Node> getShortestPath(){
		return path;
	}
	
	public String getShortestPathWithCoast(){
		String temp = "Kürzester Wege von " + this.source.getId().toString() + " nach " + this.target.getId().toString() + " unter Berücksichtigung der Kantengewichtungen:\n" + "[ ";
		for(int i = 0; i<path.size();i++){
			if(i == path.size()-1){
				temp = temp + path.get(i).getId().toString() + " " + path.get(i).getAttribute("Entfernung");
			} else {
				temp = temp + path.get(i).getId().toString() + " " + path.get(i).getAttribute("Entfernung") + " -> ";
			}
			
		}
		return temp + " ]";
	}
	
	private void setEntfernung(Node node, int value){
		node.setAttribute("Entfernung", value);
		node.setAttribute("ui.label", node.getId() + " - Entfernung: " + value);
		Printer.prompt(this, "Entfernung: " + value);
		
	}
	
	private void promptHere(){
		Printer.errPrompt(this, "----------------");
	}
	
	private int costBetweenTwoNodes(Node node1, Node node2){
		Printer.errPrompt(this, "Ermittel kosten zwischen : " + node1.toString() + " und " + node2.toString());
		int toReturn = 0;
		
		for (Edge edge : graph.getEachEdge()) {
			if((edge.getSourceNode() == node1) && (edge.getTargetNode() == node2)){
				int weight = edge.getAttribute("weight");
				Printer.errPrompt(this, "SourceNode :" + edge.getSourceNode() + " TargetNode: " + edge.getTargetNode());
				Printer.errPrompt(this, String.valueOf(weight));
				toReturn = weight;
			}
			
		}

		return toReturn;
	}
	
	private List<Node> validNeighbours(List<Node> neighbours){
		List<Node> validNeighbours = new ArrayList<Node>();
		
		for(int i = 0 ; i < neighbours.size(); i++){
			boolean neighboursOKValie = neighbours.get(i).getAttribute("OK");
			if (neighboursOKValie == false){
				validNeighbours.add(neighbours.get(i));
			}
		}			
		
		
		return validNeighbours;
	}
	
	private List<Node> getNeighboursOfNode(Node node){
		List<Node> neighbours = new ArrayList<Node>();
		
		for (Node thisNode : graph.getEachNode()) {
			Iterator<Edge> edgesOfNode = thisNode.getEachEdge().iterator();
			while(edgesOfNode.hasNext()){
				Edge nextEdge = edgesOfNode.next();
				if(nextEdge.getSourceNode() == node){
					neighbours.add(thisNode);
				}
			}
		}	
		
		return neighbours;
	}
	
	
	private void setOKTrue(Node node){
		Printer.prompt(this, "Setzt OK: ture bei: " + node.toString());
		node.setAttribute("OK", true);
	}
	
	
	private Node sucheNode(){
		Node returnNode = null;
		
		List<Node> falseList = new ArrayList<Node>();
		
		for (Node node : graph.getEachNode()) {
			boolean nodeOKValue = node.getAttribute("OK");
			
			if(nodeOKValue == false){
				falseList.add(node);
			}			
		}	
		// Ermittelte FalseListe (Alles Nodes mit OK=false)
		if(!falseList.isEmpty()){
			returnNode = falseList.get(0);
			
			for(int i = 0; i < falseList.size(); i++){
				int currentEntfernungValue = returnNode.getAttribute("Entfernung");
				// Infinity will nicht so ganz
				int nextEntfernungValue = falseList.get(i).getAttribute("Entfernung");
				
				if(nextEntfernungValue < currentEntfernungValue){
					returnNode = falseList.get(i);
				}				
			}
		}
		
		return returnNode;
		
	}
	
	
	private void initializeDijkstra(){
		Printer.prompt(this, "Initialisiere Dijkstra");
		
		for (Node node : graph.getEachNode()) {
			node.setAttribute("Entfernung", INFINITY);
			node.removeAttribute("Vorg");
		}	
		
		this.setEntfernung(source, 0);		
		
		source.setAttribute("Vorg", source.getId());
		
		for (Node node : graph.getEachNode()) {
			node.setAttribute("OK", false);
		}	
		
	}
	
	private boolean thereIsAfalseNodes(){
		boolean toReturn = false;
		
		for (Node node : graph.getEachNode()) {
			System.out.println(node.toString() + " OK: " + node.getAttribute("OK"));
			boolean nodeOKValue = node.getAttribute("OK");
			if(nodeOKValue == false){
				toReturn = true;
				break;
			} else {
				toReturn = false;
			}
		}	

		return toReturn;
	}
	
	public Graph converteUndirectedToDirected(Graph graph){
		
		List<Edge> newEdges = new ArrayList<Edge>();
		
		for(Edge edge : graph.getEachEdge()){			
			boolean needToAdd = true;
			Node source = edge.getSourceNode();
			Node target = edge.getTargetNode();
			
			graph.removeEdge(edge);
			graph.addEdge(source.getId().toString() + target.getId().toString(), source, target, true);
			
			for(Edge edge2 : graph.getEachEdge()){
				if((edge2.getSourceNode() == target) && (edge2.getTargetNode() == source)){
					needToAdd = false;
				}				
			}
			
			if(needToAdd){
				graph.addEdge(target.getId().toString() + source.getId().toString(), target, source, true);
			}
			
			
		}

		
		
		return graph;		
	}

	@Override
	public String toString() {
		return "Dijkstra :";
	}
	
	

}
