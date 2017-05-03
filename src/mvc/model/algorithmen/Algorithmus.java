package mvc.model.algorithmen;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import utility.Printer;

public abstract class Algorithmus {

	/**
	 * Diese Methode berechnet die interne Handlungsvorschrift aus und führt somit
	 * den Algorithmus aus.
	 * 
	 * @param source Startknoten
	 * @param target Zielknoten
	 * @return Graph auf den der Algorithmus angewandt wurde
	 */
	public Graph compute(Node source, Node target) {
		Printer.prompt(this, "-------------------------------------");
		Printer.prompt(this, "compute algorithmus");

		return this.procedure(source, target);
	}

	/**
	 * Diese Mehtode stellt die interne Handlungsvorschrift eines jeden Algorithmus,
	 * welche die Einzelschritten definiert, dar.
	 * 
	 * @param source Startknoten
	 * @param target Zielknoten
	 * @return Graph auf den der Algorithmus angewandt wurde
	 */
	protected abstract Graph procedure(Node source, Node target);

}
