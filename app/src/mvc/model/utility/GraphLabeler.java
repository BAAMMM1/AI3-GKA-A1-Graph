package mvc.model.utility;

import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class GraphLabeler {

	private static final int DEFAULT_R = 0;
	private static final int DEFAULT_G = 0;
	private static final int DEFAULT_B = 0;

	public GraphLabeler() {

	}

	/**
	 * Färbt auf einem Weg (z.B. vom BFS oder Dijkstra) die Knoten und Kanten
	 * ein
	 */
	public void colorPath(Graph graph, List<Node> path, int r, int g, int b) {
		/*
		 * Codeduplikate auslagern
		 */
		for (int i = 0; i < path.size(); i++) {
			path.get(i).addAttribute("ui.style", "fill-color: rgb(" + r + "," + g + "," + b + ");");
			System.out.println(path.get(i).getId().toString());
			if (i < path.size() - 1) {
				
				// Die Edges werden vom BSF benötigt, diese hier findet er nicht = path.get(i).getId().toString() node
				// Die die Edges hier berechnen
				if (graph.getEdge(path.get(i).getId().toString()) != null) {
					graph.getEdge(path.get(i).getId().toString()).addAttribute("ui.style",
							"fill-color: rgb(" + r + "," + g + "," + b + ");");

				}
			}

		}
	}

	/**
	 * Färbt alle übergebene Kanten ein
	 */
	public void colorEdges(Graph graph, List<Edge> path, int r, int g, int b) {

		for (int i = 0; i < path.size(); i++) {

			//String name = path.get(i).getSourceNode().toString() + path.get(i).getTargetNode().toString();
			
			String name = path.get(i).getId();
			
			graph.getEdge(name).addAttribute("ui.style", "fill-color: rgb(" + r + "," + g + "," + b + ");");

		}
	}

	public void colorListEdges(Graph graph, List<List<Edge>> listList, int startR, int startG, int startB) {

		for (int i = 0; i < listList.size(); i++) {

			int nextColorR = this.getRandom(256);
			int nextColorG = this.getRandom(256);
			int nextColorB = this.getRandom(256);

			for (int i2 = 0; i2 < listList.get(i).size(); i2++) {
				Node source = listList.get(i).get(i2).getSourceNode();
				Node target = listList.get(i).get(i2).getTargetNode();
				//String name = source.toString() + target.toString();
				String name = listList.get(i).get(i2).getId();

				graph.getEdge(name).addAttribute("ui.style",
						"fill-color: rgb(" + nextColorR + "," + nextColorG + "," + nextColorB + ");");
			}

		}
	}

	private int getRandom(int value) {
		Random random = new Random();
		return random.nextInt(value);
	}

	/**
	 * Setzt die Node labels
	 */
	public void setFlagToNodeLabel(Graph graph, String flag) {

		for (Node node : graph.getNodeSet()) {
			if (node.getAttribute(flag) != null) {
				String value = node.getAttribute(flag).toString();
				node.setAttribute("ui.label", flag + "(" + node.getAttribute("ui.label") + ") = " + value);
			}
		}

	}

	public void setDefaultLabels(Graph graph) {

		for (Edge edge : graph.getEdgeSet()) {
			edge.addAttribute("ui.style", "fill-color: rgb(" + DEFAULT_R + "," + DEFAULT_G + "," + DEFAULT_B + ");");
		}

		for (Node node : graph.getNodeSet()) {
			node.setAttribute("ui.label", node.getId());
			node.addAttribute("ui.style", "fill-color: rgb(" + DEFAULT_R + "," + DEFAULT_G + "," + DEFAULT_B + ");");
		}

	}

}
