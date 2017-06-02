package mvc.model.algorithmen.eulerCircle;

import java.util.LinkedList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import mvc.model.algorithmen.shortestPath.ShortestPath;
import mvc.model.algorithmen.shortestPath.ShortestPathFactory;
import scala.util.Random;
import utility.Printer;

public class Fleury extends EulerCircle {

	private LinkedList<Edge> edgePath;

	@Override
	protected List<Edge> procedure() {
		this.initialize();

		Node startAndEndNode = this.getGraph().getNode(this.getRandomStart(this.getGraph().getNodeSet().size()));
		// start = this.getGraph().getNode("s");

		Node nextNode = startAndEndNode;
		Edge nextEdge = null;
		while (!this.getGraph().getEdgeSet().isEmpty()) {

			List<Edge> edges = new LinkedList<Edge>();
			edges.addAll(nextNode.getEdgeSet());

			if (edges.size() > 1) {

				int random = this.getRandomStart(edges.size());
				Printer.promptTestOut(this, "" + random);

				/*
				 * Wähle eine Kante die keine Brücke ist
				 */
				while (this.isBridge(edges.get(random), nextNode.toString(), startAndEndNode.toString())) {
					random = this.getRandomStart(edges.size());
					Printer.promptTestOut(this, "" + random);
				}

				// nextEdge = nextNode.getEdge(random);
				// System.out.println("-->: " + edges.get(random).toString());

				/*
				 * Damit die Kante auch wieder im richten Graphen gefunden wird,
				 * da sie in isBridge einmal gelöscht und wieder hinzugefügt
				 * wird, ist die Kante nicht das selbe Objekt
				 */
				nextEdge = this.getGraph().getEdge(
						edges.get(random).getSourceNode().toString() + edges.get(random).getTargetNode().toString());
				// System.out.println("-->: " + nextEdge);

				/*
				 * System.out.println(random);
				 * System.out.println("edges.get(random) " + edges.get(random));
				 * System.out.println("nextEdge: " + nextEdge.toString());
				 */

			} else {
				/*
				 * Wenn nur noch eine Brücke vorhanden ist, gehe sie
				 */
				nextEdge = nextNode.getEdge(0);
			}

			edgePath.add(nextEdge);
			this.getGraph().removeEdge(nextEdge);

			if (nextEdge.getSourceNode() != nextNode) {
				nextNode = nextEdge.getSourceNode();
			} else {
				nextNode = nextEdge.getTargetNode();
			}

		}

		// Dem Graphen, die gelöschten Kanten wieder hinzufügen
		for (int i = 0; i < this.edgePath.size(); i++) {
			String name = edgePath.get(i).getSourceNode().toString() + edgePath.get(i).getTargetNode().toString();
			Node source = edgePath.get(i).getSourceNode();
			Node target = edgePath.get(i).getTargetNode();
			this.getGraph().addEdge(name, source, target);

			if (edgePath.get(i).getAttribute("ui.label") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label",
						edgePath.get(i).getAttribute("ui.label").toString());
			}
			if (edgePath.get(i).getAttribute("weight") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", (int)edgePath.get(i).getAttribute("weight"));
			}

		}

		Printer.prompt(this, this.edgePath.toString());

		return this.edgePath;
	}

	private void initialize() {
		this.edgePath = new LinkedList<Edge>();

	}

	/*
	 * Gibt es auch eine effizientere Implementierung?
	 */
	private boolean isBridge(Edge edge, String position, String startNode) {
		ShortestPath bfs = ShortestPathFactory.getInstance("BreadthFirstSearch");
		Node edgeSource = edge.getSourceNode();
		Node edgeTarget = edge.getTargetNode();
		String name = edgeSource.toString() + edgeTarget.toString();

		// Sicherstellen, dass die Kante des Graphen verwendet wird
		edge = this.getGraph().getEdge(name);

		this.getGraph().removeEdge(edge);

		if (bfs.calculate(this.getGraph(), position, startNode).isEmpty()) {
			this.getGraph().addEdge(name, edgeSource, edgeTarget);
			
			if (edge.getAttribute("ui.label") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
			}
			if (edge.getAttribute("weight") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", (int)edge.getAttribute("weight"));
			}
			

			return true;
		} else {

			this.getGraph().addEdge(name, edgeSource, edgeTarget);
			if (edge.getAttribute("ui.label") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
			}
			if (edge.getAttribute("weight") != null) {
				this.getGraph().getEdge(name).addAttribute("ui.label", (int)edge.getAttribute("weight"));
			}

			return false;
		}

	}

	private int getRandomStart(int value) {
		Random random = new Random();
		return random.nextInt(value);
	}

	@Override
	public String toString() {
		return String.format("Fleury");
	}

}
