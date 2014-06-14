import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Task1 {

	public static String FOLDERPATH = "/Users/jaeyoonjung/Documents/My_Documents/HPI/SS 2014/NLP/exercises/AssignmentOne/GeniaTreebank";
	public static int TRAININGFILESPERCENTAGE = 90;
	Unigram unigram;
	Bigram bigram;
	int numberOfFiles;
	int numberOfTrainingFiles;
	Perplexity perplexityCalculator;
	List<String> randomlySelectedTrainingSet;
	List<String> allFiles;

	// contructor
	public Task1() {
		this.unigram = new Unigram();
		this.bigram = new Bigram();
		this.numberOfFiles = 0;
		this.numberOfTrainingFiles = 0;
		this.perplexityCalculator = new Perplexity();
		this.randomlySelectedTrainingSet = new LinkedList<String>();
		this.allFiles = new LinkedList<String>();
	}

	public static void main(String args[]) {

		Task1 nlp = new Task1();
		nlp.decideNumberOfTrainingFiles(TRAININGFILESPERCENTAGE);
		//System.out.println(nlp.allFiles);
		nlp.randomlySelectFiles(nlp.allFiles, nlp.numberOfTrainingFiles);
		//System.out.println(nlp.randomlySelectedTrainingSet);
		nlp.train();
		//System.out.println(nlp.unigram.printUnigramHashMap());
		//System.out.println(nlp.bigram.printBigramHashMap());
		//System.out.println(nlp.unigram.getUnigramSize());
		//System.out.println(nlp.bigram.getBigramSize());

		System.out.println("training done!");
		
		nlp.test();
		
		//get perplexity
		System.out.println(nlp.perplexityCalculator.getPerplexity());
		
		//System.out.println(nlp.perplexityCalculator.getPrListSize());

	}

	private void decideNumberOfTrainingFiles(int testFilesPercentage) {

		// read in all files ending with ".xml" from FOLDERPATH
		File folder = new File(FOLDERPATH);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {

			if (file.isFile()) {

				String fileNameOnly = file.getName();
				String filepath = FOLDERPATH + "/" + fileNameOnly;

				if (filepath.endsWith(".xml")) {
					this.allFiles.add(fileNameOnly);
					this.numberOfFiles++;
				}

			}

		}

		// set numberOfTrainingFiles
		this.numberOfTrainingFiles = this.numberOfFiles * testFilesPercentage
				/ 100;

		// System.out.println("numberOfFiles: " + this.numberOfFiles);
		// System.out.println("numberOfTrainingFiles: " +
		// this.numberOfTrainingFiles);

	}
	
	private void randomlySelectFiles(List<String> allFiles, int numberOfTrainingFiles){
		
		List<String> copy = new LinkedList<String>(allFiles);
		Collections.shuffle(copy);
		this.randomlySelectedTrainingSet = copy.subList(0, numberOfTrainingFiles);
	}

	private void train() {

		// read in all files ending with ".xml" from FOLDERPATH
		File folder = new File(FOLDERPATH);
		File[] listOfFiles = folder.listFiles();

		XMLFileProcessor xmlFileProcessor = new XMLFileProcessor();

		for (File file : listOfFiles) {

			if (file.isFile()) {

				String fileNameOnly = file.getName();
				String filepath = FOLDERPATH + "/" + fileNameOnly;
				
				//System.out.println(fileNameOnly);

				if (this.randomlySelectedTrainingSet.contains(fileNameOnly)) {
					xmlFileProcessor.makeHashMap(this.unigram, this.bigram, filepath);
				}
			}
		}
		
		return;
	}

	private void test() {
		// read in all files ending with ".xml" from FOLDERPATH
		File folder = new File(FOLDERPATH);
		File[] listOfFiles = folder.listFiles();
		int count = 0;

		XMLFileProcessor xmlFileProcessor = new XMLFileProcessor();

		for (File file : listOfFiles) {

			if (file.isFile()) {

				String fileNameOnly = file.getName();
				String filepath = FOLDERPATH + "/" + fileNameOnly;

				if (filepath.endsWith(".xml")) {
					count++;
					if(count <= this.numberOfTrainingFiles) continue;
					
					xmlFileProcessor.extractSentences(unigram, bigram, filepath, perplexityCalculator);

				}
			}
		}
	}

}
