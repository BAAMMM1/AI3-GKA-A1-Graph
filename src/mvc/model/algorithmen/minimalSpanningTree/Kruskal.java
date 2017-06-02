package mvc.model.algorithmen.minimalSpanningTree;

import java.util.LinkedList;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import mvc.model.algorithmen.shortestPath.BreadthFirstSearch;
import mvc.model.exceptions.IllegalNotConnectedGraph;
import utility.Printer;

/**
 * Diese Klasse stellt den Kruskal-Algorithmus dar. Der Kruskal-Algorithmus
 * erstellt zunächst einen Spannbaum, der alle Knoten des Ursprungsgraph
 * enthält. Anschließend werden alle Kanten des Ursprungsgraphen aufsteigend
 * nach ihrem Kantengewicht in einer Liste sortiert. Im Anschluss werden die
 * Kanten den Spannbaum nach einander hinzugefügt, so dass kein Kreis im
 * Spannbaum ensteht.
 * 
 * Der Algorithmus ist beendet, wenn jede kante angeguckt wurde
 *
 */
public class Kruskal extends MinimalSpanningTree {

	private LinkedList<Edge> sortedEdges;
	private Graph minimalSpanningTree;
	private BreadthFirstSearch bfs;
	private long runTime;

	/**
	 * Diese Mehtode stellt die Handlungsvorschrift Kruskal-Algorithmus da.
	 * 
	 * @return Gibt den minimalen Spannbaum zurück
	 */
	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();
		this.initKruskal();

		for (int i = 0; i < this.sortedEdges.size(); i++) {

			Edge nextEdge = this.sortedEdges.get(i);

			if (!this.isCircle(nextEdge)) {

				this.addEdgeToSpanningTree(nextEdge);
			}
		}

		long timeEnd = System.currentTimeMillis();
		runTime = (timeEnd - timeStart);

		Printer.prompt(this, "time needed: " + this.getRunTime());
		Printer.prompt(this, "edge-weight sum: " + this.getEdgeWeightes());

		this.validResult();
		return minimalSpanningTree;

	}

	/**
	 * Diese Mehtode fügt dem Spannbaum eine Kante hinzu
	 * 
	 * @param edge
	 *            Kante die hinzugefügt werden soll
	 */
	private void addEdgeToSpanningTree(Edge edge) {
		this.minimalSpanningTree.addEdge(edge.getId(), edge.getNode0().toString(), edge.getNode1().toString(),
				edge.isDirected());
		this.minimalSpanningTree.getEdge(edge.getId().toString()).setAttribute("weight",
				(int) edge.getAttribute("weight"));
		this.minimalSpanningTree.getEdge(edge.getId().toString()).setAttribute("ui.label",
				edge.getAttribute("ui.label").toString());
	}

	/**
	 * Test ob der ermittelte Spannbaum zusammenhängend ist. Ist dies nicht der
	 * Fall, war der Ursprungsgraph nicht zusammenhängend und es wird eine
	 * IllegalNotConnectedGraph Exception geworfen.
	 */
	private void validResult() {
		if (minimalSpanningTree.getEdgeSet().size() < minimalSpanningTree.getNodeSet().size() - 1) {
			throw new IllegalNotConnectedGraph("Graph ist unvollständig");
		}
	}

	/**
	 * Diese Methode initialisiert den Kruskal-Algorithmus. Es wird ein Graph
	 * erstellt, der zunächst alle Knoten des Ursprungsgraph enthält.
	 * Anschließend werden die Kanten des Ursprungsgraf in einer Liste
	 * aufsteigend sortiert.
	 * 
	 * Des Weitere gibt es ein BFS für die Breitensuchen nach Kreisen benötigt.
	 */
	private void initKruskal() {
		this.minimalSpanningTree = new MultiGraph("Kruskal - minimalSpanningTree");

		/*
		 * Der Minimale Spanning Tree eines Graphen besitzt alle Knoten des
		 * ausgangsgraphen, aber keine Kreise
		 */
		for (Node node : this.getGraph().getEachNode()) {
			this.minimalSpanningTree.addNode(node.getId().toString());
			this.minimalSpanningTree.getNode(node.getId()).setAttribute("ui.label",
					node.getAttribute("ui.label").toString());
		}

		this.sortedEdges = new LinkedList<Edge>(this.getGraph().getEdgeSet());
		this.sortedEdges.sort((e1, e2) -> ((int) e1.getAttribute("weight")) - (int) (e2.getAttribute("weight")));

		this.bfs = new BreadthFirstSearch();

	}

	/**
	 * Diese Methode prüft anhand einer übergebenen Kante ob ein Kreis entsteht,
	 * wenn diese Kante hinzugefügt wird. Dies geschiet, indem eine Breitensuche
	 * vom Source der Kante zum Target stattfindet. Gibt es in dem Spannbaum
	 * bereits einen Weg von dem Source zum Target, dann würde ein hinzufügen
	 * der Kante einen Kreis erzeugen, deshalb gibt die Methode true aus, falls
	 * die Breitensuche einen Weg ermittelt.
	 * 
	 * Falss die Breitensuche keinen Weg ermittelt, gibt es keinen Kreis, wenn
	 * die Kante zum Spannbaum hinzugefügt wird.
	 * 
	 * @param edge
	 *            Kante die zum Spannbaum hinzugefügt werden soll.
	 * @return true wenn ein Kreis mit der Kante im Spannbaum ensteht, false
	 *         fals mit der Kante kein Kreis im Spannbaum ensteht
	 */
	private boolean isCircle(Edge edge) {
		Node source = minimalSpanningTree.getNode(edge.getSourceNode().getId());
		Node target = this.minimalSpanningTree.getNode(edge.getTargetNode().getId());

		if (((this.bfs.calculate(minimalSpanningTree, source.toString(), target.toString()).isEmpty()))
				&& !edge.getSourceNode().equals(edge.getTargetNode())) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Summiert alle Kantengewichtungen des berechneten minimalen Spannbaums
	 * auf.
	 * 
	 * @return Kantengewichtssumme des ermittelten minimalen Spannbuams
	 */
	public double getEdgeWeightes() {
		return (double) this.minimalSpanningTree.getEdgeSet().stream().map(e1 -> (int) e1.getAttribute("weight"))
				.reduce(0, (e1, e2) -> e1.intValue() + e2.intValue());
	}

	/**
	 * Gibt die Anzahl der Knoten des Spannbaums zurück
	 * 
	 * @return Knotenanzahl des Spannbaums
	 */
	public int getKnotenAnzahl() {
		return this.minimalSpanningTree.getNodeSet().size();
	}

	/**
	 * Gibt die Anzahl der Kanten des Spannbaums zurück
	 * 
	 * @return Kantenanzahl des Spannbaums
	 */
	public int getKantenAnzahl() {
		return this.minimalSpanningTree.getEdgeSet().size();
	}

	/**
	 * Gibt die RunTime des Algorithmus in Sekunden zurück
	 * 
	 * @return RunTime
	 */
	public long getRunTime() {
		return this.runTime;
	}

	@Override
	public String toString() {
		return "Kruskal";
	}

}
