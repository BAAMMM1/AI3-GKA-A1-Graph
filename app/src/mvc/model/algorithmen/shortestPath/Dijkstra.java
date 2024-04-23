package mvc.model.algorithmen.shortestPath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import mvc.model.exceptions.IllegalUndirectedGraph;
import mvc.model.exceptions.IllegalWeightedGraph;
import utility.Printer;

/**
 * Diese Klasse stellt den Dijkstra-Algorithmus dar.
 * Für gerichtete Graphen.
 */
public class Dijkstra extends ShortestPath {

	private static final int INFINITY = 999;


	/**
	 * Diese Mehtode stellt die Handlungsvorschrift des Dijkstra-Algorithmus da
	 */
	@Override
	protected List<Node> procedure() {
		this.validArguments(this.getGraph());
		
		System.clearProperty("org.graphstream.ui.renderer");

		this.initDijkstra();

		do {

			this.calculateEntfernungCoats();

		} while (this.thereIsAfalseNodes());

		/*
		 * Ermittlung des günstigsten Weg
		 */
		return this.calculateShortestPath();


	}

	private void validArguments(Graph graph) {
		/*
		 * Dijkstra darf nur auf gerichteten Graphen anwendbar
		 */
		if(!this.getGraph().getEdge(0).isDirected()){
			throw new IllegalUndirectedGraph("Dijkstra only for directed graph");
			
		/*
		 * Der Graph muss eine psoitives Gewichtung > 0 haben
		 */
		} else if(!this.isGraphCorrectWeighted(graph)){
			throw new IllegalWeightedGraph("Dijkstra only for weight > 0");
		}		
		/* TODO
		 * Der Graph muss zusammenhängend sein
		 * Wie prüfen?
		 */
		
	}

	/**
	 * Berechnet und setzt alle Entfernungskosten der Knoten vom Startknoten
	 */
	private void calculateEntfernungCoats() {
		/*
		 * nextNode = Der Knoten mit OK = false aus der FalseList mit dem
		 * kleinsten Wert von Entfernung
		 */
		Node nextNode = this.getNodeWithlowestEntfernungFromFalseList(this.computeFalseList());
		Printer.promptTestOut(this, "Knoten mit kleinster Entfernung: " + nextNode.toString());

		this.setOKFlag(nextNode, true);
		/*
		 * Für alle Knoten mir OK = false, die alle Zielknoten von nextNode
		 * sind: Falls gilt Entfernung des Nachbarn > Entfernung von nextNode +
		 * kosten dann Setze Entfernung des Nachbarn := Entfernung von nextNode
		 * + Kosten Setze Vorgänger des Nachbarn := nextNode;
		 */

		List<Node> validTargetNodes = this.getValidTargetNodes(this.getTargetNodesOfNode(nextNode));

		int entfernungNextNode = nextNode.getAttribute("Entfernung");

		for (int i = 0; i < validTargetNodes.size(); i++) {
			int entfernungNachbarn = validTargetNodes.get(i).getAttribute("Entfernung");

			int wegKosten = this.calculateCostBetweenTwoNodes(nextNode, validTargetNodes.get(i));

			if (entfernungNachbarn > (entfernungNextNode + wegKosten)) {

				this.setEntfernung(validTargetNodes.get(i), (entfernungNextNode + wegKosten));
				validTargetNodes.get(i).setAttribute("Vorg", nextNode.getId());
			}
		}
	}

	/**
	 * Ermittelt aus den gesetzten Entfernungen der Knoten zum Startknoten den
	 * kürzesten Weg
	 */
	private List<Node> calculateShortestPath() {
		Printer.promptTestOut(this, "Berechne kürzesten Weg");
		List<Node> path = new ArrayList<Node>();
		boolean run = true;
		String nextNode = this.getTarget().getAttribute("Vorg");

		if (nextNode == null) {
			run = false;
		} else {
			List<Node> temp = new ArrayList<Node>(this.getGraph().getNodeSet());
			path.add(this.getTarget());
		}

		/*
		 * Ermittelt den Weg vom Ziel zum Start
		 */
		while (run) {

			if (nextNode != this.getSource().getId()) {

				if (this.getGraph().getNode(nextNode).getAttribute("Vorg") != null) {
					String nextNodeVorgänger = this.getGraph().getNode(nextNode).getAttribute("Vorg");
					path.add(this.getGraph().getNode(nextNode));
					nextNode = this.getGraph().getNode(nextNode).getAttribute("Vorg");
				} else {
					run = false;
					break;
				}

			} else {
				/*
				 * Wenn der Vorgänger das Ziel ist, dann beende
				 */
				path.add(this.getGraph().getNode(nextNode));
				run = false;
			}

		}

		/*
		 * Dreht die Liste für die Ausgabe um
		 */
		if (!path.isEmpty()) {
			List<Node> tempList = new ArrayList<Node>();
			for (int i = path.size() - 1; i >= 0; i--) {
				tempList.add(path.get(i));
			}

			path.clear();
			path.addAll(tempList);
			Printer.promptTestOut(this, path.toString());
		}
		
		return path;
	}


