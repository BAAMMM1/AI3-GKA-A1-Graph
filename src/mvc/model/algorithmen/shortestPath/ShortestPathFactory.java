package mvc.model.algorithmen.shortestPath;

public class ShortestPathFactory {

	public static ShortestPath getInstance(String typ) {

		if (typ.equals("Dijkstra")) {
			return new Dijkstra();
			
		} else if (typ.equals("BreadthFirstSearch")){
			return new BreadthFirstSearch();	
			
		} else {
			throw new IllegalArgumentException("algortihmus not found");
			
		}
		
	}

}
