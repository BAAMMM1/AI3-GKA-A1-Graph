package mvc.model.fileExtensionSystem.IOControll.input;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {
	
	public void writeToFile(List<String> graph, String filename, String extension){
		
		
		Path file = Paths.get(filename + extension);
		
		try {
			Files.write(file, graph, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	};

}
