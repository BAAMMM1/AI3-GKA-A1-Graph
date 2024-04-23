package dev.graumann.graphsearch.mvc.model.algorithmen.eulerTour;

import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import dev.graumann.graphsearch.utility.Printer;

/**
 * Der Algorithmus von Hierholzer findet in einem zusammenhängenden,
 * ungerichteten Graphen und bei dem jeder Knoten einen geraden Knotengrad
 * besitzt, einen Eulerkreis.
 * 
 * (1) Vorgang: Der Algorithmus beginnt bei einem zufälligen ausgewählten
 * Startknoten.
 * 
 * (2) Von dort an fängt er an einen Zyklus in dem Graphen zu durchlaufen, bis
 * er wieder bei dem Startknoten angekommen ist.
 * 
 * (3)Anschließend wird ein Knoten - der noch eine weitere Kante hat, die nicht
 * zum Zyklus gehört - aus der Knotenmenge des Zyklus ermittelt.
 * 
 * Vom diesem Knoten aus wird ein weiterer Zyklus erstellt. Wenn noch weitere
 * Kanten im Graph vorhanden sind, wieder zu (3).
 * 
 * Ansonsten wird der Eulerkreis anhand der Zyklen ermittelt.
 * 
 * Dies geschiet indem ausgehen vom ersten Zyklus der jeweilige nächste Zyklus
 * in diesen integriert wird, an die Setlle an der der jeweilige nächste Zyklus
 * startet.
 *
 */
public class Hierholzer extends EulerTour {

	private List<List<Node>> cyclesNodes;
	private List<List<Edge>> cyclesEdges;
	
	private List<Edge> eulerCircuitEdges;
	private List<Node> eulerCircuitNodes;

