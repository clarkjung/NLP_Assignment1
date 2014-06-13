import java.util.ArrayList;
import java.util.StringTokenizer;


public class Perplexity {

	private ArrayList<Double> prList;
	
	public Perplexity(){
		this.prList = new ArrayList<Double>();
	}
	
	public void calculate(Unigram unigram, Bigram bigram, String string){
		String sentence = "<S>" + " " + string;
		int sentenceLength = calculateSentenceLength(sentence);
		String previousToken = "";
		double product = 1;

        StringTokenizer tokenizer = new StringTokenizer(sentence," ");

        while (tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();
            if(previousToken.equals("")){
            	previousToken = token;
            	continue;
            }
            
            double numerator = bigram.getValue(previousToken + " " + token) + 1;
            double denominator = unigram.getValue(previousToken) + unigram.getUnigramSize();
            //System.out.println(numerator + "  " + denominator + "  " + "previoustoken:" + previousToken + "  " + unigram.getValue(previousToken) + "  " + unigram.getUnigramSize() + "  " + numerator/denominator);
            
            product *= Math.pow(numerator/denominator, 1.0/sentenceLength);
            
            previousToken = token;
        }
        
        //System.out.println(1/product);
        
        prList.add(1/product);
	}
	
	private int calculateSentenceLength(String sentence){
		StringTokenizer tokenizer = new StringTokenizer(sentence," ");
		int sentenceLength = 0;
		
        while (tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();
            sentenceLength++;
        }
        
        return sentenceLength;
	}
	
	public int getPrListSize(){
		return this.prList.size();
	}
	
	public double getPerplexity(){
		
		double sum = 0;
		
		for(int i = 0;i < prList.size();i++){
			sum += prList.get(i);
		}
		
		return sum/prList.size();
	}
	
}
