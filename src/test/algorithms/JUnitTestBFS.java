package test.algorithms;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;

import mvc.model.algorithmusSystem.breadthFirstSearch.BreadthFirstSearch;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class JUnitTestBFS {
	GraphFileExtensionHandler fileHandler;
	Graph graph1;
	Node source_01;
	Node step_01_01;
	Node step_01_02;
	Node target_01;
	List<Node> shortestPathTest1;
	List<Node> shortestPathTest2;
	BreadthFirstSearch bfs1;

	@Before
	public void setUp() throws Exception {
		this.fileHandler = new GraphFileExtensionHandler();
		
		this.graph1 = fileHandler.loadGraph("db/testCases/JUnitTest_BFS_directed.graph");
		this.bfs1 = new BreadthFirstSearch(graph1);
		
		this.shortestPathTest1 = new ArrayList<Node>();	
		
		source_01 = graph1.getNode("Start");
		step_01_01 = graph1.getNode("Zwischenschritt");
		target_01 = graph1.getNode("Ziel");
		
		shortestPathTest1.add(source_01);
		shortestPathTest1.add(step_01_01);
		shortestPathTest1.add(target_01);
		
	
	
		
		
		
	}

	@Test
	public void testBFSdirected1() {
		bfs1.start(source_01, target_01);
		assertEquals(bfs1.getShortestPath(), shortestPathTest1);
		

		
		/*
		assertEquals(expected, actual);
		assertNotEquals(unexpected, actual);
		assertTrue(condition);
		assertFalse(condition);
		assertNotNull(object);
		assertSame(expected, actual);
		assertNotSame(unexpected, actual);
		assertNotNull(object);
		*/
	}

}
