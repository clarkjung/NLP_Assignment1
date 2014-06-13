import java.io.File;

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
}

