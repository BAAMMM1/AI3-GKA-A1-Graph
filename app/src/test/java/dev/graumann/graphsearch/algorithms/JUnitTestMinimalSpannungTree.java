package dev.graumann.graphsearch.algorithms;

import static org.junit.Assert.assertEquals;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.junit.Before;
import org.junit.Test;

import dev.graumann.graphsearch.mvc.model.algorithmen.minimalSpanningTree.Kruskal;
import dev.graumann.graphsearch.mvc.model.algorithmen.minimalSpanningTree.Prim;
import dev.graumann.graphsearch.mvc.model.exceptions.IllegalDirectedGraph;
import dev.graumann.graphsearch.mvc.model.exceptions.IllegalNotConnectedGraph;
import dev.graumann.graphsearch.mvc.model.exceptions.IllegalWeightedGraph;
import dev.graumann.graphsearch.mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class JUnitTestMinimalSpannungTree {

	private GraphFileExtensionHandler fileHandler;
	private Kruskal kruskal_random100_200, kruskalPreconditions, kruskalMinimalSpanningTree;
	private Prim prim_random100_200, primPreconditions, primMinimalSpanningTree;
	private org.graphstream.algorithm.Kruskal stdKruskal_random100_200;
	private org.graphstream.algorithm.Prim stdPrim_random100_200;

	@Before
	public void setUp() throws Exception {
		this.fileHandler = new GraphFileExtensionHandler();

		this.kruskalPreconditions = new Kruskal();
		this.primPreconditions = new Prim();

		this.kruskalMinimalSpanningTree = new Kruskal();
		this.primMinimalSpanningTree = new Prim();

		this.kruskal_random100_200 = new Kruskal();
		this.prim_random100_200 = new Prim();
		this.stdKruskal_random100_200 = new org.graphstream.algorithm.Kruskal();
		this.stdPrim_random100_200 = new org.graphstream.algorithm.Prim();
		this.initAlgorithmus("/dev/graumann/graphsearch/testcases/random/random100_200.graph", kruskal_random100_200, prim_random100_200,
				stdKruskal_random100_200, stdPrim_random100_200);

		// Precondition
	}

	private void initAlgorithmus(String path, Kruskal kruskal, Prim prim,
			org.graphstream.algorithm.Kruskal standardKruskal, org.graphstream.algorithm.Prim standardPrim) {

		Graph graph = this.fileHandler.loadGraphFromResources(path);

		kruskal.calculate(graph);
		prim.calculate(graph);

		standardKruskal.init(graph);
		standardKruskal.compute();

		standardPrim.init(graph);
		standardPrim.compute();
	}

	/*
	 * Preconditions - Undirected
	 */
	@Test(expected = IllegalDirectedGraph.class)
	public void preconditionKruskalUndirectedGraph() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/directed.graph");
		this.kruskalPreconditions.calculate(graphPreConditions);
	}

	@Test(expected = IllegalDirectedGraph.class)
	public void preconditionPrimUndirectedGraph() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/directed.graph");
		this.primPreconditions.calculate(graphPreConditions);
	}

	/*
	 * Preconditions - Weighted
	 */
	@Test(expected = IllegalWeightedGraph.class)
	public void preconditionKruskalWeighted() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/unweighted.graph");
		this.kruskalPreconditions.calculate(graphPreConditions);
	}

	@Test(expected = IllegalWeightedGraph.class)
	public void preconditionPrimWeighted() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/unweighted.graph");
		this.primPreconditions.calculate(graphPreConditions);
	}

	/*
	 * Preconditions - Negativ Weighted
	 */
	@Test(expected = IllegalWeightedGraph.class)
	public void preconditionKruskalNegativWeighted() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/negativeWeighted.graph");
		graphPreConditions.getEdge(0).setAttribute("weight", Integer.valueOf(-1));
		this.kruskalPreconditions.calculate(graphPreConditions);
	}

	@Test(expected = IllegalWeightedGraph.class)
	public void preconditionPrimNegativWeighted() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/negativeWeighted.graph");
		graphPreConditions.getEdge(0).setAttribute("weight", Integer.valueOf(-1));
		this.primPreconditions.calculate(graphPreConditions);
	}

	/*
	 * Preconditions - Connected
	 */
	@Test(expected = IllegalNotConnectedGraph.class)
	public void preconditionKruskalConnected() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/unconnected.graph");
		this.kruskalPreconditions.calculate(graphPreConditions);
	}

	@Test(expected = IllegalNotConnectedGraph.class)
	public void preconditionPrimConnected() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/unconnected.graph");
		this.primPreconditions.calculate(graphPreConditions);
	}

	@Test
	public void primSimpleTest() {
		assertEquals(this.prim_random100_200.getEdgeWeightes(), 3256.0, 0);
		/*
		 * Kreisfreiheit
		 * Wenn der Testdurchgelaufen ist muss ein Spannbaum entstanden sein.
		 * Spannbaumtest => Kreisfreiheit
		 */
		assertEquals(this.prim_random100_200.getKantenAnzahl(), this.prim_random100_200.getKnotenAnzahl() - 1, 0);
	}

	@Test
	public void primVSStandardImplementation() {
		assertEquals(this.prim_random100_200.getEdgeWeightes(), stdPrim_random100_200.getTreeWeight(), 0);
	}

	@Test
	public void kruskalSimpleTest() {
		assertEquals(this.kruskal_random100_200.getEdgeWeightes(), 3256.0, 0);
		/*
		 * Kreisfreiheit
		 * Wenn der Testdurchgelaufen ist muss ein Spannbaum entstanden sein.
		 * Spannbaumtest => Kreisfreiheit
		 */
		assertEquals(this.kruskal_random100_200.getKantenAnzahl(), this.kruskal_random100_200.getKnotenAnzahl() - 1, 0);
	}

	@Test
	public void kruskalVSStandardImplementation() {
		assertEquals(this.kruskal_random100_200.getEdgeWeightes(), stdKruskal_random100_200.getTreeWeight(), 0);
	}

	@Test
	public void primVSKruskal() {
		/*
		 * Kruskal müssen das selbe Ergebnis haben
		 */
		assertEquals(this.prim_random100_200.getEdgeWeightes(), this.kruskal_random100_200.getEdgeWeightes(), 0);
	}

	/*
	 * Test ob die Kantengewichtung eines minimalen Spannbaum auch nach ein der
	 * Anwendung der Algorithmen gleiche bleibt.
	 */
	@Test
	public void minimalSpanningTree() {
		Graph graphPreConditions;
		graphPreConditions = this.fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/minimalSpanningTree/minimalSpanningTree.graph");

		double result = 0;
		for(Edge egde : graphPreConditions.getEdgeSet()){
			result += ((Integer)egde.getAttribute("weight")).intValue();
		}
		
		this.kruskalMinimalSpanningTree.calculate(graphPreConditions);
		this.primMinimalSpanningTree.calculate(graphPreConditions);

		assertEquals(kruskalMinimalSpanningTree.getEdgeWeightes(), result, 0.0);
		assertEquals(primMinimalSpanningTree.getEdgeWeightes(), result, 0.0);
	}

}
