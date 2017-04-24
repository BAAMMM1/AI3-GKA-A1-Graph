package main;

import mvc.controller.Controller;
import mvc.model.Model;
import mvc.view.View;

public class Main {

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);
		

	}

}
