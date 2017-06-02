package mvc.model.graphGenerator;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import scala.util.Random;

public class RandomEuler3 extends GraphGenerator {

	private Graph result;
	private List<Node> nodes;
	private List<Node> uneven;
	private List<Node> unevenB;
	private int nodeSize;
	private int edgeSize;
	private int maxWeight;
	private LinkedList<Node> unConnected;

	public RandomEuler3() {
	}

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initialize(nodeSize, edgeSize, maxWeight);

		do {

			if (this.edgeSize == this.unConnected.size()) {

				Node circleStart = this.unConnected.get(this.getRandom(this.unConnected.size()));
				System.out.println("Start: " + circleStart.toString());

				Node node1 = null;
				Node node2 = null;
				while (this.edgeSize > 0) {

					boolean run = true;
					do {

						if (this.unConnected.contains(circleStart)) {
							System.out.println(this.unConnected.contains(circleStart));
							node1 = circleStart;
						} else {
							System.out.println(this.unConnected.contains(circleStart));
							node1 = node2;
						}

						if (this.unConnected.size() > 0) {
							node2 = this.unConnected.get(this.getRandom(this.unConnected.size()));
						} else {
							node2 = circleStart;
						}

						System.out.println(node1.toString());
						System.out.println(node2.toString());

						//System.out.println(node1.getEdgeBetween(node2));

						if ((node1 != node2) && (node1.getEdgeBetween(node2) == null)) {
							run = false;
						}

					} while (run);

					System.out.println(node1.toString());
					System.out.println(node2.toString());

					this.addEdge(node1, node2);
					this.unConnected.remove(node1);
					this.unConnected.remove(node2);
					
					System.out.println(unConnected.size());
					System.out.println(this.edgeSize);

				}

			} else {

				while (this.edgeSize >= this.unConnected.size() + 3) {
					int circleSize = this.getCircleSizeRandom(this.edgeSize - (this.unConnected.size()));
					System.out.println("CircleSize: " + circleSize);

					this.edgeSize = this.edgeSize - circleSize;
					if (this.edgeSize < this.unConnected.size() + 3) {
						circleSize = circleSize + (this.edgeSize - this.unConnected.size());
						this.edgeSize = this.unConnected.size();
					}
					
					if(this.edgeSize<3){
						circleSize = circleSize + this.edgeSize;
						this.edgeSize = 0;
					}

					System.out.println("edgeSize: " + this.edgeSize);

					//Start darf nicht vollständigen Knoten grad haben)
					Node start = this.result.getNode(this.getRandom(this.result.getNodeSet().size()));
					
					int maxgrad;
					if(this.nodeSize%2 == 0){
						maxgrad = this.nodeSize - 2;
					} else {
						maxgrad = this.nodeSize - 1;
					}
					
					while(start.getEdgeSet().size() == maxgrad){
						start = this.result.getNode(this.getRandom(this.result.getNodeSet().size()));
					}
					
					List<Node> startNachbarn = new LinkedList<Node>();
					
					Iterator<Node> it = start.getNeighborNodeIterator();
					while(it.hasNext()){
						startNachbarn.add(it.next());
					}
					
					Node node1;
					Node node2 = null;
					Edge edge1 = null;
					Edge edge2 = null;
					for (int i = 0; i < circleSize; i++) {	
						
						if(i == 0){							
							node1 = start;
						} else {
							node1 = node2;
						}
						
						
						do {
							node2 = this.result.getNode(this.getRandom(this.result.getNodeSet().size()));
							
							if(i == circleSize-2){
								while(startNachbarn.contains(node2)){
									node2 = this.result.getNode(this.getRandom(this.result.getNodeSet().size()));
								}
							}
							
							if(i == circleSize-1){
								node2 = start;
							}
							System.out.println("i: " + i);
							System.out.println("circleSize: " + circleSize);
							System.out.println("start: " + start.toString());
							System.out.println("Node1: " + node1.toString());
							System.out.println("Node2: " + node2.toString());
							
							String name1 = node1.toString()+node2.toString();
							String name2 = node2.toString()+node1.toString();
							edge1 = this.result.getEdge(name1);
							edge2 = this.result.getEdge(name2);
							System.out.println(edge1);
							System.out.println(edge2);
							System.out.println((node1 == node2));
							System.out.println((edge1 != null));
							System.out.println((edge2 != null));
							System.out.println((node1 == node2) && (edge1 != null) && (edge2 != null));
							
						} while ((node1 == node2) || (edge1 != null) || (edge2 != null));
						
						this.result.addEdge(node1.toString()+node2.toString(), node1, node2);
						
						this.unConnected.remove(node1);
						this.unConnected.remove(node2);

					}

				}

			}

		} while (this.edgeSize > 0);

		return result;
	}

	private int getCircleSizeRandom(int value) {
		Random random = new Random();

		int randomValue = random.nextInt(value);

		if (randomValue < 3) {
			return 3;
		} else {
			return randomValue;
		}

	}

	private int getRandom(int value) {
		Random random = new Random();

		return random.nextInt(value);

	}

	private void addEdge(Node node1, Node node2) {
		this.result.addEdge(node1.toString() + node2.toString(), node1, node2);
		this.edgeSize--;
	}

	private void addNode(String name) {
		this.result.addNode(name);
		this.result.getNode(name).addAttribute("ui.label", name);

	}

	private Node getRandomNode() {
		Collections.shuffle(this.nodes);

		return this.nodes.get(0);
	}

	private void validArguments(int nodeSize, int edgeSize, int maxWeight) {
		/*
		 * Erst ein Graph ab Knotenanzhal == oder größer kann einen Kreis
		 * bilden.
		 */
		if (nodeSize < 3) {
			throw new IllegalArgumentException("Knotenanzahl muss >= 3 sein");

			/*
			 * Graph darf maximal vollständig sein.
			 */
		} else if (edgeSize > (((nodeSize - 1) * nodeSize) / 2)) {
			throw new IllegalArgumentException("Maximale Kantenanzahl = ((nodeSize-1)*nodeSize)/2)");

			/*
			 * Damit mindestens ein Kreis in einem zusammenhängenden Graphen
			 * enstehen kann, muss die edgSize mindestens der nodeSize sein.
			 */
		} else if (edgeSize < nodeSize) {
			throw new IllegalArgumentException("Kantenanzahl muss minimal Knotenanzahl sein, damit ein Kreis entsteht");

			// } else if (!((edgeSize%2)==0)){
			// throw new IllegalArgumentException("Kantenanzahl muss gerade
			// sein");
		}

	}

	private void initialize(int nodeSize, int edgeSize, int maxWeight) {
		this.result = new MultiGraph("RandomEuler");
		this.nodes = new LinkedList<Node>();
		this.unConnected = new LinkedList<Node>();
		this.uneven = new LinkedList<Node>();
		this.unevenB = new LinkedList<Node>();
		this.nodeSize = nodeSize;
		this.edgeSize = edgeSize;
		this.maxWeight = maxWeight;

		for (int i = 0; i < nodeSize; i++) {
			this.addNode("Node" + i);
		}

		this.unConnected.addAll(this.result.getNodeSet());

	}

}
