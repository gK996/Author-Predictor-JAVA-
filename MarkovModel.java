import java.util.ArrayList;
import java.util.HashMap;


/**
 * Construct a Markov model and make predictions.
 * 
 * @author George Krlevski 
 * @version 1.0
 */
public class MarkovModel
{

    int k; //markov model order parameter
    NgramModel ngram; //ngram model order k
    NgramModel n1gram; //ngram model order k+1
    int alphabetSize; //number of words in teh model

    /**
     * Construct an order-n Markov model from string s
     * @param n int order of the Markov model
     * @param s String input to be modelled
     */
    public MarkovModel(int n, String s) 
    {
        ngram = new NgramModel(n, s);
        n1gram= new NgramModel(n+1, s);
        alphabetSize= ngram.getAlphabetSize(); 
        k= n;
    }

    /**
     * @return order of this Markov model
     */
    public int getK()
    {
        return k;
    }

    /**
     * @return number of characters in the alphbet
     */
    public int getAlphabetSize()
    {
        return alphabetSize;
    }

    /**
     * Calculate the Laplacian probability of string obs given this Markov model
     * @input obs String of length k+1
     */
    public double laplace(String obs) 
    { 
        String front = obs.substring(0, obs.length()-1);
        //first half 
        String total = obs;
        // whole
        double result= 0;        
        int x = 1;
        int y = getAlphabetSize();

        if(ngram.getDictionary().containsKey(front)){
            y+= ngram.getDictionary().get(front);
        }

        if(n1gram.getDictionary().containsKey(total)){
            x+= n1gram.getDictionary().get(total);
        }

        result=(double)x/y;
        
        return result;

    } 
    /**
     * @return String representing this Markov model
     */
    public String toString()
    {
        return n1gram.toString() + ngram.toString();
    }

}
