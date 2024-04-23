package mvc.model.fileExtensionSystem.Convertion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * Diese Klasse ist dafür zuständige einen Graphen aus einer .graph-Datei in ein
 * GraphObjekt zu konvertieren oder ein GraphObjekt in eine Liste mit Zeilen die
 * die einzelnene Knoten und die Kanten dazu enthält zu konvertieren.
 */
public class GraphFileConverter extends FileConverter {

	/**
	 * Mögliche Muster die eine .graph-Datei haben kann
	 */
	private final String REGEX_DIRECTED = "# ?directed;";
	private final String REGEX_IDENTIFIER = "[A-za-z0-9ÄäÖöÜü]+";
	private final String REGEX_ATTRIBUTE = "(:[0-9]+)";
	private final String REGEX_EDGE = "( \\([A-Za-z0-9ÄäÖöÜü]+\\))";
	private final String REGEX_WEIGHT = "( :: [0-9]+)";
	private final String REGEX_DIGITS = "[0-9]+";
	private final String REGEX_PART1 = REGEX_IDENTIFIER + REGEX_ATTRIBUTE + "?";
	private final String REGEX_PART2 = "(," + REGEX_IDENTIFIER + REGEX_ATTRIBUTE + "?" + REGEX_EDGE + "?" + REGEX_WEIGHT
			+ "?" + ")?";

	private final Pattern PATTERN_ORIENTATION = Pattern.compile(REGEX_DIRECTED);
	private final Pattern PATTERN_VALID_ROW = Pattern.compile(REGEX_PART1 + REGEX_PART2 + ";");

	private final Pattern PATTERN_IDENTIFIER = Pattern.compile(REGEX_IDENTIFIER);
	private final Pattern PATTERN_ATTRIBUTE = Pattern.compile("(?!" + REGEX_IDENTIFIER + ")" + REGEX_ATTRIBUTE);
	private final Pattern PATTERN_EDGE = Pattern
			.compile("(?!" + REGEX_IDENTIFIER + ")" + "(?!" + REGEX_ATTRIBUTE + ")" + REGEX_EDGE);
	private final Pattern PATTERN_WEIGHT = Pattern.compile(
			"(?!" + REGEX_IDENTIFIER + ")" + "(?!" + REGEX_ATTRIBUTE + ")" + "(?!" + REGEX_EDGE + ")" + REGEX_WEIGHT);
	private final Pattern PATTERN_DIGITS = Pattern.compile(REGEX_DIGITS);

	public List<AbstractEdge> edgesList = new ArrayList<AbstractEdge>();
	private boolean orientation = false;

	/**
	 * Diese Methode erstellt aus den Zeilen der .graph-Datei einen Graphen. In
	 * dem jede einzelne Zeile nach den vorgegebenen möglichen Mustern gesucht
	 * wird.
	 * 
	 * @param pattern
	 *            Liste die Zeilen aus der .graph-Datei enthalten soll
	 */
	@Override
	public Graph fileToGraphObject(List<String> rows) {

		MultiGraph graph = new MultiGraph("Graph");
		List<String> nodes = new ArrayList<String>();
		List<String> edges = new ArrayList<String>();
		int edgeId = 0;

		// Flag gerichtet oder ungerichteter Graph
		orientation = this.getGraphOrientation(rows.get(0));
		; // false für ungerichtet, true für

		/*
		 * Zusammenbauen des Graph
		 */
		for (int i = 0; i < rows.size(); i++) {
			boolean validRow = PATTERN_VALID_ROW.matcher(rows.get(i)).matches();

			// Nur wenn die Zeile überhaupt valid ist, dann lege los
			if (validRow) {

				String part1 = "";
				String part2 = "";

				if (rows.get(i).contains(",")) {
					part1 = rows.get(i).substring(0, rows.get(i).indexOf(","));
					part2 = rows.get(i).substring(rows.get(i).indexOf(","));
				} else {
					part1 = rows.get(i);
				}

				String node1 = this.patternFromString(PATTERN_IDENTIFIER, part1);
				String attribute1 = this.patternFromString(PATTERN_ATTRIBUTE, part1);
				String node2 = this.patternFromString(PATTERN_IDENTIFIER, part2);
				String attribute2 = this.patternFromString(PATTERN_ATTRIBUTE, part2);
				String edgepart = this.patternFromString(PATTERN_EDGE, part2);
				String weightpart = this.patternFromString(PATTERN_WEIGHT, part2);

				String edge = null;
				if (edgepart != null) {
					edge = this.patternFromString(PATTERN_IDENTIFIER, edgepart);
				}

				String weight = null;
				if (weightpart != null) {
					weight = this.patternFromString(PATTERN_DIGITS, weightpart);
				}

				this.addNodeToGraph(node1, nodes, graph);
				this.addAttributeToNode(node1, attribute1, graph);

				this.addNodeToGraph(node2, nodes, graph);
				this.addAttributeToNode(node2, attribute2, graph);

				
				/*
				 * Kante hinzuf�gen, nur wenn zwei Knoten da sind
				 */
				if ((node2 != null)) {
					if (!(edges.contains("e" + edgeId))) {
						edges.add("e" + edgeId);

						graph.addEdge("e" + edgeId, node1, node2, orientation);

						if (edge != null) {
							// Edge als Attribut hinzufügen

							if (weight != null) {
								graph.getEdge("e" + edgeId).addAttribute("ui.label", edge);
								graph.getEdge("e" + edgeId).setAttribute("weight", Integer.valueOf(weight).intValue());
								graph.getEdge("e" + edgeId).addAttribute("ui.label",
										graph.getEdge("e" + edgeId).getAttribute("ui.label") + " :: "
												+ Integer.valueOf(weight));
							} else {
								graph.getEdge("e" + edgeId).addAttribute("ui.label", edge);
							}
						} else if (weight != null) {
							graph.getEdge("e" + edgeId).setAttribute("weight", Integer.valueOf(weight));
							graph.getEdge("e" + edgeId).addAttribute("ui.label", Integer.valueOf(weight));
						}

					}
					edgeId++;
				}

			}

		}
		return graph;
	}

