package mvc.model.fileExtensionSystem.Convertion;

import java.util.List;

import org.graphstream.graph.Graph;

public abstract class FileConverter {
	
	public abstract Graph fileToGraphObject(List<String> string);
	public abstract List<String> graphObjectToString(Graph graph);


}
