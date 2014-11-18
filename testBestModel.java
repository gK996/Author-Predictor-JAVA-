
import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class testBestModel.
 *
 * @author  Rachel Cardell-Oliver
 * @version April 2014
 */
public class testBestModel
{
    double tolerance = 0.0001; //test tolerance for double equality
    BestModel models1, models2, models2rev, models3;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        String text1, text2, test, textA, textB, textC;
        text1 = "aabcabaacaac";
        text2 = "aabcabaacaac";
        test = "aabca";
        models1 = new BestModel(2, text1, text2, test);
        
        textA = "aabcabaabaab";
        textB = "aaccacaacaac";
        textC = "aabcab";
        models2 = new BestModel(2, textA, textB, textC);
        models2rev = new BestModel(2, textB, textA, textC);
        models3 = new BestModel(3, textA, textB, textC);
    }

    
     @Test(timeout=1000000)
    public void testLogLikelihood()
    {   
        HashMap<String,Double> res = models1.logLikelihood(1);
        assertEquals(-1.0986, res.get("aab"), tolerance);
        assertEquals(-0.9163, res.get("abc"), tolerance);
        assertEquals(-0.6931, res.get("bca"), tolerance);
        assertEquals(-0.6931, res.get("caa"), tolerance);
        assertEquals(-1.7918, res.get("aaa"), tolerance);
    }
    
     @Test(timeout=1000)
    public void testAvgLogLikelihood()
    {   
        HashMap<String,Double> res = models1.logLikelihood(1);
        double avgLL = models1.averageLogLikelihood(res);
        assertEquals(-1.0386, avgLL, tolerance);
    }
    
     @Test(timeout=1000)
    public void testTotalLogLikelihood()
    {   
        HashMap<String,Double> res = models1.logLikelihood(1);
        double totalLL = models1.totalLogLikelihood(res); 
        assertEquals(-5.1930, totalLL, tolerance);
    }
    
    
      @Test(timeout=100000000)
    public void testExplainBestModel()
    {   
        HashMap<String,Double> top4 = models2.explainBestModel(4);
        assertEquals(4,top4.size());
        //System.out.println(top4.keySet()); aab, cab, abc, baa]
        assertEquals(1.20397, top4.get("aab"), tolerance);
        assertEquals(1.0986, top4.get("cab"), tolerance);
        assertEquals(0.5596, top4.get("abc"), tolerance);
        assertEquals(0.2877, top4.get("baa"), tolerance);
    }
   

    @Test(timeout=1000)
    public void testChooseBestModel()
    {   
        boolean best = models2.chooseBestModel();
        assertTrue(best);
        double confidence = models2.confidence();
        assertEquals(0.3607, confidence, tolerance);
        
    }
    
     @Test(timeout=1000)
    public void testChooseNotBestModel()
    {   
        boolean best = models2rev.chooseBestModel();
        assertFalse(best);
        double confidence = models2rev.confidence();
        assertEquals(0.3607, confidence, tolerance);
        
    }
    
    @Test(timeout=1000)
    public void testChooseBestModel3()
    {   
        boolean best = models3.chooseBestModel();
        assertTrue(best);
        double confidence = models3.confidence();
        assertEquals(0.0283, confidence, tolerance);
        
    }
    
}
