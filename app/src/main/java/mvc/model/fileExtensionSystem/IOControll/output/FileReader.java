package mvc.model.fileExtensionSystem.IOControll.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
	
	
	public List<String> readFromFile(String path) {
		List<String> temp = new ArrayList<String>();
		
		File file = new File(path);
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(file, "iso-8859-1");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println(scanner.hasNext());
		while(scanner.hasNext()){
			String nextLine = scanner.nextLine();
			temp.add(nextLine);
			//System.out.println(nextLine);
		}
		
		scanner.close();
		
		
		return temp;
	}
	

}
