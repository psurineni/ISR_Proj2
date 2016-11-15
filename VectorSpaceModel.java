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

public class VectorSpaceModel {

	   public static void main(String args[])throws IOException {
	      HashMap<String, List<String>> hmap = new HashMap<String, List<String>>();
	      HashMap<String, List<String>> hash = new HashMap<String, List<String>>();
	      
	      VectorSpaceModel t =new VectorSpaceModel();
	      
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
	                }
			  catch(Exception e){
	              System.out.println(e);
	         }
	        }
	   }
	
	int numberOfwordOccurance(List<String> s1,String word)//ni//
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
	           
	   
	
