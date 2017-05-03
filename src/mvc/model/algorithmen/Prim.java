package mvc.model.algorithmen;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import utility.Printer;

public class Prim extends Algorithmus {

	private Graph graph;
	private Node source;
	private Node target;

	public Prim(Graph graph) {
		this.graph = graph;
	}

	@Override
	protected Graph procedure(Node source, Node target) {
		this.initPrim(source, target);

		while (this.graphTHasNotAllNodes()) {
			Edge e = this.getEedgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT();

			this.addEdgeAndNodeToT(e, e.getTargetNode());

		}

		return this.graph;
	}

	private void initPrim(Node source, Node target) {
		Printer.promptTestOut(this, "initialize");
		Printer.promptTestOut(this, "set source: " + source.toString());
		Printer.promptTestOut(this, "set target: " + target.toString());
		this.source = source;
		this.target = target;

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
