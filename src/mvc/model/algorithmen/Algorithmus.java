package mvc.model.algorithmen;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import utility.Printer;

public abstract class Algorithmus {
	
	// TODO command pattern für die Auswahl der Befehle?
	
	// TODO getResult() statt geShortestPath usw? Mit Prompt Ausgabe, was das Result gerade ist?

	// TODO Graph mit übergeben, so dass es von jedem Algortihmus nur ein Objekt geben muss und die interne
	// variable graph jedes mal geleert wird, vlt eine reset Algortihmus ? bzw im initilaize alle Variable zurück auf
	// 
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
