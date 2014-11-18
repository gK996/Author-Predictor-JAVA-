
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class testMarkovModel.
 *
 * @author  Rachel Cardell-Oliver
 * @version April 2014
 */
public class testMarkovModel
{

    double tolerance = 0.0001; //tolerance for double comparisons

    @Test(timeout=1000)
    public void testMarkovModel()
    {
        MarkovModel model = new MarkovModel(2, "aabcabaacaac");
        assertEquals(3,model.getAlphabetSize());
        assertEquals(2,model.getK());
        assertEquals(0.5000, model.laplace("aac"), tolerance);
        assertEquals(0.1667, model.laplace("aaa"), tolerance);
        assertEquals(0.3333, model.laplace("aab"), tolerance);
    }

    @Test(timeout=1000)
    public void testLaplace()
    {

        MarkovModel train = new MarkovModel(2, "aabcabaacaac");
        System.out.println(train.toString());
        //test obs from new string "aabca"
        assertEquals((double)(1 + 1) / (3 + 3), train.laplace("aab"), tolerance);
        assertEquals((double)(1 + 1) / (2 + 3), train.laplace("abc"), tolerance);
        assertEquals((double)(1 + 1) / (1 + 3), train.laplace("bca"), tolerance);
        assertEquals((double)(2 + 1) / (3 + 3), train.laplace("caa"), tolerance);
        assertEquals((double)(0 + 1) / (3 + 3), train.laplace("aaa"), tolerance);
    }
}
