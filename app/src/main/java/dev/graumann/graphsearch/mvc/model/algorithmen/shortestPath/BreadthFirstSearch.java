package dev.graumann.graphsearch.mvc.model.algorithmen.shortestPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import dev.graumann.graphsearch.utility.Printer;
/**
 * Für gerichtet und ungerichtete Graphen
 * @author Chris
 *
 */
public class BreadthFirstSearch extends ShortestPath {

	private static final int NOT_VISITED = -1;
	private static final int LAMBDA_START = 0;
	private boolean directed = false;


	// TODO Graph graph, Node source, Node target kann weg
	/**
	 * Diese Mehtode stellt die Handlungsvorschrift des BFS-Algorithmus da
	 */
	@Override
	protected List<Node> procedure() {
		Printer.promptTestOut(this, "BFS on: " + this.getGraph().toString());

		this.initBFS();

		this.calculateLambdas();

		return this.calculateShortestWay();
		

	}

	/**
	 * Diese Mehtode setzt alle Lambda-Werte der Knoten vom Start zum Ziel.
	 * Der Startknoten wird mit Lambda = 0 gesetzt und auf den Stack gelegt.
	 * Anschließen wird vom Stack immer wieder ein Knoten gepullt.
	 * Für den gepullten Knoten werden alle noch nicht besuchten Nachbarn ermittelt und dem Stack hinzugefügt.
	 * Außerdem werden den Nachbarn ihr Lambda-Wert hinzugefügt. 
	 */
	private void calculateLambdas() {
		Printer.promptTestOut(this, "Berechne Lambda-Werte der Knoten");

		/*
		 * Die Warteschlange, wechle die Knoten enthält, die der Algorihtmus als
		 * nächstes bearbeitet.
		 */
		List<Node> queue = new ArrayList<Node>();
		
		queue.add(this.getSource());
		this.setLambdaToNode(this.getSource(), LAMBDA_START);


		while (!queue.isEmpty()) {
			Node nextNode = queue.get(0);
			queue.remove(0);
			Printer.promptTestOut(this, "Pull from Stack: " + nextNode.toString());

			/*
			 * Wenn der Zielknoten ereicht ist, beendet den Durchlauf
			 */
			if (nextNode == this.getTarget()) {
				Printer.promptTestOut(this, "Zielknoten ereicht");
				break;
			} else {
				/*
				 * Ermittle alle Nachbarn des aktuellen Knoten und setzt ihren
				 * Lambda-Wert
				 */
				List<Node> neighbours = this.notVisitedNeighbuorsOfNode(nextNode);
				queue.addAll(neighbours);

				int stackLambdaValue = nextNode.getAttribute("BFS");
				for (int i = 0; i < neighbours.size(); i++) {
					this.setLambdaToNode(neighbours.get(i), (stackLambdaValue + 1));
				}

			}
			Printer.promptTestOut(this, "Stack: " + queue.toString());
		}
	}

	/**
	 * Diese Methode berechnet aus allen gesetzten Lambda-Werten den Weg vom Start zum Ziel indem vom
	 * Ziel der Weg rückwärts ermittelt wird
	 */
	private List<Node> calculateShortestWay() {
		List<Node> path = new ArrayList<Node>();

		int bfslambda = this.getTarget().getAttribute("BFS");
		/*
		 * Rückwerts ermittlung
		 */
		if (bfslambda != -1) {
			path.add(this.getTarget());
			int counter = 0;
			while (bfslambda > 0) {
				path.add(getNextSmallerBFS(path.get(counter)));
				counter++;
				bfslambda = path.get(counter).getAttribute("BFS");
			}

			/*
			 * Drehen der Liste
			 */
			Collections.reverse(path);
		}

		Printer.promptTestOut(this, "Ermittelter kürzester Weg: " + path.toString());
		return path;

	}



