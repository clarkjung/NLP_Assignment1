import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLFileProcessor {
	
	// constructor
	public XMLFileProcessor() {
	}
	
	public void makeHashMap(UnigramTask2 unigramTask2, BigramTask2 bigramTask2, String filepath) {
		
		try {

			File stocks = new File(filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
			
			String previousTag = "";
			String currentTag = "";

			//System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("sentence");
			//System.out.println("==========================");
			
			for (int i = 0; i < nodes.getLength(); i++) {

				//System.out.println("========== sentence number : " + i + "==========");

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					NodeList tokList = element.getElementsByTagName("tok");

					for (int j = 0; j < tokList.getLength(); j++) {
						Node tokNode = tokList.item(j);

						if (tokNode.getNodeType() == Node.ELEMENT_NODE) {
							Element tokElement = (Element) tokNode;
							currentTag = tokElement.getAttribute("cat");
							
							//process start-sentence-tag "<S>"
							if (j == 0) {
								//add to Unigram
								//String startString = "<S>";
								//System.out.print(startString + " ");
								unigramTask2.addTag("START", "<S>");
							}
							//add to Unigram
							//System.out.print(currentString + " ");
							unigramTask2.addTag(currentTag, tokElement.getTextContent().toLowerCase());
							
							
							//add to Bigram
							if(j==0){
								bigramTask2.addTag(currentTag, "<S>");
							}else{
								bigramTask2.addTag(currentTag, previousTag);
							}
							
							previousTag = currentTag;
						}
					}
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	

	public void makeHashMap(Unigram unigram, Bigram bigram, String filepath) {

		try {

			File stocks = new File(filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
			
			String previousString = "";
			String currentString = "";

			//System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("sentence");
			//System.out.println("==========================");

			for (int i = 0; i < nodes.getLength(); i++) {

				//System.out.println("========== sentence number : " + i + "==========");

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					NodeList tokList = element.getElementsByTagName("tok");

					for (int j = 0; j < tokList.getLength(); j++) {
						Node tokNode = tokList.item(j);

						if (tokNode.getNodeType() == Node.ELEMENT_NODE) {
							Element tokElement = (Element) tokNode;
							currentString = tokElement.getTextContent();							
							
							//process start-sentence-tag "<S>"
							if (j == 0) {
								//add to Unigram
								String startString = "<S>";
								//System.out.print(startString + " ");
								unigram.add(startString);
							}
							//add to Unigram
							//System.out.print(currentString + " ");
							unigram.add(currentString);
							
							
							//add to Bigram
							if(j==0){
								bigram.add("<S>", currentString);
							}else{
								bigram.add(previousString, currentString);
							}
							
							previousString = currentString;
						}
					}
				}
				//System.out.print("\n");
				//System.out.println(node.getTextContent());
				
			}
			//System.out.println("nodes.length: " + nodes.getLength());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void extractSentences(Unigram unigram, Bigram bigram, String filepath, Perplexity perplexityCalculator){
		try {

			File stocks = new File(filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName("sentence");

			

			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);
				
				String extractedSentence = node.getTextContent();
				perplexityCalculator.calculate(unigram, bigram, extractedSentence);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void extractSentences(UnigramTask2 unigramTask2, BigramTask2 bigramTask2, String filepath, Precision precision){
		try {

			File stocks = new File(filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
			
			Set<String> tagSet = bigramTask2.getTagSet();
			
			String currentTag = "";
			String previousGuessTag = "";
			ArrayList<String> answerList = new ArrayList<String>();
			ArrayList<String> guessList = new ArrayList<String>();

			//System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("sentence");
			//System.out.println("==========================");
			
			for (int i = 0; i < nodes.getLength(); i++) {

				//System.out.println("========== sentence number : " + i + "==========");

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					NodeList tokList = element.getElementsByTagName("tok");

					for (int j = 0; j < tokList.getLength(); j++) {
						Node tokNode = tokList.item(j);

						if (tokNode.getNodeType() == Node.ELEMENT_NODE) {
							
							Element tokElement = (Element) tokNode;
							currentTag = tokElement.getAttribute("cat");
							
							//save currentTag with answerList
							answerList.add(currentTag);
							
							if(j==0) previousGuessTag = "START";
							
							String currentGuessTag = precision.findHighestPossibilityTag(unigramTask2, bigramTask2, previousGuessTag, tokElement.getTextContent(), tagSet);
							
							//find the tag with the highest possibility and put it into guessList
							guessList.add(currentGuessTag);
							
							previousGuessTag = currentGuessTag;
							
						}
					}//finish one sentence
					
					//calculate precision of this sentence (arrraylist1, arraylist2)
					//insert to precision
					precision.addToPrecisionList(precision.calculatePrecision(answerList, guessList));
					
					//clear answerList and guessList
					answerList.clear();
					guessList.clear();
					
					
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

