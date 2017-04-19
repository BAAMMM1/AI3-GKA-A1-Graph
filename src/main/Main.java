package main;

import org.graphstream.graph.Graph;

import model.fileExtensionSystem.FileExtension;
import model.fileExtensionSystem.GraphFileExtensionHandler;

public class Main {

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		FileExtension fileHandler = new GraphFileExtensionHandler();
		
		Graph graph = fileHandler.loadGraph("db/examples/graph01.graph");
		
		//graph.display();
		//graph.display().disableAutoLayout()
		// Next comments
		fileHandler.saveGraph(graph, "filename");
		

	}

}
