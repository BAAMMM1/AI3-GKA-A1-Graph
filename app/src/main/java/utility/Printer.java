package utility;

public class Printer {
	
	private static final boolean testMode = false;

	public static void promptTestOut(Object obj, String string){
		if(testMode){
			System.out.println(obj.toString() + ": " + string);
		}		
	}
	
	public static void promptErrTestOut(Object obj, String string){
		if(testMode){
			System.err.println(obj.toString() + ": " + string);
		}		
	}	
	
	public static void prompt(Object obj, String string){
		System.out.println(obj.toString() + ": " + string);
	}
	
	public static void errPrompt(Object obj, String string){
		System.err.println(obj.toString() + ": " + string);
	}
}
