package dev.graumann.graphsearch.model.fileExtensionSystem;

import java.util.List;

import org.graphstream.graph.Graph;

public interface FileExtension {
	
	
	public void saveGraph(Graph graph, String path);
	public List<String> loadFileFromFile(String path);	
	public Graph loadGraphFromFile(String path);

	public List<String> loadFileFromResources(String path);	
	public Graph loadGraphFromResources(String path);

	public Graph loadGraphFromList(List<String> list);
}
