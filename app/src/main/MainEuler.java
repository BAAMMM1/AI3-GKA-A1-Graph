package main;

import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class MainEuler {

	public static void main(String[] args) {
		Graph graph = new SingleGraph("random euclidean");
	    Generator gen = new RandomEuclideanGenerator();
	    gen.addSink(graph);
	    gen.begin();
	    for(int i=0; i<1000; i++) {
	            gen.nextEvents();
	    }
	    gen.end();
	    graph.display(false);
	}
}
