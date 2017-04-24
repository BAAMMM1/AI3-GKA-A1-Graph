package mvc.model.algorithmusSystem.breadthFirstSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import mvc.model.algorithmusSystem.Algorithmus;
import utility.Printer;

public class BreadthFirstSearch extends Algorithmus {

	private Graph graph;
	private List<Node> stack;
	private List<Node> path;
	private Node source;
	private Node target;
	private static final int NOT_VISITED = -1;
	private static final int LAMBDA_START = 0;


	public BreadthFirstSearch(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Diese Mehtode startet einen Durchlauf des Algoritmus
	 */
	@Override
	public Graph start(Node source, Node target) {
		Printer.prompt(this, "-------------------------------------");
		Printer.prompt(this, "Starte BreadthFirstSearch-Algorithmus");

		this.initializeBFS(source, target);

		this.computeLambdas();

		this.computeShortestWay();
		
		Printer.prompt(this, "Beende BreadthFirstSearch-Algorithmus");

		return graph;

	}

	/**
	 * Diese Mehtode setzt alle Lambda-Werte der Knoten vom Start zum Ziel.
	 * Der Startknoten wird mit Lambda = 0 gesetzt und auf den Stack gelegt.
	 * Anschließen wird vom Stack immer wieder ein Knoten gepullt.
	 * Für den gepullten Knoten werden alle noch nicht besuchten Nachbarn ermittelt und dem Stack hinzugefügt.
	 * Außerdem werden den Nachbarn ihr Lambda-Wert hinzugefügt. 
	 */
	private void computeLambdas() {
		Printer.prompt(this, "Berechne Lambda-Werte der Knoten");

		this.stack.add(source);
		this.setLambdaToNode(source, LAMBDA_START);


		while (!this.stack.isEmpty()) {
			Node nextNode = stack.get(0);
			this.stack.remove(0);
			Printer.prompt(this, "Pull from Stack: " + nextNode.toString());

			/*
			 * Wenn der Zielknoten ereicht ist, beendet den durchlauf
			 */
			if (nextNode == target) {
				Printer.prompt(this, "Zielknoten ereicht");
				break;
			} else {
				/*
				 * Ermittle alle Nachbarn des aktuellen Knoten und setzt ihren
				 * Lambda-Wert
				 */
				List<Node> neighbours = this.notVisitedNeighbuorsOfNode(nextNode);
				stack.addAll(neighbours);

				int stackLambdaValue = nextNode.getAttribute("BFS");
				for (int i = 0; i < neighbours.size(); i++) {
					this.setLambdaToNode(neighbours.get(i), (stackLambdaValue + 1));
				}

			}
			Printer.prompt(this, "Stack: " + this.stack.toString());
		}
	}

	/**
	 * Diese Methode berechnet aus allen gesetzten Lambda-Werten den Weg vom Start zum Ziel indem vom
	 * Ziel der Weg rückwärts ermittelt wird
	 */
	private void computeShortestWay() {
		List<Node> pathTemp = new ArrayList<Node>();

		int bfslambda = target.getAttribute("BFS");

		/*
		 * Rückwerts ermittlung
		 */
		if (bfslambda != -1) {
			pathTemp.add(target);
			int counter = 0;
			while (bfslambda > 0) {
				pathTemp.add(getNextSmallerBFS(pathTemp.get(counter)));
				counter++;
				bfslambda = pathTemp.get(counter).getAttribute("BFS");
			}

			/*
			 * Drehen der Liste
			 */
			for (int i = pathTemp.size() - 1; i >= 0; i--) {
				path.add(pathTemp.get(i));
			}
		}

		Printer.prompt(this, "Ermittelter kürzester Weg: " + path.toString());

	}

	private Node getNextSmallerBFS(Node node) {
		// Prüfen ob eine Kanten zwischen den beiden existiert
		Iterator<Node> nodeIterator = node.getNeighborNodeIterator();
		int nodeBFS = node.getAttribute("BFS");
		while (nodeIterator.hasNext()) {
			Node nachbar = nodeIterator.next();
			int nachbarBFS = nachbar.getAttribute("BFS");
			if (nachbarBFS == nodeBFS - 1) {

						return nachbar;				
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
	private void initializeBFS(Node source, Node target) {
		Printer.prompt(this, "Initialisiere BreadthFirstSearch-Algorithmus");
		this.source = source;
		this.target = target;
		Printer.prompt(this, "Setzte Startknoten: " + source.toString());
		Printer.prompt(this, "Setzte Zielknoten: " + target.toString());

		/*
		 * Die Warteschlange, wechle die Knoten enthält, die der Algorihtmus als
		 * nächstes bearbeitet.
		 */
		stack = new ArrayList<Node>();
		
		/*
		 * Alles Knoten werden als noch nicht besucht initialisiert.
		 */
		for (Node node : graph.getEachNode()) {
			node.setAttribute("BFS", NOT_VISITED);
		}

		/*
		 * Label der Nodes zurücksetzten, falls sie bereits von einem anderen
		 * Algorithmus verändert wurden
		 */
		for (Node node : graph.getEachNode()) {
			// TODO Hier fehlt noch das Attribute EdgeIdentifier fals es
			// vorhanden ist.
			node.setAttribute("ui.label", node.getId());
		}
		
		path = new ArrayList<Node>();

	}

	/**
	 * Diese Methode setzt einen Lambda-Wert eines Knoten
	 * @param node Knoten der den Lambda-Wert erhalten soll
	 * @param lambda Wert
	 * @return Gibt den Knoten mit neuen Lambda_Wert zurück
	 */
	private void setLambdaToNode(Node node, int lambda) {
		node.setAttribute("BFS", lambda);
		node.setAttribute("ui.label", "λ(" + node.getAttribute("ui.label") + ") = " + lambda);
		Printer.prompt(this, "Knoten: " + node.toString() + " Lambda-Wert: " + node.getAttribute("BFS"));
		//TODO Einfärbung der Knoten
	}

	/**
	 * Diese Mehtode ermittelt alle Nachbarn eines Knoten die vom BreadthFirstSearch-Algorithmus noch nicht besucht wurden
	 * @param node Knoten dessen Nachbarn ermittelt werden soll
	 * @return Alle Nachbarn des Koten die noch nicht besucht wurden
	 */
	private List<Node> notVisitedNeighbuorsOfNode(Node node) {
		Printer.prompt(this, "Suche Nachbarn von: " + node.toString());
		List<Node> neighbours = new ArrayList<Node>();
	
		/*
		 * Gehe über alle herausgehenden Kanten des Knoten,
		 * wenn das Ziel der Kante noch nicht besuch wurde, setzte es auf die Nachbarn-List
		 */
		for(Edge edge: node.getEachLeavingEdge()){
			Edge nextEdge = edge;
			Node nextNode;

			/*
			 * Für ungerichtete Kanten muss hier getauscht werden,
			 *  ansonsten kann er nur in die gerichtete Richtung durch laufen
			 */
			if (node != nextEdge.getNode1()) {				
				nextNode = nextEdge.getNode1();
			} else {
				nextNode = nextEdge.getNode0();
			}			

			int nextNodeBFS = nextNode.getAttribute("BFS");
			if (nextNodeBFS == -1) {
				neighbours.add(nextNode);
			}
		}
		Printer.prompt(this, "Nachbarn ermittelt: " + neighbours.toString());
		
		return neighbours;
	}

	/**
	 * Diese Mehtode gibt den kürzesten Weg zurück
	 * @return Liste der Knoten die den kürzesten Weg darstellen
	 */
	public List<Node> getShortestPath() {
		// TODO Ermittlung aller Lösungswege des getShortestPath()
		if (path.size() != 0) {
			return this.path;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "BreadthFirstSearch";
	}
	
	

}
