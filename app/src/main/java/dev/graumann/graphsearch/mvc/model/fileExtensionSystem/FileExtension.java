package dev.graumann.graphsearch.mvc.model.fileExtensionSystem;

import java.util.List;

import org.graphstream.graph.Graph;

public interface FileExtension {
	
	
	public void saveGraph(Graph graph, String path);
	public List<String> loadFile(String path);	
	public Graph loadGraph(String path);
	public Graph loadGraphFromList(List<String> list);	

}
