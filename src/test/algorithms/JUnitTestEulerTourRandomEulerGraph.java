package test.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;

import mvc.model.algorithmen.eulerTour.EulerTour;
import mvc.model.algorithmen.eulerTour.EulerTourFactory;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;
import mvc.model.graphGenerator.GraphGenerator;
import mvc.model.graphGenerator.GraphGeneratorFactory;

public class JUnitTestEulerTourRandomEulerGraph {

	private EulerTour fleury;
	private EulerTour hierholzer;
	private GraphGenerator randomEuler;
	private LinkedList<Graph> graphs;

	private static final int RUNS = 20;

	/*
	 * Dieser Test läd randomEulerGraphen und prüft ob Fleury und Hierholzer
	 * korrekte Kreise ermitteln
	 */
	@Before
	public void setUp() throws Exception {
		GraphFileExtensionHandler fileHandler = new GraphFileExtensionHandler();
		this.randomEuler = GraphGeneratorFactory.getInstance("Euler");

		this.fleury = EulerTourFactory.getInstance("Fleury");
		this.hierholzer = EulerTourFactory.getInstance("Hierholzer");
		this.graphs = new LinkedList<Graph>();

		for (int counter = 0; counter < RUNS; counter++) {
			Graph graph = fileHandler.loadGraph("db/testCases/eulercircle/randomEulerGraph" + counter + ".graph");
			this.graphs.add(graph);
			System.out.println("load: " + counter);
			System.out.println(graph.getNodeSet().size());
			System.out.println(graph.getEdgeSet().size());
		}
	}

	/*
	 * Positivtest
	 */

	/*
	 * Laufzeittest und ist der Kreis eine abfolge?
	 */
	@Test
	public void FleuryEulerCycleRandom() {

		for (int counter = 0; counter < RUNS; counter++) {

			Graph graph = this.graphs.get(counter);

			List<Edge> result = this.fleury.calculate(graph);

			/*
			 * Eulerkreisgröße == Graph Kantenazahl?
			 */
			Set<Edge> circle = new HashSet<Edge>();
			circle.addAll(result);

			assertEquals(circle.size(), graph.getEdgeSet().size());

			/*
			 * Ist der Eulerkreis eine Abfolge?
			 */
			List<Edge> eulerCircle = result;

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

	/*
	 * Laufzeittest und ist der Kreis eine abfolge?
	 */
	@Test
	public void HierholzerEulerCycleRandom() {

		for (int counter = 0; counter < RUNS; counter++) {

			Graph graph = this.graphs.get(counter);

			List<Edge> result = this.hierholzer.calculate(graph);

			/*
			 * Eulerkreisgröße == Graph Kantenazahl?
			 */
			Set<Edge> circle = new HashSet<Edge>();
			circle.addAll(result);

			assertEquals(circle.size(), graph.getEdgeSet().size());

			/*
			 * Ist der Eulerkreis eine Abfolge?
			 */
			List<Edge> eulerCircle = result;

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
}
