import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the utility class MarkovModel.
 *
 * @author  Rachel Cardell-Oliver
 * @version April 2014
 */
public class testNgramModel
{

    double tolerance = 0.0001; //tolerance for double comparisons

    @Test(timeout=1000)
    public void testOneGramModel()
    {
        NgramModel test = new NgramModel("aabcabaacaac"); //default 1
        assertEquals(3,test.getAlphabetSize());
        assertEquals(12,test.getNumWords());
        assertEquals(7,(int)test.getDictionary().get("a"));
        assertEquals(2,(int)test.getDictionary().get("b"));
        assertEquals(3,(int)test.getDictionary().get("c"));
    }

    @Test(timeout=1000)
    public void testEmptyModel()
    {
        NgramModel test = new NgramModel(3, "");
        assertEquals(0,test.getAlphabetSize());
        assertEquals(0,test.getNumWords());
        assertEquals(null,test.getDictionary().get("aaa"));
    }

    @Test(timeout=1000)
    public void testBiGramModel()
    {
        NgramModel test = new NgramModel(2, "aabcabaacaac");
        assertEquals(3,test.getAlphabetSize());
        assertEquals(12,test.getNumWords());
        assertEquals(3,(int)test.getDictionary().get("aa"));
        assertEquals(2,(int)test.getDictionary().get("ab"));
        assertEquals(2,(int)test.getDictionary().get("ac"));
        assertEquals(1,(int)test.getDictionary().get("ba"));
        assertEquals(1,(int)test.getDictionary().get("bc"));
        assertEquals(3,(int)test.getDictionary().get("ca"));

    }

    @Test(timeout=1000)
    public void testTriGramModel()
    {
        NgramModel test = new NgramModel(3,"aabcabaacaac");

        assertEquals(12,test.getNumWords());
        assertEquals(1,(int)test.getDictionary().get("aab"));  
        assertEquals(2,(int)test.getDictionary().get("aac"));
        assertEquals(1,(int)test.getDictionary().get("aba"));
        assertEquals(1,(int)test.getDictionary().get("abc"));
        assertEquals(2,(int)test.getDictionary().get("aca"));
        assertEquals(1,(int)test.getDictionary().get("baa"));
        assertEquals(1,(int)test.getDictionary().get("bca"));
        assertEquals(2,(int)test.getDictionary().get("caa"));
        assertEquals(1,(int)test.getDictionary().get("cab"));
    }

    //     @Test(timeout=1000)
    //     public void testToString()
    //     {
    //         NgramModel test = new NgramModel(3,"aabcabaacaac");
    //         test.toString();
    //     }
}
