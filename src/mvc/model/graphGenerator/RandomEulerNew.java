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
public class RandomEulerNew extends GraphGenerator {

	private Graph result;

	public RandomEulerNew() {
	}

	@Override
	protected Graph procedure(int nodeSize, int edgeSize, int maxWeight) {

		this.validArguments(nodeSize, edgeSize, maxWeight);

		this.initialize(nodeSize, edgeSize, maxWeight);


		return result;
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


	}

	@Override
	public String toString() {
		return String.format("RandomEuler2");
	}

}
