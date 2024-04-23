package dev.graumann.graphsearch.mvc.model.exceptions;

public class IllegalWeightedGraph extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalWeightedGraph(String string){
		super(string);
	}

}
