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

		while (this.treeHasNotAllNodes()) {
			Node nextNode;

			if (nodesInGraphT.isEmpty()) {
				nextNode = this.getRandomStartNode();
				this.addNodeToTree(nextNode);
			} else {
				nextNode = this.queue.poll();
				this.addNodeAndEdgeToTree(nextNode);
			}

			this.updateNodePriority(nextNode);
		}

		long timeEnd = System.currentTimeMillis();

		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));
		Printer.prompt(this, "edge-weight sum: " + this.getEdgeWeightes());

		return this.tree;
	}

	/**
	 * Diese Mehtode initialisiert den Prim-Algorithmus. Zunächst befindet sich
	 * in dem minimalen Spannbaum keine Knoten. Diese müssen nach einander aus
	 * der PriorityQueue hinzugefügt werden. Die PriorityQueue enthält zum Start
	 * alle Knoten mit der Priorität OMEGA.
	 */
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

		this.queue.addAll(nodesNotInGraphT);
	}

	/**
	 * Diese Methode ermittelt ob sich alle Knoten im minimalen Spannbaum
	 * befinden.
	 * 
	 * @return true falls der minimale Spannbaum komplett ist
	 */
	private boolean treeHasNotAllNodes() {
		return !(this.tree.getNodeSet().size() == this.getGraph().getNodeSet().size());
	}

	/**
	 * Gibt einen zufällig ausgewählten Knoten aus der Menge an Knoten, welche
	 * sich nicht in dem minimalen Spannbaum befinden.
	 * 
	 * @return Zufalls Knoten
	 */
	private Node getRandomStartNode() {
		Random random = new Random();
		Node randomStart = this.nodesNotInGraphT.get(random.nextInt(this.nodesNotInGraphT.size()));
		return randomStart;
	}

	/**
	 * Fügt einen übergebenen Knoten dem minimalen Spannbaum hinzu.
	 * @param node
	 */
	private void addNodeToTree(Node node) {
		Printer.promptTestOut(this, "add node to tree: " + node.toString());
		this.nodesInGraphT.add(node);
		this.nodesNotInGraphT.remove(node);

		this.tree.addNode(node.getId());
		this.tree.getNode(node.getId()).setAttribute("ui.label", node.getAttribute("ui.label").toString());
	}

	/**
	 * 
	 * @param nextNode
	 */
	private void addNodeAndEdgeToTree(Node nextNode) {
		this.addNodeToTree(nextNode);
		Edge edge = nextNode.getAttribute("primEdge");
		this.result.add(edge);

		Node source = this.tree.getNode(edge.getSourceNode().getId());
		Node target = this.tree.getNode(edge.getTargetNode().getId());

		this.tree.addEdge(edge.getId(), source, target);
		this.tree.getEdge(edge.getId()).addAttribute("weight", (Integer) edge.getAttribute("weight"));
		this.tree.getEdge(edge.getId()).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
		Printer.promptTestOut(this, "Add Edge: " + edge.toString());

	}

	private void updateNodePriority(Node node) {
		Printer.promptTestOut(this, "update priorityQueue from node: " + node.toString());

		for (Edge edge : node.getEachEdge()) {

			if (!result.contains(edge)) {
				Printer.promptTestOut(this, "look at edge: " + edge.toString());

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
						Printer.promptTestOut(this, "update node: " + nodeForQueue.toString() + " primWeight: "
								+ nodeForQueue.getAttribute("primWeight"));

						// Remove, falls der Node bereits in der Queue war, weil
						// er schon einmal einen Wert hatte, muss
						// diese Nod in der Queue neu angeordnet werden
						this.queue.remove(nodeForQueue);
						this.queue.add(nodeForQueue);
						/*
						 * Wenn ein Element in die Queue gelegt wird, wird sie
						 * anhand des Comperator einsortiert, ändert sich der
						 * Wert im Element durch den es einsortiert wrude, muss
						 * es entfernt werde und wieder neue hinzugefügt werden.
						 * 
						 * Element können in der Queuer doppelt mit
						 * unterschiedlichen Prioritäten sein
						 */

					}
				}

			}

		}
		Printer.promptTestOut(this, "PriorityQueue: " + this.queue.toString());
	}

	public int getEdgeWeightes() {
		return this.result.stream().map(e1 -> (Integer) e1.getAttribute("weight")).reduce(0,
				(e1, e2) -> e1.intValue() + e2.intValue());

	}

	@Override
	public String toString() {
		return "Prim";
	}

}
