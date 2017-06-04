package mvc.model.algorithmen.eulerCircle;

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
 * ausgewählte Knoten is.
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
 * Wie wird eine Brücke fstgestellt?
 * 
 * 
 * 
 * 
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
public class Fleury extends EulerCircle {

	private LinkedList<Edge> edgePath;
	private Node startAndEndNode;
	private Node currentPosition;
	private Edge nextEdge;

	@Override
	protected List<Edge> procedure() {

		/*
		 * Setzt einem zufällig gewählten Startknoten und setzt die
		 * currentPosition auf diese Knoten.
		 */
		this.initialize();

		/*
		 * Ermittelt den Eulerkreis
		 */
		this.calculateEulerCircle();

		/*
		 * Dem Graphen, die gelöschten Kanten wieder hinzufügen
		 */
		this.addEdgesBackToGraph();

		/*
		 * Ergebnisausgabe
		 */
		Printer.prompt(this, this.edgePath.toString());

		return this.edgePath;
	}

	private void calculateEulerCircle() {

		/*
		 * So lange alle Kanten angeschaut wurden
		 */
		while (!this.getGraph().getEdgeSet().isEmpty()) {

			List<Edge> edges = new LinkedList<Edge>();
			edges.addAll(currentPosition.getEdgeSet());

			if (edges.size() > 1) {

				int random = this.getRandom(edges.size());
				Printer.promptTestOut(this, "" + random);

				/*
				 * Wähle eine Kante die keine Brücke ist
				 */
				while (this.isBridge(edges.get(random), currentPosition.toString(), startAndEndNode.toString())) {
					random = this.getRandom(edges.size());
					Printer.promptTestOut(this, "" + random);
				}

				/*
				 * Damit die Kante auch wieder im richten Graphen gefunden wird,
				 * da sie in isBridge einmal gelöscht und wieder hinzugefügt
				 * wird, ist die Kante nicht das selbe Objekt
				 */
				nextEdge = this.getGraph().getEdge(
						edges.get(random).getId());

			} else {
				/*
				 * Wenn nur noch eine Kante vorhanden ist, dann gehe sie
				 */
				nextEdge = currentPosition.getEdge(0);
			}

			edgePath.add(nextEdge);
			this.getGraph().removeEdge(nextEdge);

			if (nextEdge.getSourceNode() != currentPosition) {
				currentPosition = nextEdge.getSourceNode();
			} else {
				currentPosition = nextEdge.getTargetNode();
			}

		}

	}

	private void initialize() {
		this.edgePath = new LinkedList<Edge>();
		this.startAndEndNode = this.getRandomStartNode();
		this.currentPosition = startAndEndNode;
		this.nextEdge = null;

	}

	/*
	 * Gibt es auch eine effizientere Implementierung?
	 */
	/**
	 * Diese Mehtode stellt fest, ob es sich bei der Kante um eine Schnittkante
	 * handelt.
	 * 
	 * @param edge
	 * @param position
	 * @param startNode
	 * @return
	 */
	private boolean isBridge(Edge edge, String position, String startNode) {
		ShortestPath bfs = ShortestPathFactory.getInstance("BreadthFirstSearch");
		Node edgeSource = edge.getSourceNode();
		Node edgeTarget = edge.getTargetNode();
		String name = edge.getId();

		// Sicherstellen, dass die Kante des Graphen verwendet wird
		edge = this.getGraph().getEdge(name);

		this.getGraph().removeEdge(edge);

		if (bfs.calculate(this.getGraph(), position, startNode).isEmpty()) {
			this.getGraph().addEdge(name, edgeSource, edgeTarget);

			if (edge.getAttribute("ui.label") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
			}
			if (edge.getAttribute("weight") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", (int) edge.getAttribute("weight"));
			}

			return true;
		} else {

			this.getGraph().addEdge(name, edgeSource, edgeTarget);
			if (edge.getAttribute("ui.label") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
			}
			if (edge.getAttribute("weight") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", (int) edge.getAttribute("weight"));
			}

			return false;
		}

	}

	private Node getRandomStartNode() {
		return this.getGraph().getNode(this.getRandom(this.getGraph().getNodeSet().size()));
	}

	private int getRandom(int value) {
		Random random = new Random();
		return random.nextInt(value);
	}

	@Override
	public String toString() {
		return String.format("Fleury");
	}

	private void addEdgesBackToGraph() {
		for (int i = 0; i < this.edgePath.size(); i++) {
			String name = edgePath.get(i).getId();
			Node source = edgePath.get(i).getSourceNode();
			Node target = edgePath.get(i).getTargetNode();
			this.getGraph().addEdge(name, source, target);

			if (edgePath.get(i).getAttribute("ui.label") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label",
						edgePath.get(i).getAttribute("ui.label").toString());
			}
			if (edgePath.get(i).getAttribute("weight") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", (int) edgePath.get(i).getAttribute("weight"));
			}

		}
	}
}