	// TODO testen gegen die Standardimplementierung
	private Node getNextSmallerBFS(Node node) {
		
		/*
		 * Bei directed graph werden auf dem R�ckweg auf die Kanten genommen, die in den Knoten zeige, bzw nicht rausgehen aus ihm.
		 * deshlab hier unterscheiden ob rein oder raus geht;		
		*/
		
		if(!this.directed){
			
			Iterator<Node> nodeIterator = node.getNeighborNodeIterator();			
			int nodeBFS = node.getAttribute("BFS");
			while (nodeIterator.hasNext()) {
				Node nachbar = nodeIterator.next();
				int nachbarBFS = nachbar.getAttribute("BFS");
				if (nachbarBFS == nodeBFS - 1) {

							return nachbar;				
				}
			}
			
			
		} else {
			Iterator<Edge> iterator = node.getEnteringEdgeIterator();
			int nodeBFS = node.getAttribute("BFS");
			while (iterator.hasNext()) {
				Node nachbar = iterator.next().getSourceNode();
				int nachbarBFS = nachbar.getAttribute("BFS");

				if (nachbarBFS == nodeBFS - 1) {
							return nachbar;				
				}
			}
		}
		
		
		
		return null;
	}

	/**
	 * Diese Mehtode Initialisiert alle nötigen Variableb des BFS Algorithmus
	 * und setzt die Kantenbeschriftungen der Knoten zurück.
	 * 
	 * @param source
	 *            Startknoten des Algorithmus
	 * @param target
	 *            Zielknoten des Algorithmus
	 */
	private void initBFS() {		
		/*
		 * Alles Knoten werden als noch nicht besucht initialisiert.
		 */
		for (Node node : this.getGraph().getEachNode()) {
			node.setAttribute("BFS", NOT_VISITED);
		}

		
		if(!this.getGraph().getEdgeSet().isEmpty()){
			this.directed = this.getGraph().getEdgeSet().iterator().next().isDirected();
		}
		
		

	}

	/**
	 * Diese Methode setzt einen Lambda-Wert eines Knoten
	 * @param node Knoten der den Lambda-Wert erhalten soll
	 * @param lambda Wert
	 * @return Gibt den Knoten mit neuen Lambda_Wert zurück
	 */
	private void setLambdaToNode(Node node, int lambda) {
		node.setAttribute("BFS", lambda);
		Printer.promptTestOut(this, "Knoten: " + node.toString() + " Lambda-Wert: " + node.getAttribute("BFS"));
	}

	/**
	 * Diese Mehtode ermittelt alle Nachbarn eines Knoten die vom BreadthFirstSearch-Algorithmus noch nicht besucht wurden
	 * @param node Knoten dessen Nachbarn ermittelt werden soll
	 * @return Alle Nachbarn des Koten die noch nicht besucht wurden
	 */
	private List<Node> notVisitedNeighbuorsOfNode(Node node) {
		Printer.promptTestOut(this, "Suche Nachbarn von: " + node.toString());
		List<Node> neighbours = new ArrayList<Node>();
	
		/*
		 * Gehe über alle herausgehenden Kanten des Knoten,
		 * wenn das Ziel der Kante noch nicht besuch wurde, setzte es auf die Nachbarn-List
		 */
		for(Edge edge: node.getEachLeavingEdge()){
			Printer.promptTestOut(this, "LeavingEdge: " + neighbours.toString());
			Node nextNode;

			/*
			 * Für ungerichtete Kanten muss hier getauscht werden,
			 *  ansonsten kann er nur in die gerichtete Richtung durch laufen
			 */
			if (node != edge.getNode1()) {				
				nextNode = edge.getNode1();
			} else {
				nextNode = edge.getNode0();
			}			
			
			int nextNodeBFS = nextNode.getAttribute("BFS");
			if (nextNodeBFS == -1) {
				neighbours.add(nextNode);
			}
			
		}
		Printer.promptTestOut(this, "Nachbarn ermittelt: " + neighbours.toString());
		
		return neighbours;
	}


	@Override
	public String toString() {
		return "BreadthFirstSearch";
	}
	

}
