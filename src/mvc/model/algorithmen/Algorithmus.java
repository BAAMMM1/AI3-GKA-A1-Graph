package mvc.model.algorithmen;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import utility.Printer;

/**
 * Diese Klasse definiert den grundlegenden Aufbau eines Algorithmus. Ein
 * Alogirthmus kann mit compute() ausgeführt werden. Dort wird er zunächst
 * inizialisiert mit einem Graphen, einen Startknoten und einen Zielknoten.
 * Anschließen wird die jeweilige Handlungsvorschrift, die die Einzelschritten
 * des jeweiligen Algorithmus beinhaltet, ausgeführt. Dies ermöhlicht uns, dass
 * die konkreten Klassen von Algorithmus sich nur um ihre jeweilige
 * Handlungsvorschriftt kümmern müssen.
 * 
 * @author chris
 *
 */
public abstract class Algorithmus {

	private Graph graph;
	private Node source;
	private Node target;

	// TODO command pattern für die Auswahl der Befehle?

	// TODO getResult() statt geShortestPath usw? Mit Prompt Ausgabe, was das
	// Result gerade ist?

	// TODO Graph mit übergeben, so dass es von jedem Algortihmus nur ein Objekt
	// geben muss und die interne
	// variable graph jedes mal geleert wird, vlt eine reset Algortihmus ? bzw
	// im initilaize alle Variable zurück auf
	//
	/**
	 * Diese Methode berechnet die interne Handlungsvorschrift aus und führt
	 * somit den Algorithmus aus.
	 * 
	 * @param source
	 *            Startknoten
	 * @param target
	 *            Zielknoten
	 * @return Graph auf den der Algorithmus angewandt wurde
	 */
	public Graph compute(Graph graph, Node source, Node target) {
		Printer.prompt(this, "-------------------------------------"); // ohne
																		// this
		Printer.prompt(this, "compute algorithmus");

		this.initAlgorithmus(graph, source, target);

		return this.procedure();
	}

	/**
	 * Diese Mehtode stellt die interne Handlungsvorschrift eines jeden
	 * Algorithmus, welche die Einzelschritten definiert, dar.
	 * 
	 * @param source
	 *            Startknoten
	 * @param target
	 *            Zielknoten
	 * @return Graph auf den der Algorithmus angewandt wurde
	 */
	protected abstract Graph procedure();

	/**
	 * Initialisiert den Algorithmus auf einen Graphen, einen Startknoten sowie
	 * auf ein Zielknoten. Es ist die einzige Methode um die sich eine konrekte
	 * Klasse Algorithmus kümmern muss.
	 * 
	 * @param graph
	 * @param source
	 * @param target
	 */
	private void initAlgorithmus(Graph graph, Node source, Node target) {
		Printer.promptTestOut(this, "initialize");
		Printer.promptTestOut(this, "set source: " + source.toString());
		Printer.promptTestOut(this, "set target: " + target.toString());
		this.graph = graph;
		this.source = source;
		this.target = target;
	}

	protected Graph getGraph() {
		return graph;
	}

	protected Node getSource() {
		return source;
	}

	protected Node getTarget() {
		return target;
	}

}
