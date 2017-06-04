package main;

import mvc.controller.Controller;
import mvc.controller.Controller;
import mvc.model.Model;
import mvc.view.View;
import mvc.view.View;

public class Main {

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		/*
		 * TODO Random create in einem neuem Thread?
		 */
		//Test
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);
	
		

	}

}
