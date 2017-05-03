package test.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;

import mvc.model.algorithmen.BreadthFirstSearch;
import mvc.model.algorithmen.Dijkstra;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class JUnitTestDijkstra {
	
	GraphFileExtensionHandler fileHandler;
	Graph graph1;
	Graph graph2;
	Node source_01;
	Node source_02;
	Node source_03;
	Node source_04;
	Node source_05;
	Node step_01_01;
	Node step_01_02;
	Node step_04_01;
	Node target_01;
	Node target_02;
	Node target_03;
	Node target_04;
	Node target_05;
	List<Node> shortestPathTest1;
	List<Node> shortestPathTest2;
	List<Node> shortestPathTest3;
	List<Node> shortestPathTest4;
	List<Node> shortestPathTest5;
	Dijkstra djk1;
	Dijkstra djk2;
	Dijkstra djk3;
	Dijkstra djk4;
	Dijkstra djk5;
	

	@Before
	public void setUp() throws Exception {
		this.fileHandler = new GraphFileExtensionHandler();
		
		// Test 1 - 9.2.1.1
		this.graph1 = fileHandler.loadGraph("db/testCases/JUnitTest_Dijkstra_directed.graph");
		this.djk1 = new Dijkstra(graph1);
		this.shortestPathTest1 = new ArrayList<Node>();	
		source_01 = graph1.getNode("Start");
		step_01_01 = graph1.getNode("BesserWeg");
		step_01_02 = graph1.getNode("Zwischenschritt");
		target_01 = graph1.getNode("Ziel");
		
		shortestPathTest1.add(source_01);
		shortestPathTest1.add(step_01_01);
		shortestPathTest1.add(step_01_02);
		shortestPathTest1.add(target_01);
		
		// Test 2 - 9.2.1.2
		this.djk2 = new Dijkstra(graph1);
		this.shortestPathTest2 = new ArrayList<Node>();	
		source_02 = graph1.getNode("Start");
		target_02 = graph1.getNode("Graph_ausserhalb_Knoten_1");
		
		// Test 3 - 9.2.1.3
		this.djk3 = new Dijkstra(graph1);
		this.shortestPathTest3 = new ArrayList<Node>();	
		source_03 = graph1.getNode("Start");
		target_03 = graph1.getNode("unerreichbares_Ziel");
		
		// Test 4 - 9.2.2.1
		this.graph2 = fileHandler.loadGraph("db/testCases/JUnitTest_Dijkstra_ungerichtet.graph");
		this.djk4 = new Dijkstra(graph2);
		this.shortestPathTest4 = new ArrayList<Node>();	
		source_04 = graph2.getNode("Start");
		step_04_01 = graph2.getNode("Zwischenschritt");
		target_04 = graph2.getNode("Ziel");
		
		shortestPathTest4.add(source_04);
		shortestPathTest4.add(step_04_01);
		shortestPathTest4.add(target_04);
			
		// Test 5 - 9.2.2.2
		this.djk5 = new Dijkstra(graph2);
		this.shortestPathTest5 = new ArrayList<Node>();	
		source_05 = graph2.getNode("Start");
		target_05 = graph2.getNode("Graph_ausserhalb_Knoten_1");
	
		
	}

	@Test
	public void testDijkstraWeightedDirected() {
	
		// Test 1 - 9.2.1.1
		djk1.compute(source_01, target_01);
		assertEquals(djk1.getShortestPath(), shortestPathTest1);
		
		
		// Test 2 - 9.2.1.2
		djk2.compute(source_02, target_02);
		assertEquals(djk2.getShortestPath(), shortestPathTest2);
		
		// Test 3 - 9.2.1.3
		djk3.compute(source_03, target_03);
		assertEquals(djk3.getShortestPath(), shortestPathTest3);
		
	}
	
	@Test
	public void testDijkstraWeightedUnDirected() {
		// Test 4 - 9.2.2.1
		djk4.compute(source_04, target_04);
		assertEquals(djk4.getShortestPath(), shortestPathTest4);
		
		
		// Test 5 - 9.2.2.2
		djk5.compute(source_05, target_05);
		assertEquals(djk5.getShortestPath(), shortestPathTest5);
		
		
	}

}
