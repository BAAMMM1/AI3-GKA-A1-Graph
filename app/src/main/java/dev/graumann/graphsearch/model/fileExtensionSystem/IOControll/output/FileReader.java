package dev.graumann.graphsearch.model.fileExtensionSystem.IOControll.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

		while (scanner.hasNext()) {
			String nextLine = scanner.nextLine();
			temp.add(nextLine);
		}

		scanner.close();

		return temp;
	}

	public List<String> readFromResources(String path) {
		InputStream inputStream = FileReader.class.getResourceAsStream(path);

		List<String> temp = new ArrayList<String>();

		Scanner scanner = null;

		scanner = new Scanner(inputStream, "iso-8859-1");

		while (scanner.hasNext()) {
			String nextLine = scanner.nextLine();
			temp.add(nextLine);
		}

		scanner.close();

		return temp;
	}

}
