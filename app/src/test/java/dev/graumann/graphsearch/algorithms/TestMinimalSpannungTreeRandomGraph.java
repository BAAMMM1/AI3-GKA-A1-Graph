package dev.graumann.graphsearch.algorithms;

import org.graphstream.graph.Graph;

import dev.graumann.graphsearch.model.algorithmen.minimalSpanningTree.Kruskal;
import dev.graumann.graphsearch.model.algorithmen.minimalSpanningTree.Prim;
import dev.graumann.graphsearch.model.fileExtensionSystem.GraphFileExtensionHandler;

public class TestMinimalSpannungTreeRandomGraph {
	
	public static void main(String[] args) {

			GraphFileExtensionHandler fileHandler = new GraphFileExtensionHandler();
			Graph graph;
			Kruskal kruskal = new Kruskal();
			Prim prim = new Prim();
			
			graph = fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/random/random_450_50512_100.graph");
			System.out.println("Start");
			prim.calculate(graph);
			kruskal.calculate(graph);
			
			graph = fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/random/random_450_101025_100.graph");
			System.out.println("Start");
			prim.calculate(graph);
			kruskal.calculate(graph);
			
			
			graph = fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/random/random_750_280875_100.graph");
			System.out.println("Start");
			prim.calculate(graph);
			kruskal.calculate(graph);
			
			graph = fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/testcases/random/random_850_360825_100.graph");
			System.out.println("Start");
			prim.calculate(graph);
			kruskal.calculate(graph);
		
		

	}

}
