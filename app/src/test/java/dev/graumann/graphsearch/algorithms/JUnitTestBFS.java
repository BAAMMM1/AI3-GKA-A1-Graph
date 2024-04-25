package dev.graumann.graphsearch.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;

import dev.graumann.graphsearch.model.algorithmen.shortestPath.BreadthFirstSearch;
import dev.graumann.graphsearch.model.fileExtensionSystem.GraphFileExtensionHandler;

public class JUnitTestBFS {
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
	Node step_04_02;
	Node target_01;
	Node target_02;
	Node target_03;
	Node target_04;
	Node target_05;
	List<Node> shortestPathTest1;
	List<Node> shortestPathTest4;
	List<Node> shortestPathTest5;
	BreadthFirstSearch bfs1;
	BreadthFirstSearch bfs2;
	BreadthFirstSearch bfs3;
	BreadthFirstSearch bfs4;
	BreadthFirstSearch bfs5;

	@Before
	public void setUp() throws Exception {
		this.fileHandler = new GraphFileExtensionHandler();
						
		// Test 1 - 9.1.1.1
		this.graph1 = fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/JUnitTest_BFS_directed.graph");
		this.bfs1 = new BreadthFirstSearch();		
		this.shortestPathTest1 = new ArrayList<Node>();	
		source_01 = graph1.getNode("Start");
		step_01_01 = graph1.getNode("Zwischenschritt");
		target_01 = graph1.getNode("Ziel");
		
		shortestPathTest1.add(source_01);
		shortestPathTest1.add(step_01_01);
		shortestPathTest1.add(target_01);
		
		// Test 2 - 9.1.1.2
		this.bfs2 = new BreadthFirstSearch();		
		source_02 = graph1.getNode("Start");
		target_02 = graph1.getNode("Graph_ausserhalb_Knoten_1");
				
		// Test 3 - 9.1.1.3
		this.bfs3 = new BreadthFirstSearch();		
		source_03 = graph1.getNode("Start");
		target_03 = graph1.getNode("unerreichbares_Ziel");
		
		
		// Test 4 - 9.1.2.1
		this.graph2 = fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/JUnitTest_BFS_undirected.graph");
		this.bfs4 = new BreadthFirstSearch();		
		this.shortestPathTest4 = new ArrayList<Node>();	
		source_04 = graph2.getNode("Start");
		step_04_01 = graph2.getNode("Zwischenschritt");
		target_04 = graph2.getNode("Ziel");		
		
		shortestPathTest4.add(source_04);
		shortestPathTest4.add(step_04_01);
		shortestPathTest4.add(target_04);
		
		
		// Test 5 - 9.1.2.2
		this.bfs5 = new BreadthFirstSearch();		
		source_05 = graph2.getNode("Start");
		target_05 = graph2.getNode("Graph_ausserhalb_Knoten_1");		

		
		
	}

	@Test
	public void testBFSUnweightedDirected() {
		// Test 1 - 9.1.1.1
		assertEquals(bfs1.calculate(graph1, source_01.toString(), target_01.toString()), shortestPathTest1);
		
		
		// Test 2 - 9.1.1.2		
		assertEquals(bfs2.calculate(graph1, source_02.toString(), target_02.toString()), new ArrayList<Node>());
		
		// Test 3 - 9.1.1.3

		assertEquals(bfs3.calculate(graph1, source_03.toString(), target_03.toString()), new ArrayList<Node>());
		
		
	}
	
	@Test
	public void testBFSUnweightedUnDirected() {		
		
		// Test 4 - 9.1.2.1		
		assertEquals(bfs4.calculate(graph2, source_04.toString(), target_04.toString()), shortestPathTest4);		
		
		// Test 5 - 9.1.2.2		
		assertEquals(bfs5.calculate(graph2, source_05.toString(), target_05.toString()), new ArrayList<Node>());

	}

}
