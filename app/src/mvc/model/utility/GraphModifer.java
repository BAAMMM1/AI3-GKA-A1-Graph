package mvc.model.utility;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * Diese Klasse kann einen undirected Graph in einen directed Graph konvertieren
 * 
 * @author Chris
 *
 */
public class GraphModifer {

	public GraphModifer() {
	}

	/**
	 * Konvertiert einen undirected Graph zu einem directed Graph, damit der
	 * Algorithmus auf ihn ausgeführt werden kann Nicht gut hier
	 * 
	 * TODO Kantengewichtungen an beide Kanten
	 * 
	 * @param graph
	 *            Graph der konvertiert werden soll
	 * @return Konvertierter Graph mit directed = true
	 */
	public Graph converteUndirectedToDirected(Graph graph) {

		List<Edge> edges = new ArrayList();
		edges.addAll(graph.getEdgeSet());

		for (int i = 0; i < edges.size(); i++) {
			Edge toLook = edges.get(i);
			System.out.println(i);
			System.out.println("Schaue an: " + toLook.toString());
			boolean needToAdd = true;
			Node source = toLook.getSourceNode();
			Node target = toLook.getTargetNode();

			// UI Laber mit der bezeichnung fehlt noch)
			graph.removeEdge(source.toString() + target.toString());
			graph.addEdge(source.toString() + target.toString(), source, target, true);

			if (toLook.getAttribute("weight") != null) {
				int weigt = toLook.getAttribute("weight");
				graph.getEdge(source.toString() + target.toString()).setAttribute("weight", weigt);
			}

			if (toLook.getAttribute("ui.label") != null) {
				graph.getEdge(source.toString() + target.toString()).setAttribute("ui.label",
						toLook.getAttribute("ui.label").toString());
			}

			Edge toLook2 = graph.getEdge(target.toString() + source.toString());
			if (toLook2 != null) {
				System.out.println(target.getId().toString() + source.getId().toString());
				graph.removeEdge(target.getId().toString() + source.getId().toString());
				graph.addEdge(target.toString() + source.getId().toString(), target, source, true);

				if (toLook2.getAttribute("weight") != null) {
					int weigt2 = toLook2.getAttribute("weight");
					graph.getEdge(target.toString() + source.toString()).setAttribute("weight", weigt2);
				}
				if (toLook2.getAttribute("ui.label") != null) {
					graph.getEdge(target.toString() + source.toString()).setAttribute("label",
							toLook2.getAttribute("ui.label").toString());
				}
			} else {
				graph.addEdge(target.toString() + source.toString(), target, source, true);
				if (toLook.getAttribute("weight") != null) {
					int weigt = toLook.getAttribute("weight");
					graph.getEdge(target.toString() + source.toString()).setAttribute("weight", weigt);
				}

				if (toLook.getAttribute("ui.label") != null) {
					graph.getEdge(target.toString() + source.toString()).setAttribute("ui.label",
							toLook.getAttribute("ui.label").toString());
				}
			}

		}

		for (Edge edge : graph.getEachEdge()) {
			System.out.println(edge.toString());
		}

		return graph;
	}

}
