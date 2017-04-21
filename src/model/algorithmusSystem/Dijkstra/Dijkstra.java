package model.algorithmusSystem.Dijkstra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import model.algorithmusSystem.Algorithmus;
import utility.Printer;

public class Dijkstra extends Algorithmus {
	
	private Graph graph;
	private Node source;
	private Node target;
	List<Node> path;
	
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
		List<Node> temp = new ArrayList<Node>(graph.getNodeSet());
		path = new ArrayList<Node>();
		path.add(target);
		boolean run = true;
		String nextNode = target.getAttribute("Vorg");		

		while(run){
			Printer.prompt(this, "NextNode: " + nextNode);
			Printer.prompt(this, source.getId());
			if(nextNode != source.getId()){
				
				String nextNodeVorgänger = graph.getNode(nextNode).getAttribute("Vorg");
				if(nextNodeVorgänger != null){
					path.add(graph.getNode(nextNode));
					nextNode = graph.getNode(nextNode).getAttribute("Vorg");
				} else {
					Printer.prompt(this, "Vorg = null");
					//path ungültig?
					
				}
				
			} else {
				path.add(graph.getNode(nextNode));
				run = false;
			}
			
		}
		
		// Pfad ermitteln
		// Liste umdrehen
		List<Node> tempList = new ArrayList<Node>();
		for(int i = path.size() - 1; i >= 0 ; i--){
			tempList.add(path.get(i));
		}
		
		path = tempList;
		
			
		
		Printer.prompt(this, path.toString());
		
		
		
		return graph;
		
	}
	
	public List<Node> getShortestPath(){
		return path;
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
				int weight = Integer.valueOf(edge.getAttribute("weight"));
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
			node.setAttribute("Entfernung", 999);
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

	@Override
	public String toString() {
		return "Dijkstra :";
	}
	
	

}
