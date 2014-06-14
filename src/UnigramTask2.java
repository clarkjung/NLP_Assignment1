import java.util.HashMap;
import java.util.Iterator;


public class UnigramTask2 {

	private HashMap<String, HashMap<String, Integer> > unigramHashMap;
	private HashMap<String, Integer> unigramSizeHashMap;
	
	public UnigramTask2(){
		unigramHashMap = new HashMap<String, HashMap<String, Integer> >();
		unigramSizeHashMap = new HashMap<String, Integer>();
	}
	
	public void addTag(String tag, String text){
		if(hasTag(tag)){
			addString(tag, text);
		}else{
			HashMap<String, Integer> hashMapToAdd = new HashMap<String, Integer>();
			hashMapToAdd.put(text, 1);
			unigramHashMap.put(tag, hashMapToAdd);
		}
	}
	
	private boolean hasTag(String tag){
		return unigramHashMap.containsKey(tag);
	}
	
	private boolean hasString(String tag, String text){
		return unigramHashMap.get(tag).containsKey(text);
	}
	
	private void addString(String tag, String text){
		if(hasString(tag, text)){
			int tempInt = unigramHashMap.get(tag).get(text);
			tempInt++;
			unigramHashMap.get(tag).put(text, tempInt);
		}else{
			unigramHashMap.get(tag).put(text, 1);
		}
	}
	
	public void printUnigramSizeHashMap(){
		System.out.println(unigramSizeHashMap);
	}
	
	public void printUnigramHashMap(){
		for (String key : unigramHashMap.keySet()) {
		    System.out.println("=== " + key + " ===");
		    System.out.println(unigramHashMap.get(key).toString());
		}
	}
	
	public String printUnigramHashMap2(){
		return unigramHashMap.toString();
	}
	
	public int getUnigramSize(){
		return unigramHashMap.size();
	}
	
	public int getUnigramTagSize(String tag){
		
		if(unigramSizeHashMap.containsKey(tag)){
			return unigramSizeHashMap.get(tag);
		}else{
			return 0;
		}
		
		
//		if(unigramHashMap.containsKey(tag)){
//			int sum = 0;
//			Iterator<String> keySetIterator = unigramHashMap.get(tag).keySet().iterator();
//			while(keySetIterator.hasNext()){
//				String key = keySetIterator.next();
//				System.out.println(key);
//				sum += unigramHashMap.get(tag).get(key);
//			}
//			return sum;
//		}else{
//			return 0;
//		}
//		
//		//debug
//		//return 1;
		
	}
	
	public void setUnigramSizeHashMap(){
		
		Iterator<String> keySetIterator1 = unigramHashMap.keySet().iterator();
		while(keySetIterator1.hasNext()){
			String key1 = keySetIterator1.next();
			int sum = 0;
			
			Iterator<String> keySetIterator2 = unigramHashMap.get(key1).keySet().iterator();
			while(keySetIterator2.hasNext()){
				String key2 = keySetIterator2.next();
				sum += unigramHashMap.get(key1).get(key2);
			}
			
			unigramSizeHashMap.put(key1, sum);
			
		}
		
	}
	
	public int getUnigramTagStringValue(String tag, String text){
		
		if(unigramHashMap.containsKey(tag)){
			if(unigramHashMap.get(tag).containsKey(text)) return unigramHashMap.get(tag).get(text);
			else return 0;
		}
		else return 0;
	}

	
}
