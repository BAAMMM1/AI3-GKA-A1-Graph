package mvc.model.algorithmen.eulerTour;

import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import mvc.model.algorithmen.Algorithm;
import mvc.model.exceptions.IllegalDirectedGraph;
import scala.util.Random;
import utility.Printer;

/**
 * Diese Klasse definiert den grundlegenden Aufbau eines
 * EulerTour-Algorithmus. Ein EulerTour-Alogirthmus kann mit calculate()
 * ausgeführt werden und gibt als Rückgabewert den Eulerkreis als Kantenabfolge
 * an. Dort wird zunächst der übergebene Graph auf zulässigkeit geprüft.
 * 
 * Anschließen wird die jeweilige Handlungsvorschrift, die die Einzelschritten
 * des jeweiligen Algorithmus beinhaltet, ausgeführt. Dies ermöhlicht uns, dass
 * die konkreten Klassen von EulerTour-Algorithmus sich nur um ihre jeweilige
 * Handlungsvorschriftt kümmern müssen.
 *   
 */
public abstract class EulerTour extends Algorithm {

	public List<Edge> calculate(Graph graph) {
		this.validArguments(graph);

		Printer.prompt(this, "-------------------------------------");

		Printer.prompt(this, "compute eulertour algorithmus");
		Printer.prompt(this, "nodeSize: " + graph.getNodeSet().size() + " edgeSize: " + graph.getEdgeSet().size());

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
		 * Kantenanzahl muss mindestens Knotenanzahl sein, ansonsten ist kein Eulerkreis möglich
		 */
		if(graph.getEdgeSet().size() < graph.getNodeSet().size()){
			throw new IllegalArgumentException("Kantenanzahl muss mindesten Knotenanzahl sein");
		}
		
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
			
			/*
			 * Der Graph muss zusammenhängend sein
			 */
			if (edges.isEmpty()) {
				throw new IllegalArgumentException("graph must be related");
			}

			int grades = edges.size();

			for (int i2 = 0; i2 < edges.size(); i2++) {
				if (edges.get(i2).isLoop()) {
					grades += 1;
				}
			}

			/*
			 * Nach dem alle Kanten und Loops eines Knoten gezählt wurden, muss der Grad gerade sein
			 */
			if ((grades % 2) != 0) {
				throw new IllegalArgumentException("all node degree must be even");
			}
			
			
			
		}

		
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

	/**
	 * Diese Mehtode stellt die Handlungsvorschrift des jeweiligen Algorithmus da.
	 * @return
	 */
	protected abstract List<Edge> procedure();

	/**
	 * Ermittelt aus dem Graphen einen zufälligen Knoten.
	 * 
	 * @return Zufälliger Knoten aus dem Graphen
	 */
	protected Node getRandomNode() {
		return this.getGraph().getNode(this.getRandom(this.getGraph().getNodeSet().size()));
	}

	/**
	 * Ermittelt eine zufällig Zahl von (0 - (value - 1))
	 * 
	 * @param value
	 *            exklusives Maximum
	 * @return Zufällige Zahl
	 */
	protected int getRandom(int value) {
		Random random = new Random();
		return random.nextInt(value);
	}

	/**
	 * Diese Methode fügt dem Graphen alle Kanten des Eulerkreis wieder hinzu.
	 */
	protected void addEdgesBackToGraph(List<Edge> edges) {
		for (int i = 0; i < edges.size(); i++) {

			this.addEdge(edges.get(i));
		}
	}

	/**
	 * Fügt dem Graph eine übergebene Kante hinzu.
	 * 
	 * @param edge
	 *            Kante die dem Graph hinzugefügt werden soll
	 */
	protected void addEdge(Edge edge) {
		Node edgeSource = edge.getSourceNode();
		Node edgeTarget = edge.getTargetNode();
		String id = edge.getId();

		this.getGraph().addEdge(id, edgeSource, edgeTarget);

		if (edge.getAttribute("ui.label") != null) {
			this.getGraph().getEdge(id).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
		}
		
		if (edge.getAttribute("weight") != null) {
			this.getGraph().getEdge(id).addAttribute("weight", (int) edge.getAttribute("weight"));
		}

	}

}
