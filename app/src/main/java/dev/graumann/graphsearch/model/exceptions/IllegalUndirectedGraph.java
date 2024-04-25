package dev.graumann.graphsearch.model.exceptions;

public class IllegalUndirectedGraph extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IllegalUndirectedGraph(String string){
		super(string);
	}

}
