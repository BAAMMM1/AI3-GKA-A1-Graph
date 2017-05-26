package mvc.model.algorithmen.eulerCircle;

public class EulerCircleFactory {

	public static EulerCircle getInstance(String typ) {

		if (typ.equals("Hierholzer")) {
			return new Hierholzer();
			
		} else if (typ.equals("Fleury")){
			return new Fleury();	
			
		} else {
			throw new IllegalArgumentException("algortihmus not found");
			
		}
		
	}

}
