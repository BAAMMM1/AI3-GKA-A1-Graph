package mvc.model.algorithmen.minimalSpanningTree;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Prim extends MinimalSpanningTree {


	protected Graph procedure() {
		this.initPrim();

		while (this.graphTHasNotAllNodes()) {
			Edge e = this.getEedgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT();

			this.addEdgeAndNodeToT(e, e.getTargetNode());

		}
		
		return null;

	}

	private void initPrim() {		

	}

	private boolean graphTHasNotAllNodes() {
		return true;
	}

	private Edge getEedgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT() {
		
		return null;
	}

	private void addEdgeAndNodeToT(Edge edge, Node node) {

	}

	@Override
	public String toString() {
		return "Prima";
	}

}
