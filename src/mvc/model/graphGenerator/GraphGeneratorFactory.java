package mvc.model.graphGenerator;

public class GraphGeneratorFactory {

	public static GraphGenerator getInstance(String typ) {

		if (typ.equals("Simple")) {
			return new RandomSimple();
			
		} else if (typ.equals("Euler")){
			return new RandomEuler();	
			
		} else if (typ.equals("Euler ohne Kringel")){
			return new RandomEulerWithOutKringel();	
				
		} else {
			throw new IllegalArgumentException("generator not found");
			
		}
		
	}

}
