package mvc.model.algorithmen.minimalSpanningTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

public class Kruskal extends MinimalSpanningTree {

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

	// TODO Auslagern in die Superclass
	// Nur für ungerichtete?
	boolean isCyclicUtil(int i, boolean visited[], int parent) {
		// Mark the current node as visited
		visited[i] = true;
		Node node;
		LinkedList<Node> temp = new LinkedList<Node>();
		// Recur for all the vertices adjacent to this vertex
		// Iterator<Integer> it = adj[v].iterator();
		Iterator<Node> it = nodes.get(i).getNeighborNodeIterator();
		// Printer.prompt(this, "Schaue Nachbarn von: " + nodes.get(i) + " an");
		while (it.hasNext()) {

			node = it.next();

			// If an adjacent is not visited, then recur for that
			// adjacent
			if (!visited[nodes.indexOf(node)]) {
				if (isCyclicUtil(nodes.indexOf(node), visited, i)) {
					temp.add(node);
					return true;
				} else {
				}

				// TODO Hier irgendwo die Mengen an Kreise in einem Graph
				// bestimmen?!

				// If an adjacent is visited and not parent of current
				// vertex, then there is a cycle.
			} else if (nodes.indexOf(node) != parent) {
				return true;
			}

		}

		// Printer.prompt(this, "return false");
		return false;
	}

	// Returns true if the graph contains a cycle, else false.
	boolean combiningContainsACyclic(Graph graph) {
		this.nodes = new LinkedList<Node>(graph.getNodeSet());
		// Mark all the vertices as not visited and not part of
		// recursion stack
		boolean visited[] = new boolean[nodes.size()];
		for (int index = 0; index < nodes.size(); index++) {
			visited[index] = false;
		}

		// Call the recursive helper function to detect cycle in
		// different DFS trees
		for (int i = 0; i < nodes.size(); i++) {

			if (!visited[i]) { // Don't recur for u if already visited
				if (isCyclicUtil(i, visited, -1)) {
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

	@Override
	public String toString() {
		return "Kruskal";
	}

}
