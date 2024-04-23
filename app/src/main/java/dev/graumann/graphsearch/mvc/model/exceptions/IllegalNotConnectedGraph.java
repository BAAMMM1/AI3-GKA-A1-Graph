package dev.graumann.graphsearch.mvc.model.exceptions;

public class IllegalNotConnectedGraph extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalNotConnectedGraph(String string){
		super(string);
	}

}
