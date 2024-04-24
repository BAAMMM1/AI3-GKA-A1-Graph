package dev.graumann.graphsearch.mvc.model;

import java.util.List;

import org.graphstream.graph.Graph;

import dev.graumann.graphsearch.mvc.model.algorithmen.eulerTour.EulerTour;
import dev.graumann.graphsearch.mvc.model.algorithmen.eulerTour.EulerTourFactory;
import dev.graumann.graphsearch.mvc.model.algorithmen.minimalSpanningTree.MinimalSpanningTree;
import dev.graumann.graphsearch.mvc.model.algorithmen.minimalSpanningTree.MinimalSpanningTreeFactory;
import dev.graumann.graphsearch.mvc.model.algorithmen.shortestPath.ShortestPath;
import dev.graumann.graphsearch.mvc.model.algorithmen.shortestPath.ShortestPathFactory;
import dev.graumann.graphsearch.mvc.model.fileExtensionSystem.FileExtension;
import dev.graumann.graphsearch.mvc.model.fileExtensionSystem.GraphFileExtensionHandler;
import dev.graumann.graphsearch.mvc.model.graphGenerator.GraphGenerator;
import dev.graumann.graphsearch.mvc.model.graphGenerator.GraphGeneratorFactory;
import dev.graumann.graphsearch.mvc.model.utility.GraphLabeler;
import dev.graumann.graphsearch.mvc.model.utility.GraphModifer;

public class Model {

	private GraphFileExtensionHandler fileHandler;
	private Graph graph;
	private List<String> graphAsText;
	private GraphModifer modifier;
	private GraphLabeler labeler;

	public Model() {
		this.initializeModel();

	}

	public void initializeModel() {
		this.fileHandler = new GraphFileExtensionHandler();

		this.graph = fileHandler.loadGraphFromResources("/dev/graumann/graphsearch/graph/default.graph");
		this.graphAsText = fileHandler.loadFileFromResources("/dev/graumann/graphsearch/graph/default.graph");
		this.modifier = new GraphModifer();
		this.labeler = new GraphLabeler();
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

	public List<String> getGraphAsText() {
		return graphAsText;
	}

	public void setGraphAsText(List<String> graphAsText) {
		this.graphAsText = graphAsText;
	}

	public GraphModifer getModifier() {
		return modifier;
	}

	public GraphLabeler getLabeler() {
		return labeler;
	}

	public ShortestPath getShortestPath(String string) {
		return ShortestPathFactory.getInstance(string);
	}

	public MinimalSpanningTree getMinimalSpanningTree(String string) {
		return MinimalSpanningTreeFactory.getInstance(string);
	}

	public EulerTour getEulerCircle(String string) {
		return EulerTourFactory.getInstance(string);
	}

	public GraphGenerator getGraphGenerator(String string) {
		return GraphGeneratorFactory.getInstance(string);
	}

}
