package mvc.model.fileExtensionSystem;

import java.util.List;

import org.graphstream.graph.Graph;

import mvc.model.fileExtensionSystem.Convertion.FileConverter;
import mvc.model.fileExtensionSystem.IOControll.input.FileWriter;
import mvc.model.fileExtensionSystem.IOControll.output.FileReader;

public abstract class FileExtensionHandler implements FileExtension{
	
	private FileConverter converter;
	private FileReader reader;
	private FileWriter writer;
	
	public FileExtensionHandler(FileConverter converter){
		this.converter = converter;
		this.reader = new FileReader();
		this.writer = new FileWriter();
		
	}
	
	public abstract String getExtension();

	private FileConverter getConverter() {
		return converter;
	}

	private FileReader getReader() {
		return reader;
	}

	private FileWriter getWriter() {
		return writer;
	}
	
	@Override
	public final void saveGraph(Graph graph, String filename) {
		
		this.getWriter().writeToFile(this.getConverter().graphObjectToString(graph), filename, this.getExtension());
	}

	@Override
	public final Graph loadGraph(String path) {
		return this.getConverter().fileToGraphObject(this.getReader().readFromFile(path));
	}
	
	@Override
	public final List<String> loadFile(String path) {
		return this.getReader().readFromFile(path);
	}
	
	@Override
	public final Graph loadGraphFromList(List<String> list) {
		return this.getConverter().fileToGraphObject(list);
	}
	
	
	
	

}
