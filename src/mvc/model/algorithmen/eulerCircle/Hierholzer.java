package mvc.model.algorithmen.eulerCircle;

import java.util.LinkedList;

import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import scala.util.Random;
import utility.Printer;

/**
 * Der Algorithmus von Hierholzer findet in einem zusammenhängend, ungerichtet
 * Graphen und bei dem jeder Knoten einen gerade Knotengrad besitzt, einen
 * Eulerkreis.
 * 
 * Vorgang: Der Algorithmus beginnt bei einem zufällig ausgewählten Startknoten.
 * 
 * @author Chris
 *
 */
public class Hierholzer extends EulerCircle {

	private List<List<Node>> zyklenNodes;
	private LinkedList<Edge> edges;
	private LinkedList<List<Edge>> zyklenEdges;
	
	private LinkedList<List<Edge>> zyklenEdgesNew;


	@Override
	protected List<Edge> procedure() {
		this.initialize();

		this.calculateEulerParts();
		

		return this.calculateEulerCircle();
	}

	private void calculateEulerParts() {

		Node randomStart = this.getGraph().getNode(this.getRandomStart(this.getGraph().getNodeSet().size()));
		Printer.prompt(this, "random start: " + randomStart.toString());

		Node aktuellerStart = randomStart;
		Node nextNode = randomStart;

		int aktuelleListe = 0;
		this.zyklenNodes.add(new LinkedList<Node>());

		while (!this.getGraph().getEdgeSet().isEmpty()) {
			
			LinkedList<Edge> currentCircle = new LinkedList<Edge>();
			
			do {
				this.zyklenNodes.get(aktuelleListe).add(nextNode);

				List<Edge> nodeEdges = new LinkedList<Edge>();
				nodeEdges.addAll(nextNode.getEdgeSet());

				Edge nextEdge = nodeEdges.get(this.getRandomStart(nodeEdges.size()));
				currentCircle.add(nextEdge);

				this.getGraph().removeEdge(nextEdge);				

				if (nextEdge.getSourceNode() != nextNode) {
					nextNode = nextEdge.getSourceNode();
				} else {
					nextNode = nextEdge.getTargetNode();
				}

			} while (nextNode != aktuellerStart);
			/*
			 * Edge brauch hier nicht extra hinzugefügt werden
			 */
			this.zyklenNodes.get(aktuelleListe).add(nextNode);
			this.zyklenEdgesNew.add(currentCircle);
	

			// Über alle Knoten in der aktuellen List, finde den mit Grad größer
			// 0
			// nimm ihn als aktuellerStart und nextNode;
			if(!this.getGraph().getEdgeSet().isEmpty()){
				
				for (int i = 0; i < this.zyklenNodes.size(); i++) {
					
					for (int i2 = 0; i2 < this.zyklenNodes.get(i).size(); i2++) {
						
					if (this.zyklenNodes.get(i).get(i2).getEdgeSet().size() > 0) {						
						aktuellerStart = this.zyklenNodes.get(i).get(i2);
						
						nextNode = this.zyklenNodes.get(i).get(i2);					
						break;
					}
					}

				}
				
				aktuelleListe++;
				this.zyklenNodes.add(new LinkedList<Node>());
				
			}
			

			

			/*
			 * for(Edge edge : aktuellerStart.getEdgeSet()){
			 * 
			 * 
			 * }
			 */

		}

	}

	private int getRandomStart(int value) {
		Random random = new Random();
		return random.nextInt(value);
	}

	private void initialize() {
		this.edges = new LinkedList<Edge>();
		this.edges.addAll(this.getGraph().getEdgeSet());
		this.zyklenNodes = new LinkedList<List<Node>>();
		this.zyklenEdges = new LinkedList<List<Edge>>();
		this.zyklenEdgesNew = new LinkedList<List<Edge>>();

	}

	private List<Edge> calculateEulerCircle() {
		

		List<Node> resultNodes = new LinkedList<Node>();
		List<Edge> resultEdges = new LinkedList<Edge>();
		
		List<Edge> resultEdgesNew = new LinkedList<Edge>();

		// TODO Wird diese if-Anweisung gebraucht?
		// NodeZyklen zu EulerCircle
		if (!this.zyklenNodes.isEmpty()) {
			resultNodes.addAll(this.zyklenNodes.get(0));
			resultEdgesNew.addAll(this.zyklenEdgesNew.get(0));
			
			for (int i = 1; i < this.zyklenNodes.size(); i++) {

				int index = resultNodes.indexOf(this.zyklenNodes.get(i).get(0));
				
				resultNodes.remove(index);				
				
				resultNodes.addAll(index, zyklenNodes.get(i));
				
				resultEdgesNew.addAll(index, this.zyklenEdgesNew.get(i));

			}

		}
		


		/*
		 * Dem Graphen alle Kanten wieder hinzufügen
		 */
		for(int i = 0; i<this.edges.size(); i++){
			Node source = edges.get(i).getSourceNode();
			Node target = edges.get(i).getTargetNode();
			String name = edges.get(i).getId();
			
			this.getGraph().addEdge(name, source, target);
			
			if(edges.get(i).getAttribute("weight") != null){
				this.getGraph().getEdge(name).setAttribute("weight", (int)edges.get(i).getAttribute("weight"));
			}
			if(edges.get(i).getAttribute("ui.label") != null){
				this.getGraph().getEdge(name).setAttribute("ui.label", edges.get(i).getAttribute("ui.label").toString());
			}
			
		}
		
		
		
		for(int i = 0; i < this.zyklenNodes.size(); i++){
			this.zyklenEdges.add(new LinkedList<Edge>());
			
			for(int i2 = 0; i2 < this.zyklenNodes.get(i).size()-1; i2++){
				Node node1 = zyklenNodes.get(i).get(i2);
				Node node2 = zyklenNodes.get(i).get(i2+1);
				String name1 = node1.toString() + node2.toString();
				String name2 = node2.toString() + node1.toString();	
				
				if(this.getGraph().getEdge(name1) != null){
					this.zyklenEdges.get(i).add(this.getGraph().getEdge(name1));
				} else {
					this.zyklenEdges.get(i).add(this.getGraph().getEdge(name2));
				}
				
				
			}
			
			
			
		}
		
		
		
		for(int i = 0; i < resultNodes.size()-1; i++){
			Node node1 = resultNodes.get(i);
			Node node2 = resultNodes.get(i+1);	
			String name1 = node1.toString() + node2.toString();
			String name2 = node2.toString() + node1.toString();
			
			if(this.getGraph().getEdge(name1) != null){
				resultEdges.add(this.getGraph().getEdge(name1));
			} else {
				resultEdges.add(this.getGraph().getEdge(name2));
			}
					
			
			
			
			
		}
		
		Printer.prompt(this, "Node-Zyklen: \t" + this.zyklenNodes.toString());
		Printer.prompt(this, "Node-Eulerkreis: \t" + resultNodes.toString());
		Printer.prompt(this, "Edge-Zyklen: \t" + this.zyklenEdges.toString());
		Printer.prompt(this, "Edge-Eulerkreis: \t" + resultEdges.toString());
		
		Printer.prompt(this, "Edge-New: \t" + this.zyklenEdgesNew.toString());
		Printer.prompt(this, "Edge-Eulerkreis: \t" + resultEdgesNew.toString());

		return resultEdgesNew;

	}
	
	public List<List<Edge>> getEulerParts(){
		return this.zyklenEdgesNew;
	}

	@Override
	public String toString() {
		return String.format("Hierholzer");
	}
	
	

}
