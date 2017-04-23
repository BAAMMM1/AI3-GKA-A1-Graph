package mvc.model.fileExtensionSystem.Convertion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.MultiGraph;

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
	private final String REGEX_PART2 = "(," + REGEX_IDENTIFIER + REGEX_ATTRIBUTE + "?" + REGEX_EDGE + "?" + REGEX_WEIGHT + "?" + ")?";

	private final Pattern PATTERN_ORIENTATION = Pattern.compile(REGEX_DIRECTED);
	private final Pattern PATTERN_VALID_ROW = Pattern.compile(REGEX_PART1 + REGEX_PART2 + ";");

	private final Pattern PATTERN_IDENTIFIER = Pattern.compile(REGEX_IDENTIFIER);
	private final Pattern PATTERN_ATTRIBUTE = Pattern.compile("(?!" + REGEX_IDENTIFIER + ")" + REGEX_ATTRIBUTE);
	private final Pattern PATTERN_EDGE = Pattern.compile("(?!" + REGEX_IDENTIFIER + ")" + "(?!" + REGEX_ATTRIBUTE + ")" + REGEX_EDGE);
	private final Pattern PATTERN_WEIGHT = Pattern.compile("(?!" + REGEX_IDENTIFIER + ")" + "(?!" + REGEX_ATTRIBUTE + ")" + "(?!" + REGEX_EDGE + ")" + REGEX_WEIGHT);
	private final Pattern PATTERN_DIGITS = Pattern.compile(REGEX_DIGITS);

	public List<AbstractEdge> edgesList = new ArrayList<AbstractEdge>();
	private boolean orientation = false;

	@Override
	public Graph fileToGraphObject(List<String> rows) {

		MultiGraph graph = new MultiGraph("Test");
		List<String> nodes = new ArrayList<String>();
		List<String> edges = new ArrayList<String>();
		
		// Flag gerichtet oder ungerichteter Graph
		orientation = this.getGraphOrientation(rows.get(0));	; // false für ungerichtet, true für

		/*
		 * Zusammenbauen des Graph
		 */
		for (int i = 0; i < rows.size(); i++) {
			boolean validRow = PATTERN_VALID_ROW.matcher(rows.get(i)).matches();
			/*
			System.out.println("-----------------------------");
			System.out.println(
					"row: - " + rows.get(i) + " - is valid: " + validRow);
					*/

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
				
				String edge = null; // TODO Wo soll der rein?
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
			
				if ((node2 != null)) {
					if (!(edges.contains(node1 + node2))) {
						edges.add(node1 + node2);
						
						graph.addEdge(node1 + node2, node1, node2, orientation);						
						
						if (edge != null) {				
						
							if (weight != null) {
								graph.getEdge(node1 + node2).addAttribute("ui.label", edge);
								graph.getEdge(node1 + node2).setAttribute("weight", Integer.valueOf(weight));
								graph.getEdge(node1 + node2).addAttribute("ui.label", graph.getEdge(node1 + node2).getAttribute("ui.label") + " :: " + Integer.valueOf(weight));
							} else {
								graph.getEdge(node1 + node2).addAttribute("ui.label", edge);
							}
						} else if (weight != null){
							graph.getEdge(node1 + node2).setAttribute("weight", Integer.valueOf(weight));
							graph.getEdge(node1 + node2).addAttribute("ui.label", Integer.valueOf(weight));
						}
						
					}
				}
				
				/*
				System.out.println("Part1: " + part1);
				System.out.println("Node1: " + node1);
				System.out.println("Attribute1: " + attribute1);
				System.out.println("Part2: " + part2);
				System.out.println("Node2: " + node2);
				System.out.println("Attribute2: " + attribute2);
				System.out.println("Edge: " + edge);
				System.out.println("Weight: " + weight);
				System.out.println("Edge weight: " + edge);
				System.out.println("");
				*/
				
				//TODO Ausgabe der Kosten

			}

		}
		
		/*
		System.out.println(edgesList.toString());
		
		List<AbstractEdge> edgesTest = new ArrayList<AbstractEdge>();
		edgesTest.addAll(graph.getEdgeSet());
		
		List<AbstractEdge> temp = new ArrayList<AbstractEdge>();
		
		for(int i = 0 ; i< edgesTest.size() -1 ; i++){			
			
			if(edgesTest.get(i).getSourceNode() == graph.getNode("a")){
				temp.add(edgesTest.get(i));
			}
		}
		
		System.out.println(temp.toString());
		*/

		return graph;
	}

	private String patternFromString(Pattern pattern, String string) {
		String result = null;
		Matcher matcher = null;

		matcher = pattern.matcher(string);
		if (matcher.find()) {
			result = matcher.group(0);
		}

		return result;
	}
	
	private void addNodeToGraph(String node, List<String> nodes, Graph graph){
		if (node != null && !nodes.contains(node)) {
			nodes.add(node);
			graph.addNode(node);
			graph.getNode(node).addAttribute("ui.label", graph.getNode(node).getId());
		}
	}
	
	private void addAttributeToNode(String node, String attribute, Graph graph){
		if (attribute != null) {
			graph.getNode(node).addAttribute("attr1", attribute.substring(1));
		}
	}
	
	private boolean getGraphOrientation(String header){
		boolean toReturn = false;
		if (PATTERN_ORIENTATION.matcher(header).matches()) {
			toReturn = true;
		}		
		System.out.println("Graph is directed: " + toReturn);
		
		return toReturn;		
	}

	@Override
	public List<String> graphObjectToString(Graph graph) {
		System.out.print("to save graph: " + graph.toString());
		List<String> out = new ArrayList<String>();
		
		// Schau ob eine Kante gerichtet ist, dann ist der Graph gerichtet
		for(Edge edge : graph.getEachEdge()) {
			if(edge.isDirected() == true){
				out.add("#directed;");
				break;
			}
		}
		
		
		
		for(Edge edge : graph.getEachEdge()) {
           String row;
           String part1 = edge.getNode0().toString();
           if(edge.getNode0().getAttribute("attr1") != null){        	   
        	   part1 = part1 + ":" + edge.getNode0().getAttribute("attr1");
           }
           
           String part2 = edge.getNode1().toString();
           if(edge.getNode1().getAttribute("attr2") != null){        	   
        	   part2 = part2 + ":" + edge.getNode1().getAttribute("attr2");
           }
           
           //TODO Kantenbezeichnung abspeichern geht noch nicht
           
           if(edge.getAttribute("weight") != null){        	   
        	   part2 = part2 + " :: " + edge.getAttribute("weight");
           }
      
           
           
           row = part1 + "," + part2 +";";
           out.add(row);
        }
		
		for(int i = 0; i < out.size() -1; i++){
			System.out.print(out.get(i));
		}		

		return out;
	}
	


}
