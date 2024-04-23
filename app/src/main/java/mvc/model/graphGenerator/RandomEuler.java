package mvc.model.graphGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

/**
 * Die Klasse RandomEuler erzuegt einen ungerichteten, zusammenhängdenen Graphen
 * bei dem jeder Knotengrad gerade ist. Die Knotengrade sind zufällig auf die
 * jeweiligen Knoten verteilt.
 * 
 * Vorgang: Zunächst werden dem Graphen alle Knoten hinzugefügt, welche
 * ebenfalls in einer unConnected-Liste geführt werden.
 * 
 * Anschließen werden die Kanten gesetzt. Es werden zuerst alle unConncted
 * verbunden und anschließen werden zufällige Knoten ausgewählt. Diese geschiet
 * solange der RandomEuler keine weiteren Kanten außer einer zuverfügung stehen.
 * Die letzte Kante wird wieder zurück zum Start gesetzt, damit ein Eulerkreis
 * ensteht.
 */
public class RandomEuler extends GraphGenerator {

	private Graph result;
	private List<Node> unConnected;
	private int edgeSize;
	private int maxWeight;

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		/*
		 * Knotensetzten
		 */
		this.initialize(nodeSize, edgeSize, maxWeight);

		/*
		 * Kanten setzten
		 */
		this.setEdges();

		/*
		 * Knotengrade ausgeben
		 */
		this.promptResult();

		return result;
	}

	/**
	 * Überprüft die übergebenen Parameter. Für einen Eulerkreis wird mindestens
	 * edgeSize == nodeSize benötigt.
	 * 
	 * @param nodeSize
	 * @param edgeSize
	 * @param maxWeight
	 */
	private void validArguments(int nodeSize, int edgeSize, int maxWeight) {
		/*
		 * Es wird mindestens ein Knoten benötigt
		 */
		if (nodeSize < 0) {
			throw new IllegalArgumentException("Knotenanzahl muss > 0 sein");
	
			/*
			 * Damit mindestens ein Kreis in einem zusammenhängenden Graphen
			 * enstehen kann, muss die edgSize mindestens der nodeSize sein.
			 */
		} else if (edgeSize < nodeSize) {
			throw new IllegalArgumentException("Kantenanzahl muss >= Knotenanzahl sein");
	
		}
	
	}

	/**
	 * Initialisiert den RandomEuler. Dem Graphen werden alle Knoten
	 * hinzugefügt, welche ebenfalls in einer unConnected Liste geführt werden.
	 * 
	 * @param nodeSize
	 *            Knotenanzahl den der randomisierte Graph haben soll.
	 * @param edgeSize
	 *            Kantenazahl den der randomisierte Graph haben soll.
	 * @param maxWeight
	 *            Maximalesgewicht den der randomisierte Graph haben soll.
	 */
	private void initialize(int nodeSize, int edgeSize, int maxWeight) {
		this.edgeSize = edgeSize;
		this.result = new MultiGraph("RandomEuler");
		this.unConnected = new LinkedList<Node>();
		this.maxWeight = maxWeight;
	
		for (int i = 0; i < nodeSize; i++) {
			this.result.addNode("Node" + i);
			this.unConnected.add(this.result.getNode("Node" + i));
		}
	
	}

	/**
	 * Diese Methode setzt die Kanten zwischen den Knoten. Zunächst werden alle
	 * unConnected mit einander Verbunden, anschließen werden zufällige Knoten
	 * ausgewählt und Kanten gesetzt. Die geschiet bis nur noch eine Kante
	 * vorhanden ist. Diese Kante wird zurück zum Startpunkt gesetzt. Dadurch
	 * enthält jeder Knoten bei jedem Durchgang eine Kante hinein und eine Kante
	 * hinaus. Somit ensteht ein Eulerkreis.
	 */
	private void setEdges() {

		/*
		 * Zufälliger Start wird gewählt
		 */
		Node start = this.getRandomNode();
		this.unConnected.remove(start);

		Node current = start;
		Node next;

		/*
		 * Setzte Kante solange nur noch eine übrig ist,
		 */
		int id = 0;
		while (this.edgeSize > 1) {

			/*
			 * Falls es noch Knoten gibt, die noch nicht mit dem restlichen
			 * Graphen verbunden sind, wird einer dieser Knoten ausgewählt.
			 * Ansonsten wird ein zufälliger Knoten gewählt.
			 */
			if (this.unConnected.isEmpty()) {
				next = this.getRandomNode();
			} else {
				next = this.unConnected.get(0);
				this.unConnected.remove(next);
			}

			
			this.addEdge(id++, current, next);
			current = next;
			this.edgeSize--;
		}

		/*
		 * Letzte Kante muss zurück zum Start gehen
		 */
		this.addEdge(id++, current, start);
		

	}

	/**
	 * Gibt die jeweiligen Knotengrade jedes Knoten aus
	 */
	private void promptResult() {
		System.out.print("[ ");
		for (int i = 0; i < this.result.getNodeSet().size(); i++) {
			int degree = 0;
	
			for (Edge edge : this.result.getNode(i).getEdgeSet()) {
				if (edge.isLoop()) {
					degree += 2;
				} else {
					degree++;
				}
			}
	
			System.out.print(degree + " ");
		}
		System.out.println("]");
	}

	/**
	 * Ermittelt aus dem Graphen einen zufälligen Knoten.
	 * 
	 * @return Zufälliger Knoten aus dem Graphen
	 */
	private Node getRandomNode() {
		return result.getNode(this.getRandom(result.getNodeSet().size()));
	}

	/**
	 * Ermittelt eine zufällig Zahl von (0 - (value - 1))
	 * 
	 * @param value
	 *            exklusives Maximum
	 * @return Zufällige Zahl
	 */
	private int getRandom(int value) {
		Random random = new Random();
		return random.nextInt(value);
	}
	
	/**
	 * Fügt dem Graph eine Kante hinzu
	 * @param counter Id der Kante
	 * @param source Source der Kante
	 * @param target Target der Kante
	 */
	private void addEdge(int counter, Node source, Node target) {
		this.result.addEdge("e" + counter, source, target);
		
		Edge edge = this.result.getEdge("e" + (counter));
		edge.setAttribute("weight", (Integer) getRandomWeight());
		edge.setAttribute("ui.label", edge.getAttribute("weight").toString());
	}

	/**
	 * Ermittelt eine Gewicht von 1 bis maximales Gewicht.
	 * 
	 * @return Zufällige Gewichtung
	 */
	private int getRandomWeight() {
		Random random = new Random();
		if(this.maxWeight > 0){
			return random.nextInt(this.maxWeight) + 1;
		} else {
			return 0;
		}
		
	}
	
	/**
	 * Erstellt randomisierte Graphen für den Testdurchlauf.
	 * @param args
	 */
	public static void main(String[] args) {
		GraphFileExtensionHandler fileHandler = new GraphFileExtensionHandler();
		GraphGenerator randomEuler = GraphGeneratorFactory.getInstance("Euler");
		
		for (int counter = 0; counter < 50; counter++) {
			
			Random random = new Random();
			int nodeSize = random.nextInt(2500)+1;
			int edgeSize = random.nextInt(25000) + nodeSize;
			Graph graph = randomEuler.generate(nodeSize, edgeSize, 0);
			fileHandler.saveGraph(graph, "db/testCases/eulercircle/randomEulerGraph" + counter);
			System.out.println("");
			System.out.println(graph.getNodeSet().size());
			System.out.println(graph.getEdgeSet().size());
			
		}
		

	}

}
