package mvc.model.algorithmen;

import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

public class Prim extends Algorithmus<Edge> {


	@Override
	protected void procedure() {
		this.initPrim();

		while (this.graphTHasNotAllNodes()) {
			Edge e = this.getEedgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT();

			this.addEdgeAndNodeToT(e, e.getTargetNode());

		}

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
