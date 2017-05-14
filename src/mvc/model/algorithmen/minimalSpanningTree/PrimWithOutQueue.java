package mvc.model.algorithmen.minimalSpanningTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import utility.Printer;

public class PrimWithOutQueue extends MinimalSpanningTree {

	private Graph minimalSpanningTree;
	private List<Edge> resultF;
	private List<Node> allreadyIn;

	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();
		this.initPrim();		
		
		while (this.graphTHasNotAllNodes()) {
			Edge e = this.getEdgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT();

			this.addEdgeAndNodeToT(e);

		}
		
		long timeEnd = System.currentTimeMillis();
		Printer.prompt(this, "time needed: " + (timeEnd - timeStart));
		System.out.println(this.getEdgeWeightes());
		
		return minimalSpanningTree;

	}

	private void initPrim() {	
		this.minimalSpanningTree = new MultiGraph("Kruskal");
		this.resultF = new LinkedList<Edge>();
		this.allreadyIn = new LinkedList<Node>();
		
		this.addNodeToResult(this.getRandomStartNode());
	
	}
	
	private void addNodeToResult(Node node){
		this.allreadyIn.add(node);
		this.minimalSpanningTree.addNode(node.getId());
		this.minimalSpanningTree.getNode(node.getId()).setAttribute("ui.label", node.getAttribute("ui.label").toString());

	}

	private boolean graphTHasNotAllNodes() {
		return !(this.minimalSpanningTree.getNodeSet().size() == this.getGraph().getNodeSet().size());
	}

	private Edge getEdgeWithMinimalWeightWichlinkToANodeWhoIsNotInGraphT() {
		List<Edge> sortedEdges = new LinkedList<Edge>();
		
		for(Node node: this.minimalSpanningTree.getNodeSet()){
			node = this.getGraph().getNode(node.getId());
			
			System.out.println(node.toString());
			
			Iterator<Edge> it = node.getEdgeIterator();
			Edge edge;
			while(it.hasNext()){
				edge = it.next();
				System.out.println(edge.toString());
				if(!((allreadyIn.contains(edge.getSourceNode())) && (allreadyIn.contains(edge.getTargetNode())))){
					sortedEdges.add(edge);
				}
			}
					
		}
		
		sortedEdges.sort((e1, e2) -> ((Integer) e1.getAttribute("weight")).compareTo(e2.getAttribute("weight")));

		return sortedEdges.get(0);
	}

	private void addEdgeAndNodeToT(Edge edge) {
		
		Node source = edge.getSourceNode();
		Node target = edge.getTargetNode();
		
		if(!this.allreadyIn.contains(source)){
			this.addNodeToResult(source);
		} else {
			this.addNodeToResult(target);
		}		
		this.resultF.add(edge);
		this.minimalSpanningTree.addEdge(edge.getId().toString(), source, target);
		this.minimalSpanningTree.getEdge(edge.getId().toString()).addAttribute("weight", edge.getAttribute("weight").toString());
		this.minimalSpanningTree.getEdge(edge.getId().toString()).addAttribute("ui.label", edge.getAttribute("ui.label").toString());

	}
	
	private Node getRandomStartNode(){
		Random random = new Random();				
		return this.getGraph().getNode(random.nextInt(this.getGraph().getNodeSet().size()));
	}

	@Override
	public String toString() {
		return "Prima";
	}
	
	public int getEdgeWeightes(){
		return this.resultF.stream().map(e1 -> (Integer)e1.getAttribute("weight")).reduce(0, (e1,e2) -> e1.intValue()+e2.intValue());
		
	}

}