	/**
	 * Gibt einen String zurück, der den kürzesten Weg mit einschließlich seinen
	 * Kosten enthält
	 * 
	 * @return kürzesten Weg mit Kosten
	 */
	public String getShortestPathWithCoast() {
		String temp;
		List<Node> path = this.calculateShortestPath();
		if (path.size() != 0) {
			temp = "Kürzester Wege von " + this.getSource().getId().toString() + " nach " + this.getTarget().getId().toString()
					+ " unter Berücksichtigung der Kantengewichtungen:\n" + "[ ";
			for (int i = 0; i < path.size(); i++) {
				if (i == path.size() - 1) {
					temp += path.get(i).getId().toString() + " " + path.get(i).getAttribute("Entfernung");
				} else {
					temp += path.get(i).getId().toString() + " " + path.get(i).getAttribute("Entfernung")
							+ " -> ";
				}

			}
			temp = temp + " ]";
		} else {
			temp = "Es gibt keinen Pfad zum Ziel";
		}

		return temp;
	}

	/**
	 * Diese Mehtode setzt den Entfernungs-Flag eines Knoten
	 * 
	 * @param node
	 *            Knoten bei dem der Flag gesetzt werden soll
	 * @param value
	 *            Wert des Entfernungs-Flag
	 */
	private void setEntfernung(Node node, int value) {
		node.setAttribute("Entfernung", value);
		node.setAttribute("ui.label", node.getId() + " - Entfernung: " + value);
		Printer.promptTestOut(this, "Entfernung: " + value);

	}

	/**
	 * Ermittelt die Kantengewichtung zwischen zwei Knoten
	 * 
	 * @param node1
	 *            Knoten 1
	 * @param node2
	 *            Knoten 2
	 * @return Gewichtung
	 */
	private int calculateCostBetweenTwoNodes(Node node1, Node node2) {
		int toReturn = 0;
		for (Edge edge : this.getGraph().getEachEdge()) {
			if ((edge.getSourceNode() == node1) && (edge.getTargetNode() == node2)) {
				int weight = edge.getAttribute("weight");
				toReturn = weight;
			}
		}
		return toReturn;
	}

	/**
	 * Gibt eine Liste an Nachbarnknoten aus einer Liste von Nachbarn eines
	 * Knoten zurück, bei denen der OK-Flag false gesetzt ist
	 * 
	 * @param neighbours
	 *            Alle Nachbarn eines Knoten
	 * @return Nachbarn mit OK = False
	 */
	private List<Node> getValidTargetNodes(List<Node> neighbours) {
		List<Node> validNeighbours = new ArrayList<Node>();

		for (int i = 0; i < neighbours.size(); i++) {
			boolean neighboursOKValie = neighbours.get(i).getAttribute("OK");
			if (neighboursOKValie == false) {
				validNeighbours.add(neighbours.get(i));
			}
		}

		return validNeighbours;
	}

	/**
	 * Gibt alle Knoten zurück die ein Ziel des übergabe Knoten sind
	 * 
	 * @param node
	 *            Knoten beim dem die Ziel Knoten ermittelt werden soll
	 * @return Liste aller Zielknoten des Übergabeknoten
	 */
	private List<Node> getTargetNodesOfNode(Node node) {
		List<Node> neighbours = new ArrayList<Node>();

		for (Node thisNode : this.getGraph().getEachNode()) {
			Iterator<Edge> edgesOfNode = thisNode.getEachEdge().iterator();
			while (edgesOfNode.hasNext()) {
				Edge nextEdge = edgesOfNode.next();
				if (nextEdge.getSourceNode() == node) {
					neighbours.add(thisNode);
				}
			}
		}

		return neighbours;
	}

