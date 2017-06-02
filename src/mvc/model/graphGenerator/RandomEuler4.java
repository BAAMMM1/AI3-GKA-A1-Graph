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
public class RandomEuler4 extends GraphGenerator {

	private Graph result;
	private List<Node> nodes;
	private List<Node> uneven;
	private List<Node> unevenB;
	private int nodeSize;
	private int edgeSize;
	private int maxWeight;
	private int maxGrad;
	private int grades;
	private int maxGradeCounter = 0;
	private int value;
	private int addEdgeCounter;

	public RandomEuler4() {
	}

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initialize(nodeSize, edgeSize, maxWeight);

		this.addNodesToResult();

		this.calculateGrade();

		this.addEdgesToResult();

		// this.createMinimalEulerCicrle();

		// this.addEdges2();

		// this.seperateUneven();

		// this.addSeperated();

		// this.addUnevenEdges();

		return result;
	}

	private void addNodesToResult() {

		for (int i = 0; i < nodeSize; i++) {

		}

	}

	private void addEdgesToResult() {

	}

	private void calculateGrade() {
		System.out.println("calculate start");
		int[] grades = new int[this.nodeSize];

		// Falls edgeSize == nodeSize, dann besonderer Circle -> Grpßer Kreis
		if (this.edgeSize == nodeSize) {
			for (int i = 0; i < grades.length; i++) {
				grades[i] = 2;
				this.grades = this.grades - 2;
			}
		}

		int unConnectedCounter = 0;
		// Ansonten verpacke Kreise
		while (this.grades > 0) {

			// Kreisgröße erstellen und Knoten den Kreis zuordnen
			
			// Knoten positionen dürfen doppelt dran kommen!
			// Aber position vor und nach darf nicht gleich sein
			// und
			
			int circleSize = this.getCircleSize();
							
			List<Integer> postionList = new LinkedList<Integer>();
			Random rng = new Random();
			
			
			List<Integer> delta = new LinkedList<Integer>();
			for(int i = 0 ; i < nodeSize; i++){
				delta.add(0);
			}
			
			System.out.println("ermittelte circleSize --->: " + circleSize);
			int counter = 0;
			while (postionList.size() < circleSize)	{ 
				System.out.println("Hier --->");
				
				Integer next;
				if(unConnectedCounter < this.nodeSize){
					next = unConnectedCounter;
					unConnectedCounter = unConnectedCounter + 1;
				} else {
					next = rng.nextInt(this.nodeSize);
				}
			    
				System.out.println("next --->: " + next);
				System.out.println("grades[next] != this.maxGrad --->: " + (grades[next] != this.maxGrad));
			   
				
				/*
				 * !!!!!!!!!!!!!!!!!!! Wenn er in der Liste vorkommt, dann hat grades + 2*häufigkeit vorkommen
				 * Bias/ Delta hinzufügen
				 */				
				
				if((grades[next] + delta.get(next)) != this.maxGrad){
			    	
			    	if(counter > 0){			    		
			    		
			    		if((postionList.get(counter-1) != next)){

			    			postionList.add(next);
			    			int newDelta = delta.get(next) + 2;
			    			delta.add(next+1, newDelta);
			    			delta.remove(next);			    			
			    			
			    			
			    			counter++;
			    			
			    		}
			    		
			    	} else {
			    		postionList.add(next);
			    		int newDelta = delta.get(next) + 2;
		    			delta.add(next+1, newDelta);
		    			delta.remove(next);	
			    		counter++;
			    	}
			    		
			    	
			    	
			    }
			    System.out.println("postionList --->: " + postionList.toString());
			}
			
			
			
			List<Integer> postionListList = new LinkedList<Integer>();
			postionListList.addAll(postionList);
			
			System.out.println("--->" + postionListList.toString());
		
			
			// Grade verteilen auf die gesetzten Knoten
			for (int i = 0; i < circleSize; i++) {

				//Zuerst die unconnected
				int position = postionListList.get(i);
				

				if (grades[position] < this.maxGrad) {

					grades[position] = grades[position] + 2;
					this.grades = this.grades - 2;
					System.out.println(this.grades);
					
					if(grades[position] == this.maxGrad){
						this.maxGradeCounter++;
						System.out.println("maxGradCounter: " + maxGradeCounter);
					}

				}

			}
			System.out.println("Grades over: " + this.grades);
		}

		System.out.println("grades:");
		Arrays.sort(grades);
		for (int i = 0; i < grades.length; i++) {
			System.out.print(grades[i] + " ");
		}

		System.out.println("");

		for (int i = 0; i < grades.length; i++) {
			this.result.addNode("Node" + i);
			this.result.getNode("Node" + i).setAttribute("grad", grades[i]);
		}

		LinkedList<Node> list = new LinkedList<Node>();
		list.addAll(result.getNodeSet());

		Collections.sort(list, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if ((int) o1.getAttribute("grad") < (int) o2.getAttribute("grad")) {
					return -1;
				}
				if ((int) o1.getAttribute("grad") > (int) o2.getAttribute("grad")) {
					return 1;
				}
				return 0;
			}
		});

		int counter = 0;
		while (this.nodeSize - 1 > 0) {

			Node nextNode = list.get(counter);
			int gradOfNextNode = (int) nextNode.getAttribute("grad");

			if (gradOfNextNode == 0) {
				continue;
			}

			int index = list.size() - 1;
			while (gradOfNextNode > 0) {

				Node endNode = list.get(index);
				this.result.addEdge(nextNode.toString() + endNode.toString(), nextNode, endNode);
				this.addEdgeCounter++;

				endNode.setAttribute("grad", (int) endNode.getAttribute("grad") - 1);
				index--;

				gradOfNextNode--;
				nextNode.setAttribute("grad", (int) nextNode.getAttribute("grad") - 1);

			}
			

			counter++;
			nodeSize--;

			Collections.sort(list, new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					if ((int) o1.getAttribute("grad") < (int) o2.getAttribute("grad")) {
						return -1;
					}
					if ((int) o1.getAttribute("grad") > (int) o2.getAttribute("grad")) {
						return 1;
					}
					return 0;
				}
			});

		}
		System.out.println("edges added: " + this.addEdgeCounter);

	}

	private int getCircleSize() {
		if (this.grades == 6) {
			return 3;
		}
		Random random = new Random();
		int circleSize = random.nextInt(this.nodeSize);
		System.out.println("-------->: " + this.grades);

		if (circleSize < 3) {
			circleSize = 3;
		}

		System.out.println("0: ---------> circleSize: " + circleSize);

		if ((this.grades - (circleSize * 2)) < 6) {
			System.out.println("1: ---------> Grades " + (this.grades-(circleSize*2)) + " können keinen weiteren Kreis bilden");
			circleSize = circleSize + ((this.grades - (circleSize * 2)) / 2);
			//this.grades = grades;
			System.out.println("1: ---------> circleSize: " + circleSize);
		}

		if (circleSize > this.nodeSize) {
			System.out.println("2: ---------> circleSize: " + circleSize + " Kreis größer als gesamt Knotenanzahl");
			//this.grades = 6;
			circleSize = circleSize - 3;
			System.out.println("2: ---------> circleSize: " + circleSize);
		}
		
		if(circleSize > (this.nodeSize-this.maxGradeCounter)){
			System.out.println("3: ---------> circleSize: " + circleSize + " Kreisg größer als nicht vollständige Knotenanzahl");
			System.out.println("3: ---------> " + (this.nodeSize-this.maxGradeCounter));
			circleSize = (this.nodeSize-this.maxGradeCounter);
			
		}
		
		if (circleSize < 3) {
			throw new IllegalArgumentException("Kantenanzahl: Kein Euler kreis mögliche");
		}
		
		
		// TODO Kreise werden nicht korrekt vertielt

		System.out.println("circleSize: ---------> " + circleSize);
		return circleSize;
	}

	private int getRandom() {
		Random random = new Random();
		return random.nextInt(nodeSize);

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

			// wird auch beu vollständigen graden geworfen bsp. k5, müsste aber
			// gehen, k6 jedoch nicht
			// } else if((edgeSize-nodeSize)%2 != 0){
			// throw new IllegalArgumentException("Kantenanzahl muss nodeSize +
			// vielfaches von 2 sein");
			
			/*
			 * Wenn ein Grad eine gerade Anzahl Knoten hat, dürfen die Edges Nicht vollständig werden, da
			 * die Knoten ansonsten einen ungeraden Grad besitzen
			 */
		} else if ((nodeSize%2 == 0) && edgeSize == (((nodeSize - 1) * nodeSize) / 2)) {
			throw new IllegalArgumentException("Knotenanzahl gerade, darf nicht vollständig sein");

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
		this.maxGradeCounter = 0;
		this.addEdgeCounter = 0;

		if (nodeSize % 2 != 0) {
			this.maxGrad = nodeSize - 1;
		} else {
			this.maxGrad = nodeSize - 2;
		}

		this.value = (this.nodeSize / 2) + 1;

		this.grades = edgeSize * 2;

	}

	@Override
	public String toString() {
		return String.format("RandomEuler2");
	}

}
