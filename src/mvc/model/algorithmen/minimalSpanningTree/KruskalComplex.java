package mvc.model.algorithmen.minimalSpanningTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

public class KruskalComplex extends MinimalSpanningTree {

	// TODO getKantengewichtsSumme
	private LinkedList<Edge> sortedEdges;
	private List<Edge> resultF;
	private Graph minimalSpanningTree;
	private List<Node> nodes;

	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();
		this.initKruskal();

		for (int i = 0; i < this.sortedEdges.size(); i++) {
			Edge nextEdge = this.sortedEdges.get(i);

			this.addEdgeToSpanningTree(nextEdge);

			if (this.combiningContainsACyclic(minimalSpanningTree)) {
				this.removeEdgeFromSpanningTree(nextEdge);
			}

		}

		Printer.promptTestOut(this, this.minimalSpanningTree.getEdgeSet().toString());
		Printer.promptTestOut(this, this.resultF.toString());

		long timeEnd = System.currentTimeMillis();
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));
		return minimalSpanningTree;

	}

	private void addEdgeToSpanningTree(Edge edge) {
		this.resultF.add(edge);
		this.minimalSpanningTree.addEdge(edge.getId(), edge.getNode0().toString(), edge.getNode1().toString());
		this.minimalSpanningTree.getEdge(edge.getId().toString()).setAttribute("weight",
				(Integer) edge.getAttribute("weight"));
		this.minimalSpanningTree.getEdge(edge.getId().toString()).setAttribute("ui.label",
				edge.getAttribute("ui.label").toString());
	}

	private void removeEdgeFromSpanningTree(Edge edge) {
		this.resultF.remove(edge);
		minimalSpanningTree.removeEdge(edge.getId());
	}

	boolean isCyclicUtil(int i, boolean visited[], int parent) {

		// StartKnoten des DFS Tree wird auf VISITED gesetzt
		visited[i] = true;

		// Nimm alle benachtbarten Knoten des DFS STARTKNOTEN
		// Und Iteriere über sie
		Iterator<Node> it = nodes.get(i).getNeighborNodeIterator();
		Node node;
		while (it.hasNext()) {
			node = it.next();

			// Wenn ein Nachbar noch nicht besucht wurde, dann starte einen DFS
			// Tree Durchlauf für diesen Nachbarn
			if (!visited[nodes.indexOf(node)]) {
				if (isCyclicUtil(nodes.indexOf(node), visited, i)) {
					return true;
				}

				// Wenn der Nachbar bereits besucht ist und nicht der Vorgänger
				// von diesem Knoten, dann ist ein Kreis			
			} else if (nodes.indexOf(node) != parent) {
				// Dieser Nachbar ist breits besucht und nicht mein Voränger
				return true;
			}
		}
		return false;
	}

	// Diese Mehtode, setzt zunächst alle Knoten auf noch nicht besucht.
	// Returns true if the graph contains a cycle, else false.
	boolean combiningContainsACyclic(Graph graph) {
		this.nodes = new LinkedList<Node>(graph.getNodeSet());
		// Mark all the vertices as not visited and not part of
		// recursion stack

		// Setzte VISITED Flag für alle Knoten
		boolean visited[] = new boolean[nodes.size()];
		for (int index = 0; index < nodes.size(); index++) {
			visited[index] = false;
		}

		// Call the recursive helper function to detect cycle in
		// different DFS trees
		// Laufe über alle Knoten
		for (int i = 0; i < nodes.size(); i++) {

			// Nur wenn der Knoten noch nicht beuscht wurde, öffne die
			// Hilfsfunktion -> Starte DFS Search
			if (!visited[i]) { // Don't recur for u if already visited
				// Rufe Hilfsfunktion mit Knotenposition, und dem
				// visteted-Verzeichnis
				// Startet eine Tiefensuche von dem aktuellen Knoten aus
				if (isCyclicUtil(i, visited, -1)) {
					// Wenn es einen Kreis in dem DFS Tree gibt, dann sende true
					// zurück
					return true;
				}
			}
		}
		return false;
	}

	private void initKruskal() {
		this.minimalSpanningTree = new MultiGraph("Kruskal");
		this.resultF = new LinkedList<Edge>();

		/*
		 * Der Minimale Spanning Tree eines Graphen besitzt alle Knoten des
		 * ausgangsgraphen, aber keine Kreise
		 */
		for (Node node : this.getGraph().getEachNode()) {
			this.minimalSpanningTree.addNode(node.getId());
			this.minimalSpanningTree.getNode(node.getId()).setAttribute("ui.label",
					node.getAttribute("ui.label").toString());
		}

		/*
		 * Die Kanten aufsteigend nach ihrem Gewicht sortieren
		 */
		this.sortedEdges = new LinkedList<Edge>(this.getGraph().getEdgeSet());
		this.sortedEdges.sort((e1, e2) -> ((Integer) e1.getAttribute("weight")).compareTo(e2.getAttribute("weight")));

	}

	public double getEdgeWeightes() {
		return (double) this.resultF.stream().map(e1 -> (Integer) e1.getAttribute("weight")).reduce(0,
				(e1, e2) -> e1.intValue() + e2.intValue());
	}

	public int getKnotenAnzahl() {
		return this.minimalSpanningTree.getNodeSet().size();
	}

	public int getKantenAnzahl() {
		return this.minimalSpanningTree.getEdgeSet().size();
	}

	@Override
	public String toString() {
		return "Kruskal";
	}

}
