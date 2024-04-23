package test.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;

import mvc.model.algorithmen.eulerTour.EulerTour;
import mvc.model.algorithmen.eulerTour.EulerTourFactory;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class JUnitTestEulerTour {

	private GraphFileExtensionHandler fileHandler;
	private EulerTour fleury;
	private EulerTour hierholzer;

	@Before
	public void setUp() throws Exception {
		this.fileHandler = new GraphFileExtensionHandler();

		this.fleury = EulerTourFactory.getInstance("Fleury");
		this.hierholzer = EulerTourFactory.getInstance("Hierholzer");
	}

	/*
	 * Negative-Test (Preconditions)
	 */

	/*
	 * Graph ist nicht zusammenhängend
	 */
	@Test(expected = IllegalArgumentException.class)
	public void preFleuryUnConnected() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler03UnconnectedPart.graph");
		this.fleury.calculate(graph);
	}

	@Test(expected = IllegalArgumentException.class)
	public void preHierholzerUnConnected() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler03UnconnectedPart.graph");
		this.hierholzer.calculate(graph);
	}

	/*
	 * Graph nicht undirected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void preFleuryDirected() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler04Directed.graph");
		this.fleury.calculate(graph);
	}

	@Test(expected = IllegalArgumentException.class)
	public void preHierholzerDirected() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler04Directed.graph");
		this.hierholzer.calculate(graph);
	}

	/*
	 * Nicht alle Knotengrade sind gerade
	 */
	@Test(expected = IllegalArgumentException.class)
	public void preFleuryNotAllEven() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler05NotAllEven.graph");
		this.fleury.calculate(graph);
	}

	@Test(expected = IllegalArgumentException.class)
	public void preHierholzerNotAllEven() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler05NotAllEven.graph");
		this.hierholzer.calculate(graph);
	}

	/*
	 * Kantenanzahl kleiner als Knotenanzahl ist
	 */
	@Test(expected = IllegalArgumentException.class)
	public void preFleuryEdgesLowerThenNodes() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler06EdgesLowerThenNodes.graph");
		this.fleury.calculate(graph);
	}

	@Test(expected = IllegalArgumentException.class)
	public void preHierholzerEdgesLowerThenNodes() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler06EdgesLowerThenNodes.graph");
		this.hierholzer.calculate(graph);
	}

	/*
	 * Positivtest
	 */
	

	/*
	 * Beinhaltet der Kreis alle Kanten? Kantensize - Result Size, dann über ein Set, damit
	 * Duplikate ausgeschlossen sind.
	 */
	@Test
	public void FleuryEulerCycleComplete() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler01.graph");
		List<Edge> result = this.fleury.calculate(graph);
		
		assertEquals(result.size(), graph.getEdgeSet().size());
		
		Set<Edge> circle = new HashSet<Edge>();
		circle.addAll(result);	
		assertEquals(circle.size(), graph.getEdgeSet().size());
	}

	@Test
	public void HierholzerCycleComplete() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler01.graph");
		List<Edge> result = this.hierholzer.calculate(graph);
		
		assertEquals(result.size(), graph.getEdgeSet().size());
		
		Set<Edge> circle = new HashSet<Edge>();
		circle.addAll(result);

		assertEquals(circle.size(), graph.getEdgeSet().size());
	}

	/*
	 * Ist der Kreis eine abfolge und ist der Start das Ziel?
	 */
	@Test
	public void FleuryEulerCycleIsSequence() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler01.graph");

		List<Edge> eulerCircle = this.fleury.calculate(graph);

		/*
		 * Jede Kante muss einen Knoten von der nächsten Kanten ansteuern
		 */
		for (int i = 0; i < eulerCircle.size() - 1; i++) {
			Set<Node> edgeNodes = new HashSet<Node>();
			edgeNodes.add(eulerCircle.get(i).getSourceNode());
			edgeNodes.add(eulerCircle.get(i).getTargetNode());

			assertTrue(edgeNodes.contains(eulerCircle.get(i + 1).getSourceNode())
					|| edgeNodes.contains(eulerCircle.get(i + 1).getTargetNode()));
		}

		/*
		 * Letzte Kante muss wieder an Start zurück gehen
		 */
		Set<Node> startEdgeNodes = new HashSet<Node>();
		startEdgeNodes.add(eulerCircle.get(0).getSourceNode());
		startEdgeNodes.add(eulerCircle.get(0).getTargetNode());

		assertTrue(startEdgeNodes.contains(eulerCircle.get(eulerCircle.size() - 1).getSourceNode())
				|| startEdgeNodes.contains(eulerCircle.get(eulerCircle.size() - 1).getTargetNode()));

	}

	@Test
	public void HierholzerEulerCycleIsSequence() {
		Graph graph;
		graph = this.fileHandler.loadGraph("db/testCases/eulercircle/euler01.graph");

		List<Edge> eulerCircle = this.hierholzer.calculate(graph);

		/*
		 * Jede Kante muss einen Knoten von der nächsten Kanten ansteuern
		 */
		for (int i = 0; i < eulerCircle.size() - 1; i++) {
			Set<Node> edgeNodes = new HashSet<Node>();
			edgeNodes.add(eulerCircle.get(i).getSourceNode());
			edgeNodes.add(eulerCircle.get(i).getTargetNode());

			assertTrue(edgeNodes.contains(eulerCircle.get(i + 1).getSourceNode())
					|| edgeNodes.contains(eulerCircle.get(i + 1).getTargetNode()));
		}

		/*
		 * Letzte Kante muss wieder an Start zurück gehen
		 */
		Set<Node> startEdgeNodes = new HashSet<Node>();
		startEdgeNodes.add(eulerCircle.get(0).getSourceNode());
		startEdgeNodes.add(eulerCircle.get(0).getTargetNode());

		assertTrue(startEdgeNodes.contains(eulerCircle.get(eulerCircle.size() - 1).getSourceNode())
				|| startEdgeNodes.contains(eulerCircle.get(eulerCircle.size() - 1).getTargetNode()));

	}


}