	/**
	 * Diese Mehtode stellt die Handlungsvorschrift des Hierholzer-Algorithmus
	 * da.
	 */
	@Override
	protected List<Edge> procedure() {
		long timeStart = System.currentTimeMillis();
		
		this.initialize();

		/*
		 * Berechnung der Zyklen in dem Graphen
		 */
		this.calculateEulerParts();

		/*
		 * Berechnung des Eulerkreis aus den jeweiligen Zyklen
		 */
		this.calculateEulerCircle();

		/*
		 * Dem Graphen alle Kanten wieder hinzufügen
		 */
		this.addEdgesBackToGraph(eulerCircuitEdges);

		/*
		 * Ergebnisausgabe
		 */
		this.promptResult();
		
		long timeEnd = System.currentTimeMillis();
		
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));

		return this.eulerCircuitEdges;
	}

	/**
	 * Initialisiert den Algortihmus Dafür werden zwei Listen - die den
	 * Eulerkreis einmal in Kanten und in Knoten notation darstellt - benötigt.
	 * 
	 * Ebenso werden zwei Listen die die jeweiligen Zyklen in Nodes und Kanten
	 * darstellen benötigt.
	 * 
	 */
	private void initialize() {
		this.eulerCircuitEdges = new LinkedList<Edge>();
		this.eulerCircuitNodes = new LinkedList<Node>();

		this.cyclesNodes = new LinkedList<List<Node>>();
		this.cyclesEdges = new LinkedList<List<Edge>>();

	}

	/**
	 * Diese Mehtode ermittelt die jeweiligen Zyklen innerhab des Graphen, indem
	 * so lange Zyklen berechnet werden bis der Graph keine Kanten mehr besitzt.
	 */
	private void calculateEulerParts() {

		/*
		 * Start und Ende des ersten Zyklus bestimmen
		 */
		Node randomStart = this.getRandomNode();

		Node currentCycleStartAndEnd = randomStart;
		/*
		 * Aktuelle Position des ersten Zyklus setzten
		 */
		Node currentNode = currentCycleStartAndEnd;

		/*
		 * Solange noch nicht alle Kante angeschaut wurden, ermittle alle Zyklen
		 */
		while (!this.getGraph().getEdgeSet().isEmpty()) {

			LinkedList<Node> currentCycleNodes = new LinkedList<Node>();
			LinkedList<Edge> currentCycleEdges = new LinkedList<Edge>();

			do {
				/*
				 * Aktuellen Knoten dem aktuellen Zyklus hinzufügen
				 */
				currentCycleNodes.add(currentNode);

				/*
				 * Alle Kanten des aktuellen Knoten ermittelnt
				 */
				List<Edge> currentNodeEdges = new LinkedList<Edge>(currentNode.getEdgeSet());

				/*
				 * Wenn keine Kante mehr vorhanden ist, dann ist der Graph nicht
				 * zusammenhängend.
				 */
				if (currentNodeEdges.isEmpty()) {
					throw new IllegalArgumentException("graph must be related");
				}

				/*
				 * Ansonsten nehme irgendeine Kante des aktuellen Knoten
				 */
				Edge nextEdge = currentNodeEdges.get(this.getRandom(currentNodeEdges.size()));

				/*
				 * Kante dem aktuellen Kantenzyklus hinzufügen
				 */
				currentCycleEdges.add(nextEdge);

				/*
				 * Kante als gesehen makieren
				 */
				this.getGraph().removeEdge(nextEdge);

				/*
				 * Nächste Knoten ermittelt
				 */
				if (nextEdge.getSourceNode() != currentNode) {
					currentNode = nextEdge.getSourceNode();
				} else {
					currentNode = nextEdge.getTargetNode();
				}

				/*
				 * Solange bis der Zyklus vollständig ist, indem er am
				 * Startpunkt wieder angekommen ist
				 */
			} while (currentNode != currentCycleStartAndEnd);

			/*
			 * Letzter Knoten im Zyklus in der Start Edge braucht hier nicht
			 * extra hinzugefügt werden
			 */
			currentCycleNodes.add(currentCycleStartAndEnd);

			/*
			 * Aktuellen Zyklus zur Zyklusliste hinzufügen
			 */
			this.cyclesNodes.add(currentCycleNodes);
			this.cyclesEdges.add(currentCycleEdges);

			/*
			 * Identifizieren des nächsten Zyklusstarts, nur möglich wenn es
			 * noch Knoten mit Kanten gibt. Itteration über alle Knoten in der
			 * Zyklenliste, finde den mit Grad größer 0 und nimm ihn als
			 * aktuellerStart und nextNode;
			 */
			if (!this.getGraph().getEdgeSet().isEmpty()) {

				for (int i = 0; i < this.cyclesNodes.size(); i++) {

					for (int i2 = 0; i2 < this.cyclesNodes.get(i).size(); i2++) {

						if (this.cyclesNodes.get(i).get(i2).getEdgeSet().size() > 0) {
							currentCycleStartAndEnd = this.cyclesNodes.get(i).get(i2);
							currentNode = currentCycleStartAndEnd;
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Diese Methode berechnet den Eulerkreis aus allen Zyklen innerhalb des
	 * Graphen Dies geschiet indem ausgehen vom ersten Zyklus der jeweilige
	 * nächste Zyklus in diesen integriert wird, an die Setlle an der der
	 * jeweilige nächste Zyklus startet.
	 */
	private void calculateEulerCircle() {

		/*
		 * Zunächst wird dem Eulerkreis alle Knoten/Kanten des ersten Zyklus
		 * hinzugefügt
		 */
		this.eulerCircuitNodes.addAll(this.cyclesNodes.get(0));
		eulerCircuitEdges.addAll(this.cyclesEdges.get(0));

		/*
		 * Anschließent wird jeder weiter Zyklus in den Euklerkreis an die
		 * Stelle, an der der jeweilige nächste Zyklus startet hinzugefügt.
		 */
		for (int i = 1; i < this.cyclesNodes.size(); i++) {

			/*
			 * Start des nächsten Zyklus, index im eulerkreis
			 */
			int index = this.eulerCircuitNodes.indexOf(this.cyclesNodes.get(i).get(0));

			this.eulerCircuitNodes.remove(index);
			this.eulerCircuitNodes.addAll(index, this.cyclesNodes.get(i));

			this.eulerCircuitEdges.addAll(index, this.cyclesEdges.get(i));

		}

	}

	/**
	 * Diese Mehtode gibt das Ergebnis des Algorithmus auf der Console aus.
	 */
	private void promptResult() {
		Printer.prompt(this, "Node-Zyklen: \t" + this.cyclesNodes.toString());
		Printer.prompt(this, "Node-Eulerkreis: \t" + this.eulerCircuitNodes.toString());
		Printer.prompt(this, "");
		Printer.prompt(this, "Edge-Zyklen: \t" + this.cyclesEdges.toString());
		Printer.prompt(this, "Edge-Eulerkreis: \t" + eulerCircuitEdges.toString());
	}

	/**
	 * Diese Mehtode gibt die für den jeweiligen Durchlauf des Algortihmus
	 * berechneten Zyklen zurück.
	 * 
	 * @return Liefert alle berechneten Zyklen des Graphen zurück
	 */
	public List<List<Edge>> getEulerParts() {
		return this.cyclesEdges;
	}

	@Override
	public String toString() {
		return String.format("Hierholzer");
	}

}
