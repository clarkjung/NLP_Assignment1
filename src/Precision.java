import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class Precision {

	private ArrayList<Double> precisionList;
	
	public Precision(){
		this.precisionList = new ArrayList<Double>();
	}
	
	public String printPrecisionList(){
		return precisionList.toString();
	}
	
	public double calculatePrecision(ArrayList<String> answerList, ArrayList<String> guessList){

		//debug
		System.out.println("================");
		System.out.println("answerList:" + answerList.toString());
		System.out.println("guessList:" + guessList.toString());


		
		
		if(answerList.size() != guessList.size()){
			System.out.println("answerList size != guessList");
			return 0.0;
		}
		
		double sizeOfSentence = answerList.size();
		double hit = 0.0;
		
		for ( int i = 0 ; i < sizeOfSentence ; i++ ){
			
			String answerStr = answerList.get(i);
			String guessStr = guessList.get(i);
			
			if(answerStr.equals(guessStr)) hit++;
		}
		
		//debug
		System.out.println("hit: " + hit);
		System.out.println("sizeofsentence: " + sizeOfSentence);
		
		return hit/sizeOfSentence;
		
	}
	
	public void addToPrecisionList(double precision){
		precisionList.add(precision);
	}
	
	public String findHighestPossibilityTag(UnigramTask2 unigramTask2, BigramTask2 bigramTask2, String previousTag, String text, Set<String> tagSet){
		
		double highestTwoPossibilities = 0.0;
		String highestPossibilityTag = "";
		
		Iterator<String> iterator = tagSet.iterator();
		while(iterator.hasNext()){
			String setElement = iterator.next();
			
			double wordLikelyhoodProbabilityForThisSetElementNumerator = unigramTask2.getUnigramTagStringValue(setElement, text);
			double wordLikelyhoodProbabilityForThisSetElementDenominator = unigramTask2.getUnigramTagSize(setElement);
			double tagTransitionProbabilityForThisSetElementNumerator = bigramTask2.getBigramTagPreviousTagValue(setElement, previousTag);
			double tagTransitionProbabilityForThisSetElementDenominator = unigramTask2.getUnigramTagSize(previousTag);
			
			double wordLikelyhoodProbabilityForThisSetElement = wordLikelyhoodProbabilityForThisSetElementNumerator/wordLikelyhoodProbabilityForThisSetElementDenominator;
			double tagTransitionProbabilityForThisSetElement = tagTransitionProbabilityForThisSetElementNumerator/tagTransitionProbabilityForThisSetElementDenominator;
			//double wordLikelyhoodProbabilityForThisSetElement = unigramTask2.getUnigramTagStringValue(setElement, text)/unigramTask2.getUnigramTagSize(setElement);
			//double tagTransitionProbabilityForThisSetElement = bigramTask2.getBigramTagPreviousTagValue(setElement, previousTag)/unigramTask2.getUnigramTagSize(previousTag);
			
			double twoPossibilities = wordLikelyhoodProbabilityForThisSetElement * tagTransitionProbabilityForThisSetElement;
			
			if(twoPossibilities >= highestTwoPossibilities){
				highestTwoPossibilities = twoPossibilities;
				highestPossibilityTag = setElement;
			}
		}
		return highestPossibilityTag;
	}
	
	public double getPrecision(){
		double sum = 0;
		
		for(int i = 0;i < precisionList.size();i++){
			sum += precisionList.get(i);
		}
		
		return sum/precisionList.size();
	}
	

	
}
