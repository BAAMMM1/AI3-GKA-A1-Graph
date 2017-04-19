package model.fileExtensionSystem;

import org.graphstream.graph.Graph;

import model.fileExtensionSystem.Convertion.FileConverter;
import model.fileExtensionSystem.IOControll.input.FileWriter;
import model.fileExtensionSystem.IOControll.output.FileReader;

public abstract class FileExtensionHandler implements FileExtension{
	
	private FileConverter converter;
	private FileReader reader;
	private FileWriter writer;
	
	public FileExtensionHandler(FileConverter converter){
		this.converter = converter;
		this.reader = new FileReader();
		this.writer = new FileWriter();
		
	}
	
	//TODO
	// Template oder Hook-Methode
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
		//TODO
		// Template oder Hook-Methode
		this.getWriter().writeToFile(this.getConverter().graphObjectToString(graph), filename, this.getExtension());
	}

	@Override
	public final Graph loadGraph(String path) {
		return this.getConverter().fileToGraphObject(this.getReader().readFromFile(path));
	}
	
	
	

}
