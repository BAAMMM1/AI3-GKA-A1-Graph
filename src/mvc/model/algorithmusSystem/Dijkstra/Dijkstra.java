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

	public Dijkstra(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Diese Mehtode startet einen Durchlauf des Algoritmus
	 */
	@Override
	public Graph start(Node source, Node target) {
		System.clearProperty("org.graphstream.ui.renderer");
		Printer.prompt(this, "Starte Dijkstra-Algorithmus");

		this.initializeDijkstra(source, target);

		do {

			this.computeEntfernungCoats();

		} while (this.thereIsAfalseNodes());

		/*
		 * Ermittlung des günstigsten Weg
		 */
		this.computeShortestPath();

		Printer.prompt(this, "Beende Dijkstra-Algorithmus");

		return graph;

	}

	/**
	 * Berechnet und setzt alle Entfernungskosten der Knoten vom Startknoten
	 */
	private void computeEntfernungCoats() {
		/*
		 * nextNode = Der Knoten mit OK = false aus der FalseList mit dem
		 * kleinsten Wert von Entfernung
		 */
		Node nextNode = this.getNodeWithlowestEntfernungFromFalseList(this.computeFalseList());
		Printer.prompt(this, "Knoten mit kleinster Entfernung: " + nextNode.toString());

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

			int wegKosten = this.computeCostBetweenTwoNodes(nextNode, validTargetNodes.get(i));

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
	private void computeShortestPath() {
		Printer.prompt(this, "Berechne kürzesten Weg");
		boolean run = true;
		String nextNode = target.getAttribute("Vorg");
		path = new ArrayList<Node>();
		if (nextNode == null) {
			run = false;
		} else {
			List<Node> temp = new ArrayList<Node>(graph.getNodeSet());
			path.add(target);
		}

		/*
		 * Ermittelt den Weg vom Ziel zum Start
		 */
		while (run) {

			if (nextNode != source.getId()) {

				if (graph.getNode(nextNode).getAttribute("Vorg") != null) {
					String nextNodeVorgänger = graph.getNode(nextNode).getAttribute("Vorg");
					path.add(graph.getNode(nextNode));
					nextNode = graph.getNode(nextNode).getAttribute("Vorg");
				} else {
					run = false;
					break;
				}

			} else {
				/*
				 * Wenn der Vorgänger das Ziel ist, dann beende
				 */
				path.add(graph.getNode(nextNode));
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

			path = tempList;
			Printer.prompt(this, path.toString());
		}

	}

	public List<Node> getShortestPath() {
		return path;
	}

	public String getShortestPathWithCoast() {
		String temp;
		if (path.size() != 0) {
			temp = "Kürzester Wege von " + this.source.getId().toString() + " nach " + this.target.getId().toString()
					+ " unter Berücksichtigung der Kantengewichtungen:\n" + "[ ";
			for (int i = 0; i < path.size(); i++) {
				if (i == path.size() - 1) {
					temp = temp + path.get(i).getId().toString() + " " + path.get(i).getAttribute("Entfernung");
				} else {
					temp = temp + path.get(i).getId().toString() + " " + path.get(i).getAttribute("Entfernung")
							+ " -> ";
				}

			}
			temp = temp + " ]";
		} else {
			temp = "Es gibt keinen Pfad zum Ziel";
		}

		return temp;
	}

	private void setEntfernung(Node node, int value) {
		node.setAttribute("Entfernung", value);
		node.setAttribute("ui.label", node.getId() + " - Entfernung: " + value);
		Printer.prompt(this, "Entfernung: " + value);

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
	private int computeCostBetweenTwoNodes(Node node1, Node node2) {
		int toReturn = 0;
		for (Edge edge : graph.getEachEdge()) {
			if ((edge.getSourceNode() == node1) && (edge.getTargetNode() == node2)) {
				int weight = edge.getAttribute("weight");
				toReturn = weight;
			}
		}
		return toReturn;
	}

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
	 * 
	 * @param node
	 * @return
	 */
	private List<Node> getTargetNodesOfNode(Node node) {
		List<Node> neighbours = new ArrayList<Node>();

		for (Node thisNode : graph.getEachNode()) {
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
	 * @param node Knoten bei dem das Flag gesetzt werden sonn
	 * @param value Wert des OK-Flags
	 */
	private void setOKFlag(Node node, boolean value) {
		Printer.prompt(this, "Setzt OK: ture bei: " + node.toString());
		node.setAttribute("OK", value);
	}

	/**
	 * Ermittelt den Knoten aus der FalseList, welcher den kleinen Entfernungsweert besitzt
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
	 * @return Liste der Knoten mit OK = false
	 */
	private List<Node> computeFalseList() {
		List<Node> falseList = new ArrayList<Node>();

		for (Node node : graph.getEachNode()) {
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
	private void initializeDijkstra(Node source, Node target) {
		Printer.prompt(this, "Initialisiere Dijkstra");

		Printer.prompt(this, "Setzte Startknoten: " + source.toString());
		Printer.prompt(this, "Setzte Zielknoten: " + target.toString());
		this.source = source;
		this.target = target;

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

	private boolean thereIsAfalseNodes() {
		boolean toReturn = false;

		for (Node node : graph.getEachNode()) {
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

	public Graph converteUndirectedToDirected(Graph graph) {

		List<Edge> edges = new ArrayList();
		edges.addAll(graph.getEdgeSet());

		for (int i = 0; i < edges.size(); i++) {
			Edge toLook = edges.get(i);
			System.out.println(i);
			System.out.println("Schaue an: " + toLook.toString());
			boolean needToAdd = true;
			Node source = toLook.getSourceNode();
			Node target = toLook.getTargetNode();

			// UI Laber mit der bezeichnung fehlt noch)
			graph.removeEdge(source.toString() + target.toString());
			graph.addEdge(source.toString() + target.toString(), source, target, true);

			int weigt = toLook.getAttribute("weight");
			graph.getEdge(source.toString() + target.toString()).setAttribute("weight", weigt);
			graph.getEdge(source.toString() + target.toString()).setAttribute("ui.label",
					toLook.getAttribute("ui.label").toString());

			Edge toLook2 = graph.getEdge(target.toString() + source.toString());
			if (toLook2 != null) {
				System.out.println(target.getId().toString() + source.getId().toString());
				graph.removeEdge(target.getId().toString() + source.getId().toString());
				graph.addEdge(target.toString() + source.getId().toString(), target, source, true);

				int weigt2 = toLook2.getAttribute("weight");
				graph.getEdge(target.toString() + source.toString()).setAttribute("weight", weigt2);
				graph.getEdge(target.toString() + source.toString()).setAttribute("label",
						toLook2.getAttribute("ui.label").toString());

			} else {
				graph.addEdge(target.toString() + source.toString(), target, source, true);
				graph.getEdge(target.toString() + source.toString()).setAttribute("weight", weigt);
				graph.getEdge(target.toString() + source.toString()).setAttribute("ui.label",
						toLook.getAttribute("ui.label").toString());
			}

		}

		for (Edge edge : graph.getEachEdge()) {
			System.out.println(edge.toString());
		}

		return graph;
	}

	@Override
	public String toString() {
		return "Dijkstra :";
	}

	/**
	 * Diese Mehtode gibt an, ob der Graph eine korrekte Gewichtung hat. D.h.
	 * Die Kanten müssen eine positive Gewichtung besitzten die nich 0 ist.
	 * 
	 * @return true/false
	 */
	public boolean isGraphCorrectWeighted() {
		boolean temp = true;
		for (Edge edge : this.graph.getEachEdge()) {

			if (edge.getAttribute("weight") != null) {

				int weight = edge.getAttribute("weight");
				if (weight >= 0) {
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
