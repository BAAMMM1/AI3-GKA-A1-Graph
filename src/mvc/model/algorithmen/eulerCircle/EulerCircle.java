package mvc.model.algorithmen.eulerCircle;

import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import mvc.model.algorithmen.Algorithm;
import mvc.model.exceptions.IllegalDirectedGraph;
import utility.Printer;

/**
 * Diese Klasse definiert den grundlegenden Aufbau eines
 * EulerCircuit-Algorithmus. Ein EulerCircuit-Alogirthmus kann mit calculate()
 * ausgeführt werden und gibt als Rückgabewert den Eulerkreis als Kantenabfolge
 * an. Dort wird zunächst der übergebene Graph auf zulässigkeit geprüft.
 * 
 * Anschließen wird die jeweilige Handlungsvorschrift, die die Einzelschritten
 * des jeweiligen Algorithmus beinhaltet, ausgeführt. Dies ermöhlicht uns, dass
 * die konkreten Klassen von EulerCircuit-Algorithmus sich nur um ihre jeweilige
 * Handlungsvorschriftt kümmern müssen.
 * 
 *
 */
public abstract class EulerCircle extends Algorithm {

	public List<Edge> calculate(Graph graph) {
		this.validArguments(graph);

		Printer.prompt(this, "-------------------------------------");

		Printer.prompt(this, "compute euler circle algorithmus");

		this.initAlgorithmus(graph);

		return this.procedure();
	}

	/**
	 * Prüft ob alle Knoten einen geraden Knotengrad haben und der Graph
	 * ungerichtet ist.
	 * 
	 * @param graph
	 */
	private void validArguments(Graph graph) {
		/*
		 * Alle Knotengrade müssen gerade sein
		 */
		List<Node> nodes = new LinkedList<Node>();
		nodes.addAll(graph.getNodeSet());
		for (int i = 0; i < nodes.size(); i++) {

			/*
			 * Loopes müssen zu den Kanten hinzugefügt werden, da Graphstream
			 * sie nur als eine Kante zählt. Nicht aber als eine Kante die raus
			 * geht und eine Kante die reinkommt.
			 */
			List<Edge> edges = new LinkedList<Edge>();
			edges.addAll(nodes.get(i).getEdgeSet());

			int grades = edges.size();

			for (int i2 = 0; i2 < edges.size(); i2++) {
				if (edges.get(i2).isLoop()) {
					grades += 1;
				}
			}

			if ((grades % 2) != 0) {
				throw new IllegalArgumentException("all node degree must be even");
			}

		}

		/*
		 * Der Graph muss zusammenhängend sein
		 */

		/*
		 * Der Graph muss ungerichtet sein
		 */
		if (graph.getEdge(0).isDirected()) {
			throw new IllegalDirectedGraph("Error: EulerCircle only for undirected Graph");
		}

	}

	/**
	 * Initialisiert den Algorithmus auf einen Graph
	 * 
	 * @param graph
	 *            Übergebener Graph auf den der Algorithmus angewendet werden
	 *            soll.
	 */
	private void initAlgorithmus(Graph graph) {
		this.setGraph(graph);

	}

	protected abstract List<Edge> procedure();

}
