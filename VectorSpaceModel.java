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
		Project pr = new Project();
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader("Dataset.txt"));
			String record;
			
			
			while((record = fileReader.readLine())!=null){
				String[] tempArray = record.split("\\s+", 2);
				String NG = tempArray[0];
				System.out.println(tempArray[1]);
				String recordString = null;
				if (tempArray.length > 1) {
					recordString = tempArray[1];
				}
				String r ="";
				if (documents.containsKey(NG)) {
					r = documents.get(NG);
				}
				r = r + " " + recordString; 
//				System.out.println(r.trim());
				documents.put(NG, r.trim());	
			}
			String[] query = new String[args.length];
			query = args;
			Set<String> docRecord = documents.keySet();
			System.out.println(docRecord);
			for (String doc : docRecord) {
			   pr.rankingofDocument(documents.get(doc),query);
			}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void rankingofDocument(String record, String[]query){
		System.out.println(record);
		Map<String,Integer> documentVector = new HashMap<String,Integer>();
		Map<String,Integer> queryVector = new HashMap<String,Integer>();
		Map<String,Double> documentVector1 = new HashMap<String,Double>();
		Map<String,Double> queryVector1 = new HashMap<String,Double>();
		Map<String,Double> documentVector2 = new HashMap<String,Double>();
		Map<String,Double> queryVector2 = new HashMap<String,Double>();
		Map<String,Double> documentVector3 = new HashMap<String,Double>();
		Map<String,Double> queryVector3 = new HashMap<String,Double>();
		 String[] words = record.split("\\s+");
		 for (String word : words) {
			 int frequency = 0;
			 if(documentVector.get(word)!=null){
				 frequency = documentVector.get(word);
			 }
			 frequency =frequency + 1;
			 documentVector.put(word, frequency);
		 }
			Set<String> docRecord = documentVector.keySet();
			for (String word : docRecord) {
				double secondTerm = calsecondTerm(word);
				double weight = documentVector.get(word) * secondTerm;
				documentVector1.put(word,weight);
			}
			for (String word : docRecord) {
				double weight = 1 + Math.log(documentVector.get(word))/Math.log(2);
				documentVector2.put(word,weight);
			}
			for (String word : docRecord) {
				double secondTerm = calsecondTerm(word);
				double weight = (1 + Math.log(documentVector.get(word))/Math.log(2)) * secondTerm;
				documentVector3.put(word,weight);
			}
			 for (String word : query) {
				 int frequency = 0;
				 if(queryVector.containsKey(word)){
					 frequency = queryVector.get(word);
				 }
				 frequency =frequency + 1;
				 queryVector.put(word, frequency);
			 }
				Set<String> queryRecord = queryVector.keySet();
				int maxfrequency = 1;
				for (String word : queryRecord) {
					if (maxfrequency < queryVector.get(word) )
					{
						maxfrequency = queryVector.get(word);
					}
				}
				for (String word : queryRecord) {
					double secondTerm = calsecondTerm(word);
					double weight = (0.5 + (0.5*queryVector.get(word)/maxfrequency)) * secondTerm;
					queryVector1.put(word,weight);
				}
				for (String word : queryRecord) {
					double secondTerm =0;
					double N = 20;
					double n = 0;
					Set<String> record1 = documents.keySet();
					for (String doc : record1) {
					      String record2 = documents.get(doc);
					      if(record2.contains(word))
					    	  n = n + 1;
					}
					secondTerm = Math.log(N/n)/Math.log(2);
					queryVector2.put(word, secondTerm);
				}
				for (String word : queryRecord) {
					double secondTerm = calsecondTerm(word);
					
					double weight = (1 + Math.log(queryVector.get(word))/Math.log(2)) * secondTerm;
					queryVector3.put(word,weight);
				}
			 
		 System.out.println("Ranking of the document"+ calVecotrMutliplication(documentVector1,queryVector1));
		 
	}
	double calVecotrMutliplication(Map<String,Double> documentVector, Map<String,Double> queryVector){
		double denominator = calculateDenominator(documentVector)* calculateDenominator(queryVector);
		double numerator =0;
		Set<String> queryTerms = queryVector.keySet();
		for (String term : queryTerms){
			if(documentVector.containsKey(term)){
				numerator += documentVector.get(term)*queryVector.get(term);
			}
		}
		if (denominator == 0){
			return 0.0;
		}
		return( numerator/denominator);
	}
	
	double calculateDenominator(Map<String,Double> vector){
		double sum = 0;
		Set<String> terms = vector.keySet();
		for (String term : terms){
			sum += vector.get(term)*vector.get(term);
		}
		return Math.sqrt(sum);
		
	}
	double calsecondTerm(String word){
		double secondTerm = 0;
		double N = 20;
		double n = 0;
		Set<String> docRecord = documents.keySet();
		for (String doc : docRecord) {
		      String record = documents.get(doc);
		      if(record.contains(word))
		    	  n = n+1;
		}
		secondTerm = Math.log(N/n)/Math.log(2);
		return secondTerm;
	}

}

