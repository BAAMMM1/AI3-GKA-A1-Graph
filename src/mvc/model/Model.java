package mvc.model;

import java.util.List;

import org.graphstream.graph.Graph;

import mvc.model.algorithmen.minimalSpanningTree.Kruskal;
import mvc.model.algorithmen.minimalSpanningTree.Prim;
import mvc.model.algorithmen.shortestPath.BreadthFirstSearch;
import mvc.model.algorithmen.shortestPath.Dijkstra;
import mvc.model.fileExtensionSystem.FileExtension;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class Model {
	
	private GraphFileExtensionHandler fileHandler;
	private Graph graph;
	private List<String> graphAsText;
	private BreadthFirstSearch bfs;
	private Dijkstra dijksta;
	private Kruskal kruskal;
	private Prim prim;
	
	
	public Model(){
		this.initializeModel();		
		
	}
	
	public void initializeModel(){
		this.fileHandler = new GraphFileExtensionHandler();
		this.graph = fileHandler.loadGraph("db/kruskal/circle01.graph");
		this.graphAsText = fileHandler.loadFile("db/kruskal/circle01.graph");
		this.bfs = new BreadthFirstSearch();
		this.dijksta = new Dijkstra();
		this.kruskal = new Kruskal();
		this.prim = new Prim();
	}

	public FileExtension getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(GraphFileExtensionHandler fileHandler) {
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

	public Kruskal getKruskal() {
		return kruskal;
	}

	public Prim getPrim() {
		return prim;
	}	
	

}
