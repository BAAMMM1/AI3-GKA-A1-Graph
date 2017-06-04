package mvc.model.graphGenerator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class RandomEulerWithOutKringel extends GraphGenerator {

	private Graph result;
	private List<Node> unConnected;

	public RandomEulerWithOutKringel() {
	}

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initialize(nodeSize, edgeSize, maxWeight);

		Node start = this.getRandomNode();
		this.unConnected.remove(start);
		System.out.println("start: " + start.toString());

		Node current = start;
		Node next = null;
		int counter = 0;

		while (edgeSize > 1) {

			if (this.unConnected.isEmpty()) {

				do {
					System.out.println("do1");
					next = this.getRandomNode();
					
				} while (current == next);

				if (edgeSize == 2) {
					
					do {
						next = this.getRandomNode();
					} while (current == next || start == next);
				}

			} else {
				next = this.unConnected.get(0);
				this.unConnected.remove(next);
			}

			System.out.println("current: " + current.toString());
			System.out.println("next: " + next.toString());

			this.result.addEdge("e" + counter++, current, next);
			current = next;
			edgeSize--;
		}

		System.out.println("current: " + current.toString());
		System.out.println("start: " + start.toString());
		this.result.addEdge("e" + counter++, current, start);

		/*
		 * Knotengrade ausgeben
		 */

		return result;
	}

	private Node getRandomNode() {
		Node node = result.getNode("Node" + this.getRandom(result.getNodeSet().size()));

		return node;
	}

	private int getRandom(int value) {
		Random random = new Random();
		return random.nextInt(value);
	}

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
			throw new IllegalArgumentException("Kantenanzahl muss <= Knotenanzahl sein");

		} else if (nodeSize < 2) {
			throw new IllegalArgumentException("Ohne Kringel werden mindestens zwei Knoten benötigt");

		} else if (((edgeSize-nodeSize) %2) != 0) {
			throw new IllegalArgumentException("Ohne Kringel: Kantenanzahl muss edgeSize-nodeSize %2 == 0");
			
		} 


	}

	private void initialize(int nodeSize, int edgeSize, int maxWeight) {
		this.result = new MultiGraph("RandomEuler");
		this.unConnected = new LinkedList<Node>();

		for (int i = 0; i < nodeSize; i++) {
			this.result.addNode("Node" + i);
			this.unConnected.add(this.result.getNode("Node" + i));
		}

	}

}
