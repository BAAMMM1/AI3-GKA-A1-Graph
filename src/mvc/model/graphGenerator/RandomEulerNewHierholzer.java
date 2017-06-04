package mvc.model.graphGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
public class RandomEulerNewHierholzer extends GraphGenerator {

	private Graph result;
	private int nodeSize;
	private int edgeSize;
	private int maxGradeCounter;
	private int maxGrad;

	public RandomEulerNewHierholzer() {
	}

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initialize(nodeSize, edgeSize, maxWeight);

		if (nodeSize == this.edgeSize) {
			/*
			 * Spezialfall
			 */
		} else {

			while (this.edgeSize > 0) {
				System.out.println("edgeSize: " + this.edgeSize);
				int circle = this.calcCircleSize();

				System.out.println("circle: " + circle);

				List<Node> edges = this.calcEdges(circle);
				
				System.out.println(edges);
				
				this.addEdges(edges);
				
				this.edgeSize = this.edgeSize - circle;

				/*
				 * int counter = 0; while(counter < circle){
				 * 
				 * 
				 * 
				 * this.edgeSize--; counter += 1; }
				 */
			}

		}

		return result;
	}

	private void addEdges(List<Node> edges) {

		for(int i = 0; i < edges.size()-1; i++){
			this.result.addEdge(edges.get(i).toString()+edges.get(i+1).toString(), edges.get(i), edges.get(i+1));
		}
		
		
	}

	private List<Node> calcEdges(int circle) {
		List<Node> list = new LinkedList<Node>();

		Node start;
		Node end;

		do {
			start = this.getRandomNode();
		} while (start.getEdgeSet().size() == this.maxGrad);

		end = start;
		
		Node vorletzter;
		
		do {
			vorletzter = this.getRandomNode();
		}while(end.hasEdgeBetween(vorletzter)
				|| vorletzter.getEdgeSet().size() == this.maxGrad
				|| vorletzter == end
				);
		
		
		
		list.add(start);
		
		Node next;
		circle -= 1;
		int counter = 1;
		while(circle > 2){
			//Try verteileung zwischen Start <-> Vorletzter -> End
			do{
				next = this.getRandomNode();
			}while(next == list.get(counter-1)
					|| list.get(counter-1).hasEdgeBetween(next)
					|| next.getEdgeSet().size() == this.maxGrad
					);
			
			list.add(next);
			
			counter += 1;			
			circle--;
		}
		

			do{
				next = this.getRandomNode();
			}while(next == list.get(counter-1)
					|| next == vorletzter
					|| list.get(list.size()-1).hasEdgeBetween(next)
					|| next.hasEdgeBetween(vorletzter)
					|| next.getEdgeSet().size() == this.maxGrad
					);
			
			list.add(next);

		
		
		list.add(vorletzter);
		list.add(end);
		
		
		Node vorvorletzter;
		do{
			vorvorletzter = this.getRandomNode();
		}while(vorvorletzter == end
				|| vorvorletzter.hasEdgeBetween(vorletzter)
				|| vorvorletzter.hasEdgeBetween(list.get(list.size()-4))	
				|| vorvorletzter.getEdgeSet().size() == this.maxGrad
				);
		
		
	
		
		return list;

	}

	private int calcCircleSize() {

		if (this.edgeSize < 3) {
			throw new IllegalArgumentException("Kein Kreis mehr möglich");
		}

		if (this.edgeSize == 3) {
			return 3;
		}

		Random random = new Random();
		int circleSize = random.nextInt(this.nodeSize);

		if (circleSize < 3) {
			circleSize = 3;
		}

		if ((edgeSize - (circleSize)) < 3) {
			circleSize = circleSize + ((edgeSize - (circleSize)));
		}

		if (circleSize > this.nodeSize) {
			circleSize = circleSize - 3;
		}

		if (circleSize > (this.nodeSize - this.maxGradeCounter)) {
			circleSize = (this.nodeSize - this.maxGradeCounter);

		}

		if (circleSize < 3) {
			throw new IllegalArgumentException("Kantenanzahl: Kein Euler kreis mögliche");
		}

		return circleSize;

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
		this.nodeSize = nodeSize;
		this.edgeSize = edgeSize;
		this.maxGradeCounter = 0;

		for (int i = 0; i < nodeSize; i++) {
			this.result.addNode("Node" + i);
		}

		if (nodeSize % 2 != 0) {
			this.maxGrad = nodeSize - 1;
		} else {
			this.maxGrad = nodeSize - 2;
		}

	}

	@Override
	public String toString() {
		return String.format("RandomEulerNewHierholzer");
	}

}
