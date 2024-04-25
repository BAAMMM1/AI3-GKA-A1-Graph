package dev.graumann.graphsearch.model.fileExtensionSystem;

import dev.graumann.graphsearch.model.fileExtensionSystem.Convertion.GraphFileConverter;

public class GraphFileExtensionHandler extends FileExtensionHandler{

	private String EXTENSION = ".graph";
	
	public GraphFileExtensionHandler() {
		super(new GraphFileConverter());
	}

	@Override
	public String getExtension() {
		return EXTENSION;
	}


}
