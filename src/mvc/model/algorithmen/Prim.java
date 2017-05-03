package mvc.model.algorithmen;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Prim extends Algorithmus {
	
	private Graph graph;
	private Node source;
	private Node target;

	public Prim(Graph graph) {
		this.graph = graph;
	}

	@Override
	protected Graph procedure(Node source, Node target) {
		this.initializePrim(source, target);
		
		while(this.graphTHasNotAllNodes()){			
			Edge e = this.getEedgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT();
			
			this.addEdgeAndNodeToT(e, e.getTargetNode());
			
		}
		
		return this.graph;
	}

	private void initializePrim(Node source, Node target){
		this.source = source;
		this.target = target;
		
	}
	
	private boolean graphTHasNotAllNodes(){
		return true;
	}
	
	private Edge getEedgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void addEdgeAndNodeToT(Edge edge, Node node){
		
	}
	
	

}
