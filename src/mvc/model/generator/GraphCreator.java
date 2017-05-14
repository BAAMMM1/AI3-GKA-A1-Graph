package mvc.model.generator;

import org.graphstream.graph.Graph;

import mvc.model.algorithmen.minimalSpanningTree.Kruskal;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class GraphCreator {
	
	public static void main(String[] args) {
		GraphGenerator generator = new GraphGenerator();
		GraphFileExtensionHandler fileHandler = new GraphFileExtensionHandler();
		Graph graph;
		Kruskal kruskal = new Kruskal();
		
		boolean run = true;
		int nodeSize = 450;
		int edgeSize = (nodeSize*(nodeSize-1))/2;
		int maxWeight = 100;
		
		while(run){
			System.out.println("NodeSize: " + nodeSize + " EdgeSize: " + edgeSize + " Maximal Weight: " + maxWeight);
			graph = generator.generateRandomSimpleGraph(nodeSize, edgeSize, maxWeight);
			
			kruskal.calculate(graph);
			fileHandler.saveGraph(graph, "db/random/random_" + nodeSize + "_" + edgeSize + "_" + maxWeight + "_time_" + kruskal.getRunTimeInSek());
			if(kruskal.getRunTimeInSek() < 600){
				nodeSize += 50;
				edgeSize = (nodeSize*(nodeSize-1))/2;
			} else {
				run = false;
			}
			
		}
		
		
		

	}

}
