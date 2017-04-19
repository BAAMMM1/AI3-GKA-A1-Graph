package model.fileExtensionSystem;

import org.graphstream.graph.Graph;

public interface FileExtension {
	
	public void saveGraph(Graph graph, String path);
	public Graph loadGraph(String path);	

}
