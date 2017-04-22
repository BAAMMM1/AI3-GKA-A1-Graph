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
		
		/*
		System.out.println(model.getGraph().getEdge("ab").isDirected());
		System.out.println(model.getGraph().getEdge("ab").getSourceNode().toString());		
		System.out.println(model.getGraph().getEdge("ab").getNode0().toString());
		
		System.out.println(model.getGraph().getEdge("ba").isDirected());
		System.out.println(model.getGraph().getEdge("ba").getSourceNode().toString());		
		System.out.println(model.getGraph().getEdge("ba").getNode0().toString());
		*/

	}

}
