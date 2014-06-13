import java.util.HashMap;


public class Unigram
{
	private HashMap<String, Integer> unigramHashMap;
	
	//constructor
	public Unigram(){
		unigramHashMap = new HashMap<String, Integer>();
	}
	
	public void add(String string){
		
		if(hasString(string)){
			
			int tempInt = unigramHashMap.get(string);
			tempInt++;
			unigramHashMap.put(string, tempInt);
			
		}else{
			unigramHashMap.put(string, 1);
		}
	}
	
	private boolean hasString(String string){
		return unigramHashMap.containsKey(string);
	}
	
	public String printUnigramHashMap(){
		return unigramHashMap.toString();
	}
	
	public int getValue(String string){
		
		if(hasString(string)){
			return unigramHashMap.get(string);
		}else{
			return 0;
		}
		
		
	}
	
	public int getUnigramSize(){
		return unigramHashMap.size();
	}
	
}