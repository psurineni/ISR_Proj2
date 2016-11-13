import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.*;
import java.util.Iterator;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;
import java.math.*;

public class assignmentOne {

	   public static void main(String args[])throws IOException {

	      /* This is how to declare HashMap */
	      HashMap<String, List<String>> hmap = new HashMap<String, List<String>>();
	      HashMap<String, List<String>> hash = new HashMap<String, List<String>>();
	      
	      assignmentOne t =new assignmentOne();
	      
		  try (BufferedReader br = new BufferedReader(new FileReader("dataset.txt"))) 
	          {
	            String line;
		    String[] temp;
	            while((line = br.readLine()) != null) {
	                List<String> list = new ArrayList<String>();
	                temp=line.split("\\s+",2);
	                if (temp.length > 1)
	                {
	                     if ( hmap.containsKey(temp[0]))
	                     {
	                        list = hmap.get(temp[0]);
	                     }
	                     list.add(temp[1]);
	                  hmap.put(temp[0],list);
	                }
	            }
	         }
		  try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) 
	          {
	            String line;
		       String[] temp1;
	            while((line = br.readLine()) != null) {
	                List<String> list = new ArrayList<String>();
	                temp1=line.split(",");
	                for (int i =0;i<temp1.length;i++)
	                    System.out.println("command" + temp1[i]);
	                 if (temp1.length == 3)
	                {
	                    int command = Integer.parseInt(temp1[0]);
	                    if(command == 1){
	                        //Entropy
	                      t.Entropy (temp1[1],temp1[2],hmap); 
	                    }
	                    else if(command== 2){
	                        t.conditionEntropy(temp1[1],temp1[2],hmap);
	                    }
	                    else if(command == 3){
	                        //Mutual information
	                       t.mutualInformation(temp1[1],temp1[2],hmap);
	                    }
	                    else if(command == 4)
	                    {
	                        //KL distance
	                       t.KLdistance(temp1[1],temp1[2],hmap);
	                    }
	                    else {
	                     list.add(temp1[1]);
	                        System.out.println("Wrong input command");     
	                    }
	                }
	                else 
	                {
	                    System.out.println("Insufficient parameters");
	                }
	            }
	         }catch(Exception e){
	              System.out.println(e);
	         }
	        }
	           
	   
	void Entropy(String command2, String command3, HashMap<String ,List<String>> Dataset)
	{
	    if (command2.trim().equals("all"))
	    {
	        System.out.println("\n\nThis is entropy method");
	        Set set = Dataset.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()){ 
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("\nKey is: \n"+ mentry.getKey() + " & Entropy is: "+entropyCal((List)mentry.getValue(),command3,Dataset));

	      }      
	    }
	    else if(Dataset.containsKey(command2))
	    {
	        System.out.println("individual");
	        System.out.print("\nKey is: \n"+ command2 + " & Entropy is: "+entropyCal((List)Dataset.get(command2),command3,Dataset));
	    }
	    
	}

	double entropyCal(List<String> newsGroup,String command3,HashMap<String ,List<String>> Dataset)
	{
	    double entropy = 0.0;
	    //System.out.println("this is entropyCal");
	    //System.out.println("command3" +command3);
	    String[] words = command3.trim().split("\\s+");
	    //List<String> list=Dataset.get(newsGroup);
	    for (String word : words)
	    {
		double a=probability(newsGroup,word);
		double b=probability(newsGroup,word);
		if(b!=0)
		{
	        entropy = entropy - (a*(Math.log(b)/Math.log(2)));
		}
	    }
	    return entropy;
	}
	void conditionEntropy(String command2, String command3,HashMap<String ,List<String>> Dataset)
	{    
	    if (command2.trim().equals("all"))
	    {
	        System.out.println("\n\n\n\nThis is conditinal entropy method\n");
	        Set set = Dataset.entrySet();
	        Iterator iterator = set.iterator();
	      while(iterator.hasNext()){ 
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("\nKey is: \n"+ mentry.getKey() + " & Value is: \n"+conditionalEntropyCal((List)mentry.getValue(),command3,Dataset));
	      }      
	    }
	    else if(Dataset.containsKey(command2))
	    {
	        System.out.print("\nKey is: "+ command2 + " & Value is: "+conditionalEntropyCal((List)Dataset.get(command2),command3,Dataset));
	    }
	}
	double conditionalEntropyCal(List<String> newsGroup,String command3,HashMap<String ,List<String>> Dataset)
	{
	    double cond = 0.0;
	    String[] words = command3.trim().split("\\s+");
	    //List<String> list=Dataset.get(newsGroup);
	    String firstWord = words[0];
	    for (int j =0;j<words.length;j++)
	    {
	        double probOfSecond = probability(newsGroup,words[j]);
	        double joinfProbability = probability(newsGroup,firstWord,words[j]);
		if (joinfProbability!=0)
		{
	        cond = cond +joinfProbability * (Math.log(probOfSecond/joinfProbability)/Math.log(2));
		}
	    }
	    //System.out.println("conditinal entropy " + cond );
	    return cond;
	}
	void mutualInformation(String command2, String command3,HashMap<String ,List<String>> Dataset)
	{
	            if (command2.trim().equals("all"))
	    {
	        System.out.println("\n\n\n\nThis is mutual inforamtion method\n");
	        Set set = Dataset.entrySet();
	        Iterator iterator = set.iterator();
	      while(iterator.hasNext()){ 
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("Key is: \n"+ mentry.getKey() + " & Value is: \n"+miCal((List)mentry.getValue(),command3,Dataset));
	      }      
	    }
	    else if(Dataset.containsKey(command2))
	    {
	        System.out.print("Key is: \n"+ command2 + " & Value is: \n"+miCal((List)Dataset.get(command2),command3,Dataset));
	    }
	    
	}
	double miCal(List<String> newsGroup,String command3,HashMap<String ,List<String>> Dataset)
	{
	    double mi = 0.0;
	    String[] words = command3.trim().split("\\s+");
	    //List<String> list=Dataset.get(newsGroup);
	    double probOfFirst = probability(newsGroup,words[0]);
	    double probOfSecond = probability(newsGroup,words[1]);
	    double joinfProbability = probability(newsGroup,words[0],words[1]);
	    if(joinfProbability!=0)
	    {
	    mi = -(probOfFirst * Math.log(probOfFirst))+(joinfProbability * (Math.log(probOfSecond/joinfProbability)/Math.log(2)));
	    }
            return mi;
	}
	void KLdistance(String command2, String command3,HashMap<String ,List<String>> Dataset)
	{
	    System.out.println("\n\n\n\nThis is KL divergence method\n");
	        double kldistance = 0.0;
	        Set set = Dataset.entrySet();
	        double [] array1 =null;
	        double [] array2 = null;
	        Map <String,Integer> m1 = new HashMap<String,Integer>();
	        Map <String,Integer> m2 = new HashMap<String,Integer>();
	        int noOfOccuranceinEachGroup1 =0;
	        int totalOccurance1 = 0;
	        int noOfOccuranceinEachGroup2 =0;
	        int totalOccurance2 = 0;
	        Iterator iterator = set.iterator();
	        String[] words = command3.trim().split("\\s+");
	        int index=0;
	      while(iterator.hasNext()){ 
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         
	         noOfOccuranceinEachGroup1= numberOfwordOccurance((List<String>)mentry.getValue(),words[0]);
	         noOfOccuranceinEachGroup2 = numberOfwordOccurance((List<String>)mentry.getValue(),words[1]);

	         m1.put((String)mentry.getKey(),noOfOccuranceinEachGroup1);
	         totalOccurance1 = totalOccurance1+noOfOccuranceinEachGroup1;
	         m2.put((String)mentry.getKey(),noOfOccuranceinEachGroup2);
	         totalOccurance2 = totalOccurance2+noOfOccuranceinEachGroup1;
	      }    
	      
	      
	      Set<String> s = Dataset.keySet();
	      for (String ng:s)
	      { 
	          double probA =0;
	          double probB = 0;
	          if (totalOccurance1!=0)
	        	  probA = (double)m1.get(ng)/totalOccurance1;
	          if (totalOccurance2 !=0)
	             probB = (double)m2.get(ng)/totalOccurance2;
	          if(probA!= 0 && probB!=0)
	            kldistance = kldistance + (probA * (Math.log(probA/probB)/Math.log(2)));
	      }
	      System.out.println("KL divergence of words " + command3 +" is "+kldistance);
	}

	double probability(List<String>ls, String word)
	{
	    double count = 0.0;
	    double prob=0;
	    for (int i=0;i<ls.size();i++)
	    {
	        if(ls.get(i).contains(word))
	        {
	            count++;
	        }
	    }
	    if(ls.size()!=0)
	    {
	    prob = (double)count/(double)ls.size();
	    }
	    return prob;
	}
	double probability(List<String>ls, String word1,String word2)
	{
	    double count = 0;
	    double prob = 0;
	    for (int i=0 ;i < ls.size();i++)
	    {
	        if(ls.get(i).contains(word1) && ls.get(i).contains(word2) )
	        {
	            count++;
	        }
	    }
 	    if(ls.size()!=0)
	    {
	    prob = (double)count/(double)ls.size();
	    }
	    return prob;
	}
	int numberOfwordOccurance(List<String> s1,String word)
	{
	    int count=0;
	    for(String w:s1)
	    {
	        if(w.contains(word))
	            count++;
	    }
	    return count;
	}


	}
	       