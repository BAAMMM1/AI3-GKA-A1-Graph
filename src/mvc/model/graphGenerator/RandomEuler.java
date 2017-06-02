package mvc.model.graphGenerator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class RandomEuler extends GraphGenerator {

	private Graph result;
	private List<Node> nodes;
	private List<Node> uneven;
	private List<Node> unevenB;
	private int nodeSize;
	private int edgeSize;
	private int maxWeight;

	public RandomEuler() {
	}

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initialize(nodeSize, edgeSize, maxWeight);

		this.createMinimalEulerCicrle();

		this.addEdges2();

		// this.seperateUneven();

		// this.addSeperated();

		// this.addUnevenEdges();

		return result;
	}

	private void addSeperated() {

		for (int i = 0; i < this.unevenB.size(); i++) {
			Node random = this.getRandomUnevenNode();
			this.result.addEdge(this.unevenB.get(i).toString() + random.toString(), this.unevenB.get(i), random);
			this.unevenB.remove(this.unevenB.get(i));
		}

	}

	private void seperateUneven() {

		System.out.println("-> " + this.uneven.toString());

		for (int i = 0; i < this.uneven.size(); i++) {
			List<Edge> edges = new LinkedList<Edge>(this.uneven.get(i).getEdgeSet());

			for (int i2 = 0; i2 < edges.size(); i2++) {
				if (this.uneven.contains(edges.get(i2).getNode0())) {

					this.unevenB.add(edges.get(i2).getNode0());
					this.uneven.remove(edges.get(i2).getNode0());

				}

				if (this.uneven.contains(edges.get(i2).getNode1())) {

					this.unevenB.add(edges.get(i2).getNode1());
					this.uneven.remove(edges.get(i2).getNode1());

				}
			}

		}

		System.out.println("-> " + this.uneven.toString());
		System.out.println("-> " + this.unevenB.toString());

	}

	private void addUnevenEdges() {

		while (this.uneven.size() != 0) {
			System.out.println("addUnevenEdges: " + this.uneven);

			for (int i = 0; i < this.uneven.size(); i++) {
				System.out.println(this.uneven.get(i).getEdgeSet().size());
			}

			Node node1 = this.getRandomUnevenNode();
			Node node2;
			do {
				node2 = this.getRandomUnevenNode();
			} while (node1 == node2);

			if ((result.getEdge(node1.toString() + node2.toString()) == null)
					&& (result.getEdge(node2.toString() + node1.toString()) == null)) {
				this.result.addEdge(node1 + node2.toString(), node1, node2);
				this.uneven.remove(node1);
				this.uneven.remove(node2);
			}

		}

	}

	private void addEdges() {
		// nodeSize 10, edgeSize 10
		System.out.println("uneven: " + this.uneven);
		System.out.println("uneven / 2: " + this.uneven.size() / 2);
		System.out.println("edgsize: " + edgeSize);
		while (this.uneven.size() / 2 < edgeSize) {

			Node node1 = this.getRandomNode();
			Node node2;
			do {
				node2 = this.getRandomNode();

			} while (node1 == node2);

			if ((this.result.getEdge(node1.toString() + node2.toString()) == null)
					&& (this.result.getEdge(node2.toString() + node1.toString()) == null)) {

				this.result.addEdge(node1.toString() + node2.toString(), node1, node2);
				this.edgeSize--;

				if ((node1.getEdgeSet().size() % 2) != 0) {
					this.uneven.add(node1);

				} else {
					if (uneven.contains(node1)) {
						this.uneven.remove(node1);
					}
				}

				if ((node2.getEdgeSet().size() % 2) != 0) {
					this.uneven.add(node2);

				} else {
					if (uneven.contains(node2)) {
						this.uneven.remove(node2);
					}
				}

			}

			System.out.println("uneven: " + this.uneven);
			System.out.println("uneven / 2: " + this.uneven.size() / 2);
			System.out.println("edgsize: " + edgeSize);

		}

	}

	private void addEdges2() {
		// nodeSize 10, edgeSize 10
		System.out.println("uneven: " + this.uneven);
		System.out.println("uneven: " + this.uneven.size());
		System.out.println("edgsize: " + edgeSize);
		while (edgeSize > 0) {

			Node node1;
			Node node2;
			do {
				if (this.uneven.size() > 0) {

					node1 = this.getRandomNode();
					node2 = this.getRandomNode();
					
					if ((this.result.getEdge(this.uneven.get(0).toString() + this.uneven.get(1).toString()) == null)
							&& (this.result
									.getEdge(this.uneven.get(1).toString() + this.uneven.get(0).toString()) == null)) {
						System.out.println("3");
						System.out.println(this.uneven.get(0).toString() + this.uneven.get(1).toString() == null);
						node1 = this.uneven.get(0);
						node2 = this.uneven.get(1);
					}

				} else {
					node1 = this.getRandomNode();
					node2 = this.getRandomNode();
				}
				
				
				
				

				System.out.println(node1);
				System.out.println(node2);
			} while ((node1 == node2) || (this.result.getEdge(node1.toString() + node2.toString()) != null)
					|| (this.result.getEdge(node2.toString() + node1.toString()) != null));



			this.result.addEdge(node1.toString() + node2.toString(), node1, node2);
			this.edgeSize--;

			if ((node1.getEdgeSet().size() % 2) != 0) {
				this.uneven.add(node1);

			} else {
				if (uneven.contains(node1)) {
					this.uneven.remove(node1);

				}
			}

			if ((node2.getEdgeSet().size() % 2) != 0) {
				this.uneven.add(node2);

			} else {
				if (uneven.contains(node2)) {
					this.uneven.remove(node2);

				}
			}

			System.out.println("uneven: " + this.uneven);
			System.out.println("uneven: " + this.uneven.size());
			System.out.println("edgsize: " + edgeSize);
			System.out.println(this.result.getEdgeSet().size());

		}

		/*
		 * 
		 * // Such eine Even Kante die Nicht zu uneven 1 und uneven 2 Verbunden
		 * ist // und verbinde sie boolean run = true; Node uneven1 =
		 * this.nodes.get(0); Node uneven2 = this.nodes.get(1); while (run) {
		 * Node even = this.getRandomEvenNode();
		 * System.out.println(even.toString());
		 * 
		 * if((this.result.getEdge(uneven1.toString() + uneven2.toString()) ==
		 * null) && (this.result.getEdge(uneven2.toString() +
		 * uneven1.toString()) == null)){ if (
		 * (this.result.getEdge(uneven1.toString() + even.toString()) == null)
		 * && (this.result.getEdge(even.toString() + uneven1.toString()) ==
		 * null) && (this.result.getEdge(uneven2.toString() + even.toString())
		 * == null) && (this.result.getEdge(even.toString() +
		 * uneven2.toString()) == null) ) {
		 * this.result.addEdge(uneven1.toString() + even.toString(), uneven1,
		 * even); this.result.addEdge(uneven2.toString() + even.toString(),
		 * uneven2, even); run = false;
		 * 
		 * } } else { run = false; }
		 * 
		 * 
		 * 
		 * 
		 * }
		 */
		System.out.println(this.result.getEdgeSet().size());

		/*
		 * if(this.result.getEdge(this.uneven.get(0).toString( ) +
		 * this.uneven.get(1).toString()) != null){
		 * this.result.removeEdge(this.uneven.get(0).toString( )+
		 * this.uneven.get(1).toString()); } else {
		 * this.result.removeEdge(this.uneven.get(1).toString( )+
		 * this.uneven.get(0).toString()); }
		 */

	}

	private void addEdges3() {
		// nodeSize 10, edgeSize 10
		System.out.println("uneven: " + this.uneven);
		System.out.println("uneven size: " + this.uneven.size());
		System.out.println("edgsize: " + edgeSize);
		while (edgeSize > 2) {

			Node node1 = this.getRandomNode();
			Node node2;
			do {
				if (this.uneven.size() > 0) {
					node1 = this.getRandomNode();
					node2 = this.uneven.get(0);
				} else {
					node2 = this.getRandomNode();
				}

			} while (node1 == node2);

			if ((this.result.getEdge(node1.toString() + node2.toString()) == null)
					&& (this.result.getEdge(node2.toString() + node1.toString()) == null)) {

				this.result.addEdge(node1.toString() + node2.toString(), node1, node2);
				this.edgeSize--;

				if ((node1.getEdgeSet().size() % 2) != 0) {
					this.uneven.add(node1);

				} else {
					if (uneven.contains(node1)) {
						this.uneven.remove(node1);
					}
				}

				if ((node2.getEdgeSet().size() % 2) != 0) {
					this.uneven.add(node2);

				} else {
					if (uneven.contains(node2)) {
						this.uneven.remove(node2);
					}
				}

			}

			System.out.println("uneven: " + this.uneven);
			System.out.println("uneven: " + this.uneven.size());
			System.out.println("edgsize: " + edgeSize);

		}

	}

	private void createMinimalEulerCicrle() {

		this.addNode("Node0");
		Node first = result.getNode("Node0");
		this.nodes.add(first);

		Node nextNode = null;
		Node prevNode = null;
		for (int i = 1; i < nodeSize; i++) {
			this.addNode("Node" + i);

			prevNode = this.result.getNode("Node" + (i - 1));
			nextNode = result.getNode("Node" + i);

			this.addEdge(prevNode, nextNode);

			this.nodes.add(nextNode);
		}

		this.addEdge(first, nextNode);

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

	private Node getRandomUnevenNode() {
		Collections.shuffle(this.uneven);

		return this.uneven.get(0);
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
		this.uneven = new LinkedList<Node>();
		this.unevenB = new LinkedList<Node>();
		this.nodeSize = nodeSize;
		this.edgeSize = edgeSize;
		this.maxWeight = maxWeight;

	}

}