	/**
	 * Setzt das OK-Falg eines Knoten
	 * 
	 * @param node
	 *            Knoten bei dem das Flag gesetzt werden sonn
	 * @param value
	 *            Wert des OK-Flags
	 */
	private void setOKFlag(Node node, boolean value) {
		Printer.promptTestOut(this, "Setzt OK: ture bei: " + node.toString());
		node.setAttribute("OK", value);
	}

	/**
	 * Ermittelt den Knoten aus der FalseList, welcher den kleinen
	 * Entfernungsweert besitzt
	 * 
	 * @param falseList
	 * @return Knoten mit kleinsten Entfernungswert
	 */
	private Node getNodeWithlowestEntfernungFromFalseList(List<Node> falseList) {
		Node returnNode = null;

		if (!falseList.isEmpty()) {
			returnNode = falseList.get(0);

			for (int i = 0; i < falseList.size(); i++) {
				int currentEntfernungValue = returnNode.getAttribute("Entfernung");
				// Infinity will nicht so ganz
				int nextEntfernungValue = falseList.get(i).getAttribute("Entfernung");

				if (nextEntfernungValue < currentEntfernungValue) {
					returnNode = falseList.get(i);
				}
			}
		}

		return returnNode;

	}

	/**
	 * Ermittelt aus allen Knoten des Graphen mit dem OK-Flag: false
	 * 
	 * @return Liste der Knoten mit OK = false
	 */
	private List<Node> computeFalseList() {
		List<Node> falseList = new ArrayList<Node>();

		for (Node node : this.getGraph().getEachNode()) {
			boolean nodeOKValue = node.getAttribute("OK");

			if (nodeOKValue == false) {
				falseList.add(node);
			}
		}

		return falseList;

	}

	/**
	 * Diese Methode initialisiert drei Flags: Entfernung - Die vom jeweiligen
	 * Knoten bisher günstigste Wegkosten bis zum Startknoten Vorg - Der
	 * ermittelte Vorgänger auf dem Weg des jeweiligen Knoten auf dem
	 * günstigsten Weg OK - Als bereits beuscht Flag Der Startknoten erhält:
	 * Entfernung 0, Vorgänger sich selbst, OK = false; Alle anderen Knoten
	 * erhalten: Entfernung INFINITY, Vorgänger leer, OK = false;
	 * 
	 * @param source
	 *            Startknoten des Algorithmus
	 * @param target
	 *            Zielknoten des Algorithmus
	 */
	private void initDijkstra() {

		for (Node node : this.getGraph().getEachNode()) {
			node.setAttribute("Entfernung", INFINITY);
			node.removeAttribute("Vorg");
		}

		this.setEntfernung(this.getSource(), 0);

		this.getSource().setAttribute("Vorg", this.getSource().getId());

		for (Node node : this.getGraph().getEachNode()) {
			node.setAttribute("OK", false);
		}

	}

	/**
	 * Stellt fest, ob im Graphen noch ein Knoten ist, bei dem der OK-Flag false
	 * gesetzt ist
	 * 
	 * @return true/false
	 */
	private boolean thereIsAfalseNodes() {
		boolean toReturn = false;

		for (Node node : this.getGraph().getEachNode()) {
			boolean nodeOKValue = node.getAttribute("OK");
			if (nodeOKValue == false) {
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
		return "Dijkstra";
	}

	/**
	 * Diese Mehtode gibt an, ob der Graph eine korrekte Gewichtung hat. D.h.
	 * Die Kanten müssen eine positive Gewichtung besitzten die nich 0 ist.
	 * 
	 * @return true/false
	 */
	public boolean isGraphCorrectWeighted(Graph graph) {
		boolean temp = true;
		for (Edge edge : graph.getEachEdge()) {

			if (edge.getAttribute("weight") != null) {

				int weight = edge.getAttribute("weight");
				if (weight >= 1) {
					temp = temp && true;
				} else {
					temp = temp && false;
				}
			} else {
				temp = temp && false;
			}
		}

		System.out.println("isGraphDijkstraCorrectWeighted: " + temp);
		return temp;

	}


}
