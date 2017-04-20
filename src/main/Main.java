package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Graph;

import model.algorithmusSystem.Algorithmus;
import model.algorithmusSystem.breadthFirstSearch.BreadthFirstSearch;
import model.fileExtensionSystem.FileExtension;
import model.fileExtensionSystem.GraphFileExtensionHandler;

public class Main {

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		FileExtension fileHandler = new GraphFileExtensionHandler();
		
		Graph graph = fileHandler.loadGraph("db/examples/graph01.graph");
		
		//test comment
		graph.display();
		
		
		/*

			Iterator<Node> it = graph.getNode("a").getNeighborNodeIterator();
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			System.out.println(it.next().toString());
			*/
		/*
		Map<String,List<String>> bfsTabelle = new HashMap<String, List<String>>();
		
		bfsTabelle.put("0", new ArrayList<String>());
		bfsTabelle.put("1", new ArrayList<String>());
		bfsTabelle.put("2", new ArrayList<String>());
		bfsTabelle.put("3", new ArrayList<String>());
		
		bfsTabelle.get("0").add("a");
		bfsTabelle.get("0").add("b");
		bfsTabelle.get("0").add("c");		
		bfsTabelle.get("1").add("d");
		*/
		
		/*
		System.out.println(bfsTabelle.get("0"));
		System.out.println(bfsTabelle.get("1"));
		*/
		
		BreadthFirstSearch bfsAlgo = new BreadthFirstSearch(graph);
		
		bfsAlgo.shortestPath("s", "t");
	
	
			
	
				
		
		//graph.display().disableAutoLayout();
		//fileHandler.saveGraph(graph, "filename");
		

	}

}
