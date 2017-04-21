package mvc.model;

import java.util.List;

import org.graphstream.graph.Graph;

import mvc.model.algorithmusSystem.Dijkstra.Dijkstra;
import mvc.model.algorithmusSystem.breadthFirstSearch.BreadthFirstSearch;
import mvc.model.fileExtensionSystem.FileExtension;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class Model {
	
	private FileExtension fileHandler;
	private Graph graph;
	private List<String> graphAsText;
	private BreadthFirstSearch bfs;
	private Dijkstra dijksta;
	
	
	public Model(){
		this.initializeModel();		
		
	}
	
	public void initializeModel(){
		this.fileHandler = new GraphFileExtensionHandler();
		this.graph = fileHandler.loadGraph("db/examples/dijkstra.graph");
		this.graphAsText = fileHandler.loadFile("db/examples/dijkstra.graph");
	}

	public FileExtension getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(FileExtension fileHandler) {
		this.fileHandler = fileHandler;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public BreadthFirstSearch getBfs() {
		return bfs;
	}

	public void setBfs(BreadthFirstSearch bfs) {
		this.bfs = bfs;
	}

	public Dijkstra getDijksta() {
		return dijksta;
	}

	public void setDijksta(Dijkstra dijksta) {
		this.dijksta = dijksta;
	}

	public List<String> getGraphAsText() {
		return graphAsText;
	}

	public void setGraphAsText(List<String> graphAsText) {
		this.graphAsText = graphAsText;
	}
	
	

}
