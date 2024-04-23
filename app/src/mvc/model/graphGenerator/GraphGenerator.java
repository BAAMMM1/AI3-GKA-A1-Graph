package mvc.model.graphGenerator;

import org.graphstream.graph.Graph;

import utility.Printer;

public abstract class GraphGenerator {

	public Graph generate(int nodeSize, int edgeSize, int maxWeight) {
		this.validArguments(nodeSize, edgeSize, maxWeight);

		Printer.prompt(this, "-------------------------------------");

		Printer.prompt(this, "generate graph");

		return this.procedure(nodeSize, edgeSize, maxWeight);
	}

	private void validArguments(int nodeSize, int edgeSize, int maxWeight) {
		if(nodeSize <= 0){
			throw new IllegalArgumentException("nodeSize uncorrect");
			
		} else if(edgeSize <= 0){
			throw new IllegalArgumentException("edgeSize uncorrect");
			
		}
	}

	protected abstract Graph procedure(int nodeSize, int edgeSize, int maxWeight);

}
