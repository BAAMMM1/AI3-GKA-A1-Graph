package mvc.model.graphGenerator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

/**
 * Die Klasse kann einen einfachen, ungerichteten und gewichteten Graphen
 * erstellen. Dies bedeutet, der Graph besitzt keine Mehrfachkanten und keine
 * Kringel. Die Anzahl an Knoten und Kanten ist variabel.
 *
 */
public class RandomSimple extends GraphGenerator{

	private Graph result;
	private List<Node> nodesList;
	private Random random;
	private int nodeSize;
	private int edgeSize;
	private int maxWeight;

	public RandomSimple() {
	}

	/**
	 * Diese Methode bietet die Funktionalität der Klasse an. Sie erstellt den
	 * einfachen, ungerichteten und gewichteten Graphen.
	 * 
	 * @param nodeSize
	 *            Anzahl an Knoten - Dabei gilt: nodes > 0
	 * @param edgeSize
	 *            Anzahlen an Kanten - Dabei gilt: (edges >= nodes - 1) &&
	 *            (edges <= (nodes * (nodes - 1)) / 2)
	 * @param maxWeight
	 *            Maximales Kantengewicht - Dabei gilt: (maxWeight > 0)
	 * @return Erstellten Graphen
	 */
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initGenerator(nodeSize, edgeSize, maxWeight);

		this.generateConnectedNodes();

		this.addSimpleEdgesToNodes();

		return result;
	}

	/**
	 * Prüft ob die Parameter korrekt sind
	 * 
	 * @param nodes
	 *            Knotenanzahl
	 * @param edges
	 *            Kantenanzahl
	 * @param maxWeight
	 *            Maximales Kantengewicht
	 */
	private void validArguments(int nodes, int edges, int maxWeight) {
		if (nodes <= 0) {
			throw new IllegalArgumentException("Error: Knotenanzahl muss über 0");
		} else if (edges < nodes - 1) {
			throw new IllegalArgumentException("Error: Kein zusammenhängender Graph möglich - Kanten zu gering");
		} else if (edges > (nodes * (nodes - 1)) / 2) {
			throw new IllegalArgumentException("Error: Kanten zu groß");
		} else if (maxWeight <= 0) {
			throw new IllegalArgumentException("Error: Kantengewichtung darf nicht negativ sein");
		}
	}

	/**
	 * Initialisiert den GraphGenerator für einen Durchlauf.
	 * 
	 * @param nodeSize
	 *            Knotenanzahl
	 * @param edgeSize
	 *            Kantenanzahl
	 * @param maxWeight
	 *            aximales Kantengewicht
	 */
	private void initGenerator(int nodeSize, int edgeSize, int maxWeight) {
		this.nodesList = new LinkedList<Node>();
		this.result = new MultiGraph("RandomSimple");
		this.random = new Random();
		this.nodeSize = nodeSize;
		this.edgeSize = edgeSize;
		this.maxWeight = maxWeight;
	}

	/**
	 * Erstellt einen zusammenhängenden Graphen mit Kantenanzahl = Knotenanzahl
	 * -1
	 */
	private void generateConnectedNodes() {
		this.addNode("firstNode");

		for (int i = 0; i < this.nodeSize - 1; i++) {
			/*
			 * Wählt zufällig einen Knoten aus den bereits vorhandenen Knoten
			 * aus und setzt einen neuen Knoten mit einer neuen Kanten an diesen
			 * Knoten. Dadurch entsteht ein zusammenhängender Graph.
			 */
			Node nodeRandomFromList = this.getRandomNodeFromList();
			Node nodeNew = this.addNode("Node" + i);

			this.addEdge(result, nodeRandomFromList, nodeNew);
			this.edgeSize--;

		}
	}

	/**
	 * Fügt dem Graph alle weiteren geforderten Knoten hinzu, sodass der Graph
	 * einfach bleibt. D.h ohne Kringel und Mehrfachkanten.
	 */
	private void addSimpleEdgesToNodes() {
		// Wenn nicht schlicht, dann Kringel und Mehrfachkanten erlaubt, dann
		// müsste das hier nicht

		for (int i = 0; i < this.edgeSize; i++) {

			/*
			 * Kringel: Die Kante darf nicht zwei mal den selben Knoten haben.
			 */
			Node node01 = this.getRandomNodeFromList();
			Node node02;
			do {
				node02 = this.getRandomNodeFromList();
			} while (node01 == node02);

			/*
			 * Mehrfachkanten: Es kann nicht zweimal die identische Kanten
			 * gesetzt werden oder eine Kante in die Gegenrichtung
			 */
			String edgeID01 = node01.getId()+ node02.getId();
			String edgeID02 = node02.getId()+ node01.getId();

			if ((result.getEdge((edgeID01)) == null) && (result.getEdge((edgeID02)) == null)) {
				this.addEdge(result, node01, node02);
			} else {
				i--;
				continue;
			}
		}
	}

	/**
	 * Fügt dem erstellten Graphen einen Knoten hinzu, setzt das ui.label und
	 * fügt den Knoten der Knotenliste hinzu.
	 * 
	 * @param name
	 * @return
	 */
	private Node addNode(String name) {
		result.addNode(name);

		Node node = result.getNode(name);
		node.setAttribute("ui.label", name);
		this.nodesList.add(node);

		return node;
	}

	/**
	 * Fügt dem erstellten Graphen eine Kante hinzu, setzt das ui.label und das
	 * Gewicht der Kante.
	 */
	private void addEdge(Graph graph, Node node01, Node node02) {
		String edgeID = node01.getId() + node02.getId();

		graph.addEdge(edgeID, node01, node02);
		Edge edge = graph.getEdge(edgeID);

		edge.setAttribute("weight", (Integer) getRandomWeight());
		edge.setAttribute("ui.label", edge.getAttribute("weight").toString());

	}

	/**
	 * Ermittelt aus der Liste der bereits vorhandenen Knoten des Graphen einen
	 * zufälligen Knoten aus.
	 * 
	 * @return zufällig gewählter Knoten
	 */
	private Node getRandomNodeFromList() {
		Collections.shuffle(nodesList);

		return nodesList.get(0);
	}

	/**
	 * Ermittelt eine Gewicht von 1 bis maximales Gewicht.
	 * 
	 * @return Zufällige Gewichtung
	 */
	private int getRandomWeight() {
		return random.nextInt(this.maxWeight) + 1;
	}

	public static void main(String[] args) {
		if(args.length == 3){
			RandomSimple generator = new RandomSimple();
			GraphFileExtensionHandler fileHandler = new GraphFileExtensionHandler();
			Graph graph = generator.procedure(Integer.valueOf(args[0]), Integer.valueOf(args[1]),
					Integer.valueOf(args[2]));
			fileHandler.saveGraph(graph, "db/random/random_" + args[0] + "_" + args[1] + "_" + args[2]);
		} else {
			System.out.println("<nodeSize>, <edgesSize>, <maxWeight>");
		}
		

	}

}
