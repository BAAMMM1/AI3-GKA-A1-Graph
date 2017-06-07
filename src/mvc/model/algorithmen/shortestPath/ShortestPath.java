package mvc.model.algorithmen.shortestPath;

import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import mvc.model.algorithmen.Algorithm;
import utility.Printer;

/**
 * Diese Klasse definiert den grundlegenden Aufbau eines
 * Traversierung-Algorithmus. Ein Traversierung-Alogirthmus kann mit calculate()
 * ausgeführt werden. Dort wird er zunächst die übergabe Parameter
 * aufzulässigkeit geprüft. Anschließenden wird der Algorithmus mit einem
 * Graphen, einen Startknoten und einen Zielknoten inizialisiert. Anschließend
 * wird die jeweilige Handlungsvorschrift, die die Einzelschritten des
 * jeweiligen Algorithmus beinhaltet, ausgeführt. Dies ermöhlicht uns, dass die
 * konkreten Klassen von Algorithmus sich nur um ihre jeweilige
 * Handlungsvorschriftt kümmern müssen.
 * 
 */
public abstract class ShortestPath extends Algorithm {

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
	 * @param graph
	 * @param source
	 *            Startknoten
	 * @param target
	 *            Zielknoten
	 * @return Knotenliste, die den kürzesten Weg darfstellt
	 */
	public List<Node> calculate(Graph graph, String source, String target) {
		this.validArguments(graph, source, target);

		Printer.promptTestOut(this, "-------------------------------------"); // ohne
		// this
		Printer.promptTestOut(this, "compute shortest path algorithmus");

		this.initAlgorithmus(graph, source, target);

		return this.procedure();
	}

	/**
	 * Prüft die übergebenen Parameter
	 * @param graph Übergebener Graph
	 * @param source Startknoten
	 * @param target Zielknoten
	 */
	private void validArguments(Graph graph, String source, String target) {

		if ((graph.getNode(source) == null)) {
			throw new IllegalArgumentException("source uncorrect");

		} else if ((graph.getNode(target) == null)) {
			throw new IllegalArgumentException("target uncorrect");

		}

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
	protected abstract List<Node> procedure();

	/**
	 * Initialisiert den Algorithmus auf einen Graphen, einen Startknoten sowie
	 * auf ein Zielknoten. Es ist die einzige Methode um die sich eine konrekte
	 * Klasse Algorithmus kümmern muss.
	 * 
	 * @param graph
	 * @param source
	 * @param target
	 */
	private void initAlgorithmus(Graph graph, String source, String target) {
		Printer.promptTestOut(this, "initialize");
		Printer.promptTestOut(this, "set source: " + source.toString());
		Printer.promptTestOut(this, "set target: " + target.toString());
		this.setGraph(graph);
		this.source = this.getGraph().getNode(source);
		this.target = this.getGraph().getNode(target);
	}

	protected Node getSource() {
		return source;
	}

	protected Node getTarget() {
		return target;
	}

	// TODO Abstracte Mehtode die in den jeweiligen Algorithmen-Klassen prüft ob
	// der Graph für sie geeignet ist.
	// Beispiel, beim Bruskal-Algorithmus muss der Graph .... haben

}
