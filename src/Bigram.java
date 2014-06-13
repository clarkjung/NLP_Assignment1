import java.util.HashMap;


public class Bigram {
	
	private HashMap<String, Integer> bigramHashMap;
	
	//constructor
	public Bigram(){
		bigramHashMap = new HashMap<String, Integer>();
	}

	public void add(String previousString, String currentString){
		
		String combineString = previousString + " " + currentString;
		
		if(hasString(combineString)){
			
			int tempInt = bigramHashMap.get(combineString);
			tempInt++;
			bigramHashMap.put(combineString, tempInt);
			
		}else{
			bigramHashMap.put(combineString, 1);
		}
	}
	
	private boolean hasString(String string){
		return bigramHashMap.containsKey(string);
	}
	
	public String printBigramHashMap(){
		return bigramHashMap.toString();
	}
	
	public int getValue(String string){
		
		if(hasString(string)){
			return bigramHashMap.get(string);
		}else{
			return 0;
		}
	}
	
	public int getBigramSize(){
		return bigramHashMap.size();
	}
}
