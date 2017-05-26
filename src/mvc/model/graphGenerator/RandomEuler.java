package mvc.model.graphGenerator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class RandomEuler extends GraphGenerator {

	private Graph result;
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

		return result;
	}

	private void createMinimalEulerCicrle() {
		
		
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

		//} else if (!((edgeSize%2)==0)){
			//throw new IllegalArgumentException("Kantenanzahl muss gerade sein");
		}

	}

	private void initialize(int nodeSize, int edgeSize, int maxWeight) {
		this.result = new MultiGraph("RandomEuler");
		this.nodeSize = nodeSize;
		this.edgeSize = edgeSize;
		this.maxWeight = maxWeight;

	}

}
