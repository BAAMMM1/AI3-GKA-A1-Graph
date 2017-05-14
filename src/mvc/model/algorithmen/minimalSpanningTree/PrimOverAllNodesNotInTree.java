package mvc.model.algorithmen.minimalSpanningTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Predicate;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

public class PrimOverAllNodesNotInTree extends MinimalSpanningTree {

	private Graph tree;
	private List<Node> nodesNotInGraphT;
	private List<Node> nodesInGraphT;
	private List<Edge> result;
	private PriorityQueue<Node> queue;

	private static final Integer OMEGA = Integer.MAX_VALUE;

	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();
		this.initPrim();

		this.addNodeToResult(this.getRandomStartNode());
		
		while (this.graphTHasNotAllNodes()) {
			this.setNodeFlags();

			Node nextNode = this.queue.poll();
			Edge e = nextNode.getAttribute("primEdge");

			this.addEdgeAndNodeToT(nextNode, e);

		}

		long timeEnd = System.currentTimeMillis();
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));
		System.out.println(this.getEdgeWeightes());

		return tree;

	}

	private void initPrim() {
		this.tree = new MultiGraph("Prim");
		this.nodesInGraphT = new LinkedList<Node>();
		this.result = new LinkedList<Edge>();
		this.nodesNotInGraphT = new LinkedList<Node>();
		this.nodesNotInGraphT.addAll(this.getGraph().getNodeSet());

		this.queue = new PriorityQueue<Node>(nodesNotInGraphT.size(), (e1,
				e2) -> ((Integer) e1.getAttribute("primWeight")).compareTo((Integer) e2.getAttribute("primWeight")));

	}

	private void setNodeFlags() {

		for (Node node : nodesNotInGraphT) {
			System.out.println("Ermittel Edges for: " + node.toString());

			Edge lowestEdge;
			Iterator<Edge> it = node.getEdgeIterator();
			lowestEdge = null;

			while (it.hasNext()) {
				Edge nextEdge = it.next();

				if ((nodesInGraphT.contains(nextEdge.getSourceNode()))
						|| (nodesInGraphT.contains(nextEdge.getTargetNode()))) {
					if (lowestEdge == null) {
						lowestEdge = nextEdge;
					} else if ((Integer) nextEdge.getAttribute("weight") < (Integer) lowestEdge
							.getAttribute("weight")) {
						lowestEdge = nextEdge;
					}
				}
			}

			if (lowestEdge != null) {
				System.out.println("LowestEdge for: " + node.toString() + " = " + lowestEdge.toString());
				node.addAttribute("primWeight", (Integer) lowestEdge.getAttribute("weight"));
				node.addAttribute("primEdge", lowestEdge);
				if (!queue.contains(node)) {
					this.queue.add(node);
				}

			} else {
				System.out.println("No lowestEdge for: " + node.toString());
				node.addAttribute("primWeight", OMEGA);
			}
			System.out.println("Node primWeight: " + node.getAttribute("primWeight"));
		}

		for (int i = 0; i < this.nodesNotInGraphT.size(); i++) {
			System.out.println(nodesNotInGraphT.get(i).toString() + " primWeight: "
					+ nodesNotInGraphT.get(i).getAttribute("primWeight"));
		}

	}

	private void addNodeToResult(Node node) {
		Printer.promptTestOut(this, "Füge: " + node.toString() + " zum Result hinzu");
		this.nodesInGraphT.add(node);
		this.nodesNotInGraphT.remove(node);

		this.tree.addNode(node.getId());
		this.tree.getNode(node.getId()).setAttribute("ui.label",
				node.getAttribute("ui.label").toString());

	}

	private boolean graphTHasNotAllNodes() {
		return !(this.tree.getNodeSet().size() == this.getGraph().getNodeSet().size());
	}

	private void addEdgeAndNodeToT(Node node, Edge edge) {

		Node source;
		Node target;

		if (edge.getSourceNode() == node) {
			source = node;
			target = edge.getTargetNode();
		} else {
			source = edge.getSourceNode();
			target = node;
		}

		if (!this.nodesInGraphT.contains(source)) {
			this.addNodeToResult(source);
		} else {
			this.addNodeToResult(target);
		}
		source = this.tree.getNode(source.getId());
		target = this.tree.getNode(target.getId());
		
		this.result.add(edge);

		this.tree.addEdge(edge.getId().toString(), source, target);
		this.tree.getEdge(edge.getId().toString()).addAttribute("weight",
				(Integer) edge.getAttribute("weight"));
		this.tree.getEdge(edge.getId().toString()).addAttribute("ui.label",
				edge.getAttribute("ui.label").toString());

	}

	private Node getRandomStartNode() {
		Random random = new Random();
		Node randomStart = this.nodesNotInGraphT.get(random.nextInt(this.nodesNotInGraphT.size()));
		return randomStart;
	}

	@Override
	public String toString() {
		return "Prima";
	}
	
	public int getEdgeWeightes(){
		return this.result.stream().map(e1 -> (Integer)e1.getAttribute("weight")).reduce(0, (e1,e2) -> e1.intValue()+e2.intValue());
		
	}

}

