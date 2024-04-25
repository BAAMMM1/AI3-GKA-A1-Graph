package dev.graumann.graphsearch;

import dev.graumann.graphsearch.controller.Controller;
import dev.graumann.graphsearch.model.Model;
import dev.graumann.graphsearch.view.View;

public class Main {

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		Model model = new Model();
		View view = new View();
		new Controller(model, view);
		
	}

}
