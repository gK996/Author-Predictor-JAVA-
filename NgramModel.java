import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;
import java.util.Iterator;

/**
 * Ngram generator
 * 
 * @author George Krlevski
 * @version 1.0
 */
public class NgramModel
{
    //dictionary of all ngrams and their frequencies
    private HashMap<String,Integer> ngram; 
    //number of distinct characters in the input
    private int alphabetSize;
    //total number of ngrams in the input
    private int numWords;

    /** 
     * Create an n-gram frequency model for an input string
     * ngrams at the end of the string wrap to the front
     * e.g. "abbbbc" includes "bca" and "cab" in its 3-grams
     * @param int n size of n-grams to create
     * @param String inp input string to be modelled
     */
    public NgramModel(int n, String inp) 
    {
        String size = new String(); 
        int freq= 0;
        numWords= inp.length();
        alphabetSize= 0;
        ngram = new HashMap <String, Integer>();

        try{
            for(int i=0; i<inp.length(); i++){ 
                String eachGram = new String();
                //create new string 
                for(int x=0; x<n; x++){
                    eachGram = eachGram + inp.charAt((i+x)%inp.length());
                    //adds character to eachGram 
                }

                if(ngram.containsKey(eachGram)){
                    freq= ngram.get(eachGram)+1; 
                    //count frequency
                    ngram.put(eachGram,freq);
                }
                else{
                    ngram.put(eachGram,1);
                }
            }
            for(int j= 0; j< inp.length(); j++){
                if (size.indexOf(inp.charAt(j))== -1){
                    size=size + inp.charAt(j);
                    alphabetSize= size.length();

                }
            }
            
        }
        catch(NullPointerException ex){
            System.err.println("Input must be >0 " + ex.getMessage());
            // Error case
        }

    }

    /** 
     * default constructor generates model for ngrams of size 1
     */
    public NgramModel(String inp) 
    {
        this(1,inp);
    }

    /**
     * @return HashMap<String,Integer> the ngram dictionary with word frequencies
     */
    public HashMap<String,Integer> getDictionary() {
        return ngram;
    }

    /**
     * @return int the size of the alphabet of a given input
     */
    public int getAlphabetSize() {
        return alphabetSize;
    }

    /**
     * @return int the total number of ngram words counted in the model
     */
    public int getNumWords() {
        return numWords;
    }

    /**
     * Make list of all words with frequency at least freq
     * @param freq int lower frequency limit for returned words
     * @return ArrayList all words with frequency more than freq
     */
    public ArrayList<String> getTopWords(int freq) {  
        ArrayList<String> tWords = new ArrayList <String> ();
        
        for( String key : ngram.keySet()){
            //for string key in set of keys
            int wordSize= ngram.get(key);
            if( wordSize >= freq){
                tWords.add(key);
            }
        }        
        return tWords;

    }

    /**
     * @return String representation of the ngram model
     */
    public String toString()
    {
        String output= "";
        int size= getAlphabetSize();
        int num= getNumWords();
        Set key= ngram.keySet();
        for(Iterator x = key.iterator(); x.hasNext();){
            String toS= (String) x.next();   
            int  d = ngram.get(toS);
             
            output+= toS +" " + d + "\n";
        }
        
        return output + "Alphabet size= " +size + "\n" + "Number of words= " + num + "\n";

    }
}
