package mvc.model.graphGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

/*
 * Wenn Knoten = edge dann mach daraus einen großen Kreis
 * Ansonsten bilde Kreise, erst mit anconnected, dann mit Lnoten die unvollständig sind
 */
public class RandomEulerNewFeuery extends GraphGenerator {

	private Graph result;
	private List<Node> unConnected;
	private int maxKanten;
	private Node startEndNode;

	public RandomEulerNewFeuery() {
	}

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initialize(nodeSize, edgeSize, maxWeight);

		startEndNode = this.unConnected.get(0);
		Node current = startEndNode;
		Node next = null;
		boolean bool = true;
		for (int i = 1; i <= edgeSize; i++) {
			
			System.out.println(i);
			boolean alleNachbarnKeinKanteNachEnde = true;
			if (i < nodeSize) {
				next = this.unConnected.get(i);

				/*
				 * Endknoten muss der Startknoten sein
				 */
			} else if (i == edgeSize) {
				next = startEndNode;

				/*
				 * Vorvorlezter darf nicht der Endknoten sein
				 */
			} else if (i == edgeSize - 2) {

				do {
					bool = true;
					next = this.getRandomNode();

					/*
					 * Von der Menge an Knoten, die noch nicht mit dem Ende
					 * verbunden sind, darf er mindesten einen nicht als
					 * Nachbarn haben
					 */
					Set<Node> set = new LinkedHashSet<Node>();
					set.addAll(this.result.getNodeSet());
					Iterator<Node> it = this.startEndNode.getNeighborNodeIterator();
					while(it.hasNext()){
						set.remove(it.next());
					}
					
					Set<Node> set2 = new LinkedHashSet<Node>();
					Iterator<Node> it2 = next.getNeighborNodeIterator();
					while(it2.hasNext()){
						set2.add(it2.next());
					}
					
					List<Node> list = new LinkedList<Node>();
					list.addAll(set);
					
					for(int i2 = 0; i2 < set.size(); i2++){
						if(!(set2.contains(list.get(i2)))){
							bool = false;
						}
						
					}
					
					
					System.out.println(bool);
					System.out.println(next.toString());

				} while (current == next || current.hasEdgeBetween(next) || next == this.startEndNode
						|| alleNachbarnKeinKanteNachEnde
						|| bool);
				/*
				 * Vorletzter Knoten darf nicht Endknoten sein und darf kein
				 * Nachbar vom Endknoten sein
				 */
			} else if (i == edgeSize - 1) {
				System.out.println("----> Hier");

				do {

					next = this.getRandomNode();

					System.out.println("----> do" + next);

				} while (next == startEndNode || current == next || startEndNode.hasEdgeBetween(next)
						|| next.hasEdgeBetween(current));
			} else {

				do {
					next = this.getRandomNode();

				} while (current == next || current.hasEdgeBetween(next));

			}

			System.out.println(current.toString() + next.toString());
			this.result.addEdge(current.toString() + next.toString(), current, next);
			current = next;

		}

		return result;
	}

	private Node getRandomNode() {
		Node node = result.getNode("Node" + this.getRandom(result.getNodeSet().size()));

		if (this.startEndNode.getEdgeSet().size() == (this.maxKanten - 1)) {
			do {
				System.out.println("random do");
				node = result.getNode("Node" + this.getRandom(result.getNodeSet().size()));
				;
			} while (this.startEndNode == node);
		}

		return node;
	}

	private int getRandom(int value) {
		Random random = new Random();
		return random.nextInt(value);
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

			/*
			 * Wenn ein Grad eine gerade Anzahl Knoten hat, dürfen die Edges
			 * Nicht vollständig werden, da die Knoten ansonsten einen ungeraden
			 * Grad besitzen
			 */
		} else if ((nodeSize % 2 == 0) && edgeSize == (((nodeSize - 1) * nodeSize) / 2)) {
			throw new IllegalArgumentException("Knotenanzahl gerade, darf nicht vollständig sein");

		}

	}

	private void initialize(int nodeSize, int edgeSize, int maxWeight) {
		this.result = new MultiGraph("RandomEuler");
		this.unConnected = new LinkedList<Node>();

		for (int i = 0; i < nodeSize; i++) {
			this.result.addNode("Node" + i);
			this.unConnected.add(this.result.getNode("Node" + i));
		}

		/*
		 * unconnected shuffeln, damit der Start immer zufällig ist
		 */

		if (nodeSize % 2 != 0) {
			this.maxKanten = nodeSize - 1;
		} else {
			this.maxKanten = nodeSize - 2;
		}
		System.out.println("maxKanten: " + this.maxKanten);

	}

	@Override
	public String toString() {
		return String.format("RandomEuler2");
	}

}
