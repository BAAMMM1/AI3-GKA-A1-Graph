package mvc.model.fileExtensionSystem;

import mvc.model.fileExtensionSystem.Convertion.GraphFileConverter;

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
