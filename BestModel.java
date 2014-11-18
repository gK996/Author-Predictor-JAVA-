import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;


/**
 * Write a description of class BestModel here.
 * 
 * @author George Krlevski 
 * @version 1.0
 */
public class BestModel
{
    //Markov models for text1 and text2
    MarkovModel model1, model2; 
    //k+1-gram model for test string
    NgramModel testNgram;
    //log likelihoods of test with model1 and model2
    HashMap<String,Double> loglike1, loglike2; 
    //order of the Markov models
    int order;

    /**
     * Generate k-th order Markov models for text1 and text2
     * and calculate loglikelihoods of test against each model.
     * These structures can then be queried to find best matches.
     * @return true if test is closest to text1 and false otherwise
     */
    public BestModel(int k,
    String text1,
    String text2,
    String test) 
    {
        this.order= k;
        model1= new MarkovModel(k,text1);
        model2= new MarkovModel(k,text2);
        testNgram= new NgramModel(k+1,test);
        //initializing

        loglike1= new HashMap<String,Double> ();
        loglike2 = new HashMap<String, Double>();

        for( String each: testNgram.getDictionary().keySet()){
            double prob1= model1.laplace(each);
            double prob2= model2.laplace(each);
            //get and put in loglike
            loglike1.put(each,(Math.log(prob1)));
            loglike2.put(each, (Math.log(prob2)));
        }

    }

    /** 
     * Get average log likelihood for the current models.
     * @input whichModel 1=model1, 2=model2
     * @input test String for loglikelihood to be calculated
     * @return HashMap<String,Double> log likelihood for each word of test
     */
    public HashMap<String,Double> logLikelihood(int whichModel) 
    {
        if(whichModel==1){
            //model1
            return loglike1;

        }
        if(whichModel==2){
            //model2
            return loglike2;
        }
        //Else
        return null;
    }

    public double totalLogLikelihood(HashMap<String,Double> logs)
    {        
        //for loop to add loglikelihood
        double sum= 0.0;
        for(String x: testNgram.getDictionary().keySet()){
            sum+= logs.get(x);
        }
        return sum;
    }

    public double averageLogLikelihood(HashMap<String,Double> logs)
    {
        double result= 0.0;
        double totalNumber=logs.size();
        double logT= this.totalLogLikelihood(logs);        
        if(logT==0.0){
            logT=1.0; //Error case
        }
        else{
            result = (double)totalLogLikelihood(logs)/totalNumber;
        }

        //total/number of logs
        return result;
    }

    /**
     * Given precalculated model1, model2 and loglike1 and loglike2 find best match to test
     * @return true if text1 is best match and false if text2 is best match
     */
    public boolean chooseBestModel() 
    {
        double i= averageLogLikelihood(loglike1);
        double j= averageLogLikelihood(loglike2);
        if (i> j){
            return true; 
        }
        else{
            return false;
        }
    }

    /**
     * @return confidence measure in best match
     */
    public double confidence() 
    {
        double x = averageLogLikelihood(loglike1);
        double y= averageLogLikelihood(loglike2);
        double prob= Math.abs(x-y);
        
        return prob;
    }

    /**
     * Find the top n words for which the difference in the 
     * log probabilities of two models are greatest.
     * @param topn int number of obs,difs to return
     * @return HashMap<String,Double> top k strings and 
     *    their loglikelihood differences between model 1 and model 2
     */
    public HashMap<String,Double> explainBestModel(int topn)
    {
        HashMap <String , Double> topK=new HashMap<String, Double>();
        HashMap <String , Double> tWords=new HashMap<String, Double>();
        double x= 0;
        double y=0;
        double difs = 0;
        for(String s: testNgram.getDictionary().keySet()){
            x= logLikelihood(1).get(s);
            y= logLikelihood(2).get(s);
            difs= Math.abs(x-y);
            topK.put(s,difs);// puts all 
        }

        List<Entry<String,Double>> sortedEntries = new ArrayList<Entry<String,Double>>(topK.entrySet());

        Collections.sort(sortedEntries, 
            new Comparator<Entry<String,Double>>() {
                @Override
                public int compare(Entry<String,Double> e1, Entry<String,Double> e2) {
                    return e2.getValue().compareTo(e1.getValue());//comparison
                }
            }
        );
        //Sorting
        sortedEntries = sortedEntries.subList(0, topn);
        // sublist of 0, topn
        for(int i=0; i<sortedEntries.size(); i++){
            String key =  sortedEntries.get(i).getKey();
            Double value= sortedEntries.get(i).getValue();
            tWords.put(key,value);//from List to HashMap
            
        }

        return tWords;

    }
}

