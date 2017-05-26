package mvc.model.algorithmen.eulerCircle;

import org.graphstream.graph.Graph;

import mvc.model.algorithmen.Algorithm;
import utility.Printer;

public abstract class EulerCircle extends Algorithm{
	
	public Graph calculate(Graph graph){
		this.validArguments(graph);
		
		Printer.prompt(this, "-------------------------------------");
		
		Printer.prompt(this, "compute euler circle algorithmus");	
		
		this.initAlgorithmus(graph);
		
		return this.procedure();
	}

	private void validArguments(Graph graph) {
		
	}
	
	private void initAlgorithmus(Graph graph) {
		this.setGraph(graph);
		
	}

	protected abstract Graph procedure();

}
