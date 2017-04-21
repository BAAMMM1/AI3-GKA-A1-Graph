package main;

import org.graphstream.graph.Graph;

import model.algorithmusSystem.Dijkstra.Dijkstra;
import model.algorithmusSystem.breadthFirstSearch.BreadthFirstSearch;
import model.algorithmusSystem.breadthFirstSearch.BreadthFirstSearch2;
import model.fileExtensionSystem.FileExtension;
import model.fileExtensionSystem.GraphFileExtensionHandler;

public class Main {

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		FileExtension fileHandler = new GraphFileExtensionHandler();
		
		Graph graph = fileHandler.loadGraph("db/examples/dijkstra.graph");
		
		//test comment
		//graph.display();
		
		/*
		BreadthFirstSearch2 bfs2 = new BreadthFirstSearch2(graph);
		bfs2.stpAlgorithmus(graph.getNode("s"), graph.getNode("t"));
		 */
		
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
		/*
		BreadthFirstSearch bfsAlgo = new BreadthFirstSearch(graph);
		
		bfsAlgo.shortestPath("s", "t");
		*/
	
		Dijkstra djk = new Dijkstra(graph);

		
		graph = djk.runStart(graph.getNode("v4"), graph.getNode("v1"));
			
	
				
		
		//graph.display().disableAutoLayout();
		//fileHandler.saveGraph(graph, "filename");
		

	}

}
