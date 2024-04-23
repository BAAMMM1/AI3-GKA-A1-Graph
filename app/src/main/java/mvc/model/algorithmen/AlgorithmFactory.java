package mvc.model.algorithmen;

import mvc.model.algorithmen.shortestPath.Dijkstra;

public class AlgorithmFactory {

	public AlgorithmFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static Algorithm getInstance(String typ){
		
		if(typ.equals("Dijkstra")){
			return new Dijkstra();
		} else {
			
		}
		return null;
		
	}
	
	public static void main(String[] args) {
		Dijkstra algortihm  = (Dijkstra) AlgorithmFactory.getInstance("Dijkstra");
	}

}
