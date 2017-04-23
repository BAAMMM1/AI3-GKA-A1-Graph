package test.algorithms;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;

import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class JUnitTestBFS {
	GraphFileExtensionHandler fileHandler;
	Graph graph;
	Node node1;
	Node node2;
	Node node3;
	Node node4;
	List<Node> shortestPathTest1;

	@Before
	public void setUp() throws Exception {
		this.fileHandler = new GraphFileExtensionHandler();
		this.graph = fileHandler.loadGraph("db/examples/simple.graph");
		this.shortestPathTest1 = new ArrayList<Node>();	
		
		node1 = graph.getNode("a");
		node2 = graph.getNode("a");
		node3 = graph.getNode("a");
		node4 = graph.getNode("a");
		shortestPathTest1.addAll(new ArrayList<Node>());
		
		
		
	}

	@Test
	public void test() {
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
