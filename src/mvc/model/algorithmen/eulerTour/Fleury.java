package mvc.model.algorithmen.eulerTour;

import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import mvc.model.algorithmen.shortestPath.ShortestPath;
import mvc.model.algorithmen.shortestPath.ShortestPathFactory;
import scala.util.Random;
import utility.Printer;

/**
 * Der Algorithmus von Fleury findet in einem zusammenhängend, ungerichtet
 * Graphen und bei dem jeder Knoten einen gerade Knotengrad besitzt, einen
 * Eulerkreis.
 * 
 * Vorgang: Der Algorithmus beginnt bei einem zufällig ausgewählten Startknoten,
 * der auch den Endknoten darstellt und im ersten Schritt der aktuelle
 * ausgewählte Knoten ist.
 * 
 * (1)So lange alle Kanten erfasst wurden:
 * 
 * Sehe alle Kanten des aktuellen Knoten an. Wenn der Knoten mehr Kanten als 1
 * hat, dann hat er mindesten eine Kante die keine Brücke darstellt.
 * 
 * Eine Brücke ist eine besondere Kante. Sie verbindet zwei Mengen von
 * zusammenhängenden Knoten der Graphen. Falls diese Brücke überquert wird, gibt
 * es keine Möglichkeit mehr zur anderen zusammenhängenden Knotenmenge
 * zugelangen.
 * 
 * Eine Brücke wird festgestellt, in dem die Kante aus dem Graph entfernt wird
 * und anschließend geprüft wird, ob es möglich ist von der aktuellen Position
 * (ein Knoten) wieder zurück zum StartAndEndknoten zugelangen. Ist dies nicht
 * möglich ist die Kante eine Brücke. Nach der Prüfung wird dem Graph die Kante
 * wieder hinzugefügt.
 * 
 * Deshalb darf keine Brücke genommen werden. Dadurch werden alle Knoten im
 * Graph erreicht.
 * 
 * => Wenn (KantenSize des aktuellen Knoten > 1) Wähle eine Kante die keine
 * Brücke ist, ansonsten wähle seine letzte Kante.
 * 
 * Anschließen wird diese Kanten überquert. Der aktuelle Knoten wird zum
 * gegenüberliegenden Knoten. Zurück zu (1)
 * 
 * Wenn alle Kanten erfasst wurden, ist der Eulerkreis ermittelt. *
 * 
 * Voraussetzung: - Graph muss zusammenhängend sein - Graph muss ungerichtet
 * sein - Jede Kanten des Graphen muss eine gerade Anzahl an Knotengrad besitzen
 * 
 * @author Chris
 *
 */
public class Fleury extends EulerTour {

	private LinkedList<Edge> eulerCircuit;
	private Node startAndEndNode;
	private Node currentPosition;
	private Edge nextEdge;

