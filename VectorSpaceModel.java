import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VectorSpaceModel {
	public static Map<String,String> documents = new HashMap<String,String>();
	public static void main(String[] args) {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader("Dataset.txt"));
			String record;
			
			
			while((record = fileReader.readLine())!=null){
				String[] tempArray = record.split("\\s+", 2);
				String NG = tempArray[0];
				String recordString = null;
				if (tempArray.length > 1) {
					recordString = tempArray[1];
				}
				String r = null;
				if (documents.containsKey(NG)) {
					r = documents.get(NG);
				}
				r = r + " " + recordString; 
				documents.put(NG, r);	
			}
			String[] query = new String[Integer.parseInt(args[0])];
			for(int i=1; i<Integer.parseInt(args[0]); i++) {
				System.out.println(args[i]);
				query[i-1] = args[i];
			}
			
			Set<String> docRecord = documents.keySet();
			for (String doc : docRecord) {
			   rankingofDocument(documents.get(doc),query);
			}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void rankingofDocument(String record, String[]query){
		Map<String,Integer> documentVector = new HashMap<String,Integer>();
		Map<String,Integer> documentVector1 = new HashMap<String,Integer>();
		 String[] words = record.split("\\s+");
		 for (String word : words) {
			 int frequency = 0;
			 if(documentVector.get(word)!=null){
				 frequency = documentVector.get(word);
			 }
			 documentVector.put(word, frequency);
		 }
			Set<String> docRecord = documentVector.keySet();
			for (String word : docRecord) {
				double secondTerm = calsecondTerm(word);
				double weight = documentVector.get(word) * secondTerm
				documentVector1.put(word,weight);
			}
		 
	}

}
	           
	   
	
