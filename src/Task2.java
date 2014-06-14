import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class Task2 {

	public static String FOLDERPATH = "/Users/jaeyoonjung/Documents/My_Documents/HPI/SS 2014/NLP/exercises/AssignmentOne/GeniaTreebank";
	public static int TRAININGFILESPERCENTAGE = 90;
	UnigramTask2 unigramTask2;
	BigramTask2 bigramTask2;
	List<String> randomlySelectedTrainingSet;
	List<String> allFiles;
	int numberOfFiles;
	int numberOfTrainingFiles;
	Precision precision;
	
	public Task2(){
		this.unigramTask2 = new UnigramTask2();
		this.bigramTask2 = new BigramTask2();
		this.numberOfFiles = 0;
		this.numberOfTrainingFiles = 0;
		this.randomlySelectedTrainingSet = new LinkedList<String>();
		this.allFiles = new LinkedList<String>();
		this.precision = new Precision();
	}
	
	public static void main(String[] args) {
		
		Task2 task2 = new Task2();
		task2.decideNumberOfTrainingFiles(TRAININGFILESPERCENTAGE);
		task2.randomlySelectFiles(task2.allFiles, task2.numberOfTrainingFiles);
		task2.train();
		
		//System.out.println(task2.unigramTask2.printUnigramHashMap2());
		//System.out.println(task2.unigramTask2.getUnigramSize());
		//task2.unigramTask2.printUnigramHashMap();
//		System.out.println(task2.unigramTask2.getUnigramTagStringValue("PRP", "they"));
//		System.out.println(task2.unigramTask2.getUnigramTagStringValue("VBN", "occupied"));
//		System.out.println(task2.unigramTask2.getUnigramTagStringValue("NNPS", "Retroviruses"));
		
		//System.out.println(task2.bigramTask2.printBigramHashMap2());
		//System.out.println(task2.bigramTask2.getBigramSize());
		
		System.out.println("training done!");
		//System.out.println(task2.bigramTask2.getTagSet());
		//task2.unigramTask2.printUnigramSizeHashMap();
		
		task2.test();
		System.out.println("test done!");
		
		//System.out.println(task2.precision.printPrecisionList());
		System.out.println("precision: " + task2.precision.getPrecision());


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
				//String filepath = FOLDERPATH + "/" + "1309833.xml";

				//System.out.println(fileNameOnly);

				if (this.randomlySelectedTrainingSet.contains(fileNameOnly)) {
					xmlFileProcessor.makeHashMap(this.unigramTask2, this.bigramTask2, filepath);
				}
			}
		}
		
		//set unigramSizeHashMap
		this.unigramTask2.setUnigramSizeHashMap();
		
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
					
					xmlFileProcessor.extractSentences(this.unigramTask2, this.bigramTask2, filepath, precision);

				}
			}
		}
	}
	
	


}