	/**
	 * Diese Mehtode stellt die Handlungsvorschrift des Fleury-Algortihmus da.
	 */
	@Override
	protected List<Edge> procedure() {
		long timeStart = System.currentTimeMillis();

		/*
		 * Setzt einem zufällig gewählten Startknoten und setzt die
		 * currentPosition auf diese Knoten.
		 */
		this.initialize();

		/*
		 * Ermittelt den Eulerkreis
		 */
		this.calculateEulerCircuit();

		/*
		 * Dem Graphen, die gelöschten Kanten wieder hinzufügen
		 */
		this.addEdgesBackToGraph(this.eulerCircuit);

		/*
		 * Ergebnisausgabe
		 */
		Printer.prompt(this, this.eulerCircuit.toString());
		
		long timeEnd = System.currentTimeMillis();
		
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));

		return this.eulerCircuit;
	}

	/**
	 * Diese Methode initialisiert den Algorithmus. Dafür wurd ein zufälliger
	 * Startknoten ausgewählt, der ebenson der Endknoten ist.
	 */
	private void initialize() {
		this.eulerCircuit = new LinkedList<Edge>();
		this.startAndEndNode = this.getRandomNode();
		this.currentPosition = startAndEndNode;

	}

	/**
	 * Der Graph wird vom zufällig gewählten Startknoten über alle Kanten hinweg
	 * gelaufenb bis die letzte Kante wieder zum Starknoten führt. Der gelaufene
	 * Weg - welcher den Eulerkreis darstellt - wird in der im edgePath
	 * festgehalten.
	 */
	private void calculateEulerCircuit() {

		/*
		 * So lange alle Kanten noch nicht angeschaut wurden
		 */
		while (!this.getGraph().getEdgeSet().isEmpty()) {

			List<Edge> currentPositionEdges = new LinkedList<Edge>();
			currentPositionEdges.addAll(currentPosition.getEdgeSet());

			/*
			 * Wenn der Graph nicht zusammenhhängend ist, besitzt der Graph noch
			 * weitere Kanten in einer Knotenmenge, die nicht ereicht werden
			 * können und die aktuelle Position besitzt keine weiteren Kanten
			 * mehr
			 */
			if (currentPositionEdges.isEmpty()) {
				// this.addEdgesBackToGraph();
				throw new IllegalArgumentException("graph must be related");
			}

			/*
			 * Wenn der Knoten mehr als eine Kante hat, dann nehme keine Brücke
			 * als nächste Kante
			 */
			if (currentPositionEdges.size() > 1) {

				Edge randomEdge;
				/*
				 * Wähle eine Kante die keine Brücke ist
				 */
				do {
					randomEdge = currentPositionEdges.get(this.getRandom(currentPositionEdges.size()));
				} while (this.isBridge(randomEdge, currentPosition.toString()));

				/*
				 * Damit die Kante auch wieder im richten Graphen gefunden wird,
				 * da sie in isBridge einmal gelöscht und wieder hinzugefügt
				 * wird, ist die Kante nicht das selbe Objekt
				 */
				nextEdge = this.getGraph().getEdge(randomEdge.getId());

			} else {
				/*
				 * Wenn nur noch eine Kante vorhanden ist, dann gehe sie
				 */
				nextEdge = currentPosition.getEdge(0);
			}

			/*
			 * Die Kante wird aus dem Graph entfernt, damit sie nicht erneut
			 * angeschaut wird
			 */
			eulerCircuit.add(nextEdge);
			this.getGraph().removeEdge(nextEdge);

			/*
			 * Auswahl des nächsten Knoten
			 */
			if (nextEdge.getSourceNode() != currentPosition) {
				currentPosition = nextEdge.getSourceNode();
			} else {
				currentPosition = nextEdge.getTargetNode();
			}

		}

	}

	/**
	 * Diese Mehtode stellt fest, ob es sich bei der Kante um eine Schnittkante
	 * handelt. D.h Es wird geprüft, ob diese Kante zwei Mengen von
	 * zusammenhängenden Knoten, die ohne diese Kante nicht mehr voneinander
	 * ereichbar sind, verbindet.
	 * 
	 * @param edge
	 *            Kante die aus eine Brücke geprüft werden soll
	 * @param position
	 *            Knotenposition des Algorithmus
	 * @return true - falls Kante eine Brückte ist, false - falls Kante keine
	 *         Brücke ist
	 */
	private boolean isBridge(Edge edge, String position) {
		ShortestPath bfs = ShortestPathFactory.getInstance("BreadthFirstSearch");

		/*
		 * Sicherstellen, dass die Kante des Graphen verwendet wird. Durchs
		 * löschen und hinzufügen verändern sich die Edge-Objekte wo mit man
		 * dann die Kante aus dem Graph herausnehmen muss, welche die ID der
		 * übergebenen Kante besitzt.
		 */
		edge = this.getGraph().getEdge(edge.getId());

		this.getGraph().removeEdge(edge);

		if (bfs.calculate(this.getGraph(), position, this.startAndEndNode.toString()).isEmpty()) {
			this.addEdge(edge);
			return true;

		} else {
			this.addEdge(edge);
			return false;
		}

	}

	@Override
	public String toString() {
		return String.format("Fleury");
	}
}
