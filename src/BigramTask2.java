import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class BigramTask2 {

private HashMap<String, HashMap<String, Integer> > bigramHashMap;
	
	public BigramTask2(){
		bigramHashMap = new HashMap<String, HashMap<String, Integer> >();
	}
	
	public void addTag(String tag, String previousTag){
		if(hasTag(tag)){
			addPreviousTag(tag, previousTag);
		}else{
			HashMap<String, Integer> hashMapToAdd = new HashMap<String, Integer>();
			hashMapToAdd.put(previousTag, 1);
			bigramHashMap.put(tag, hashMapToAdd);
		}
	}
	
	private boolean hasTag(String tag){
		return bigramHashMap.containsKey(tag);
	}
	
	private boolean hasPreviousTag(String tag, String previousTag){
		return bigramHashMap.get(tag).containsKey(previousTag);
	}
	
	private void addPreviousTag(String tag, String previousTag){
		if(hasPreviousTag(tag, previousTag)){
			int tempInt = bigramHashMap.get(tag).get(previousTag);
			tempInt++;
			bigramHashMap.get(tag).put(previousTag, tempInt);
		}else{
			bigramHashMap.get(tag).put(previousTag, 1);
		}
	}
	
	public void printBigramHashMap(){
		for (String key : bigramHashMap.keySet()) {
		    System.out.println(key);
		    System.out.println(bigramHashMap.get(key).toString());
		}
	}
	
	public String printBigramHashMap2(){
		return bigramHashMap.toString();
	}
	
	public int getBigramSize(){
		return bigramHashMap.size();
	}
	
	public int getBigramTagSize(String tag){
		
		if(bigramHashMap.containsKey(tag)){
			int sum = 0;
			Iterator<String> keySetIterator = bigramHashMap.get(tag).keySet().iterator();
			while(keySetIterator.hasNext()){
				String key = keySetIterator.next();
				sum += bigramHashMap.get(tag).get(key);
			}
			return sum;
		}else{
			return 0;
		}
		
	}
	
	public int getBigramTagPreviousTagValue(String tag, String previousTag){
		if(bigramHashMap.containsKey(tag)){
			if(bigramHashMap.get(tag).containsKey(previousTag)) return bigramHashMap.get(tag).get(previousTag);
			else return 0;
		}
		else return 0;
	}
	
	public Set<String> getTagSet(){
		return bigramHashMap.keySet();
	}
	
	
}
