package mvc.model.algorithmen.minimalSpanningTree;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

public class Prim extends MinimalSpanningTree {

	private Graph tree;
	private List<Node> nodesNotInGraphT;
	private List<Node> nodesInGraphT;
	private List<Edge> result;
	private PriorityQueue<Node> queue;

	private static final Integer OMEGA = Integer.MAX_VALUE;

	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();
		this.initPrim();

		Node start = this.getRandomStartNode();
		System.out.println("Startknoten: " + start.toString());
		this.addNodeToTree(start);
		this.setNodeWeight(start);

		while (this.graphTHasNotAllNodes()) {
			Node nextNode = this.getNextNodeFromQueue();
			System.out.println("nextNode: " + nextNode.toString());

			this.addNodeAndEdgeToTree(nextNode);
			this.setNodeWeight(nextNode);

		}

		long timeEnd = System.currentTimeMillis();
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));
		System.out.println(this.getEdgeWeightes());
		return this.tree;
	}

	private Node getNextNodeFromQueue() {

		// Wird die abfrage hier noch gebraucht?
		// Es sind ja Keine doppelten mehr in der Queue, da sie ja removed
		// werden

		Node nextNode;
		do {
			nextNode = this.queue.poll();
		} while (this.nodesInGraphT.contains(nextNode));

		return nextNode;
	}

	private void addNodeAndEdgeToTree(Node nextNode) {
		this.addNodeToTree(nextNode);
		Edge edge = nextNode.getAttribute("primEdge");
		this.result.add(edge);

		Node source = this.tree.getNode(edge.getSourceNode().getId());
		Node target = this.tree.getNode(edge.getTargetNode().getId());

		this.tree.addEdge(edge.getId(), source, target);
		this.tree.getEdge(edge.getId()).addAttribute("weight", (Integer) edge.getAttribute("weight"));
		this.tree.getEdge(edge.getId()).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
		System.out.println("Add Edge: " + edge.toString());

	}

	private void setNodeWeight(Node node) {
		System.out.println("Sehe Kanten von Knoten: " + node.toString() + " an");

		for (Edge edge : node.getEachEdge()) {

			if (!result.contains(edge)) {
				System.out.println("Sehe Kanten " + edge.toString() + " an");
				Node nodeForQueue;
				Node source = edge.getSourceNode();
				Node target = edge.getTargetNode();

				if (this.nodesNotInGraphT.contains(source)) {
					nodeForQueue = source;
				} else {
					nodeForQueue = target;
				}

				if (!this.nodesInGraphT.contains(nodeForQueue)) {
					// Da jede kannte neu angeschaut wird von den Nodes, kann es
					// sein das eine Node neue
					// gesetzt werden könnte, da muss geprüft werden, das dieser
					// primWeight nicht über
					// dem eigentlichen Wert liegt
					// Es kamm vor, das der primWeight, und damit auch die
					// primEdge überschrieben wurde
					if ((Integer) nodeForQueue.getAttribute("primWeight") > (Integer) edge.getAttribute("weight")) {
						nodeForQueue.setAttribute("primWeight", (Integer) edge.getAttribute("weight"));
						nodeForQueue.setAttribute("primEdge", edge);
						System.out.println("Add to Queue Node: " + nodeForQueue.toString() + " primWeight: "
								+ nodeForQueue.getAttribute("primWeight"));

						// Remove, falls der Node bereits in der Queue war, weil
						// er schon einmal einen Wert hatte, muss
						// diese Nod in der Queue neu angeordnet werden
						this.queue.remove(nodeForQueue);
						this.queue.add(nodeForQueue);
						/*
						 * Wenn ein Element in die Queue gelegt wird, wird sie anhand des Comperator einsortiert,
						 * ändert sich der Wert im Element durch den es einsortiert wrude, muss es entfernt werde
						 * und wieder neue hinzugefügt werden.
						 * 
						 * Element können in der Queuer doppelt mit unterschiedlichen Prioritäten sein
						 */

					}
				}

			}

		}

		System.out.println(this.queue.toString());

	}

	@Override
	public String toString() {
		return "Prima";
	}

	private void initPrim() {
		this.tree = new MultiGraph("Prim");
		this.nodesInGraphT = new LinkedList<Node>();
		this.result = new LinkedList<Edge>();
		this.nodesNotInGraphT = new LinkedList<Node>();
		this.nodesNotInGraphT.addAll(this.getGraph().getNodeSet());

		this.queue = new PriorityQueue<Node>(nodesNotInGraphT.size(), (e1,
				e2) -> ((Integer) e1.getAttribute("primWeight")).compareTo((Integer) e2.getAttribute("primWeight")));

		for (Node node : nodesNotInGraphT) {
			node.addAttribute("primWeight", OMEGA);
		}
	}

	private void addNodeToTree(Node node) {
		System.out.println("Add Node to tree: " + node.toString());
		this.nodesInGraphT.add(node);
		this.nodesNotInGraphT.remove(node);

		this.tree.addNode(node.getId());
		this.tree.getNode(node.getId()).setAttribute("ui.label", node.getAttribute("ui.label").toString());

	}

	private Node getRandomStartNode() {
		Random random = new Random();
		Node randomStart = this.nodesNotInGraphT.get(random.nextInt(this.nodesNotInGraphT.size()));
		return randomStart;
	}

	private boolean graphTHasNotAllNodes() {
		return !(this.tree.getNodeSet().size() == this.getGraph().getNodeSet().size());
	}

	public int getEdgeWeightes() {
		return this.result.stream().map(e1 -> (Integer) e1.getAttribute("weight")).reduce(0,
				(e1, e2) -> e1.intValue() + e2.intValue());

	}

}