	/**
	 * Hilfsmethode die ein übergebenes Pattern-Muster auf einen String anwendet
	 * 
	 * @param pattern
	 *            Das gewünschte Muster
	 * @param string
	 *            Der String in dem das Muster gesucht werden soll
	 * @return
	 */
	private String patternFromString(Pattern pattern, String string) {
		String result = null;
		Matcher matcher = null;

		matcher = pattern.matcher(string);
		if (matcher.find()) {
			result = matcher.group(0);
		}

		return result;
	}

	/**
	 * Fügt dem Graphen einen Knoten hinzu
	 * 
	 * @param node
	 *            Knotenname
	 * @param nodes
	 *            Liste aller Knoten
	 * @param graph
	 *            Graph dem der Knoten hinzugefügt werden soll
	 */
	private void addNodeToGraph(String node, List<String> nodes, Graph graph) {
		if (node != null && !nodes.contains(node)) {
			nodes.add(node);
			graph.addNode(node);
			graph.getNode(node).addAttribute("ui.label", graph.getNode(node).getId());
		}
	}

	/**
	 * Fügt einem Knoten ein Attribut hinzu
	 * 
	 * @param node
	 *            Knotem dem das Attribut hinzugefügt werden soll
	 * @param attribute
	 *            Das Attribute, welches hinzugefügt werden soll
	 * @param graph
	 *            Der Graph, der den Knoten enthält
	 */
	private void addAttributeToNode(String node, String attribute, Graph graph) {
		if (attribute != null) {
			graph.getNode(node).addAttribute("attr1", attribute.substring(1));
		}
	}

	/**
	 * Ermittelt ob der Graph der .graph-Datei directed oder undirected ist
	 * 
	 * @param header
	 * @return
	 */
	private boolean getGraphOrientation(String header) {
		boolean toReturn = false;
		if (PATTERN_ORIENTATION.matcher(header).matches()) {
			toReturn = true;
		}

		return toReturn;
	}

	/**
	 * Wandelt einen Graphen in eine Liste mit Zeilen um, die die einzelnene
	 * Knoten und die Kanten dazu enthält
	 */
	@Override
	public List<String> graphObjectToString(Graph graph) {
		System.out.print("to save graph: " + graph.toString());
		List<String> out = new ArrayList<String>();

		// Schau ob eine Kante gerichtet ist, dann ist der Graph gerichtet
		for (Edge edge : graph.getEachEdge()) {
			if (edge.isDirected()) {
				out.add("#directed;");
				break;
			}
		}

		for (Edge edge : graph.getEachEdge()) {
			String row;
			String part1 = edge.getNode0().toString();
			if (edge.getNode0().getAttribute("attr1") != null) {
				part1 = part1 + ":" + edge.getNode0().getAttribute("attr1");
			}

			String part2 = edge.getNode1().toString();
			if (edge.getNode1().getAttribute("attr2") != null) {
				part2 = part2 + ":" + edge.getNode1().getAttribute("attr2");
			}

			// TODO Kantenbezeichnung abspeichern geht noch nicht

			if (edge.getAttribute("weight") != null) {
				part2 = part2 + " :: " + edge.getAttribute("weight");
			}

			row = part1 + "," + part2 + ";";
			out.add(row);
		}

		for (int i = 0; i < out.size() - 1; i++) {
			System.out.print(out.get(i));
		}

		return out;
	}

}
