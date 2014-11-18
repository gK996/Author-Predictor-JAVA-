import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the TextFileManager.
 *
 * @author  Rachel CO
 * @version April 2014
 */
public class testTextFileManager
{
    String testDir = "shorttexts/";

    @Test(timeout=1000)
    public void testCleanStringAllChars()
    {
        String s = TextFileManager.cleanString("AbCdEF   G  h");
        assertEquals("AbCdEF G h",s);
    }
    
    @Test(timeout=1000)
    public void testCleanString()
    {
        String s = TextFileManager.cleanString(" *A(b, <_c>).. \nDE;\r\t!f?");
        assertEquals(" Ab c DEf",s);
    }

    @Test(timeout=1000) 
    public void testSizePlainfile() 
    {
        ArrayList<String> filelines1 = 
            TextFileManager.readFile(testDir+"english1.txt");
        assertEquals(25, filelines1.size());
        ArrayList<String> filelines2 = 
            TextFileManager.readFile(testDir+"german1.txt");
        assertEquals(23, filelines2.size());
    }
    
    @Test(timeout=1000) 
    public void testCleanSizePlainFile1() 
    {
        ArrayList<String> filelines1 = 
            TextFileManager.readFile(testDir+"english1.txt");
        String s = TextFileManager.cleanString(filelines1);
        assertEquals(2593, s.length());
        assertTrue(s.startsWith("There once was a man who had a donkey"));
    }

    @Test(timeout=1000) 
    public void testCleanSizePlainFile2() 
    {
        ArrayList<String> filelines1 = 
            TextFileManager.readFile(testDir+"german1.txt");
        String s = TextFileManager.cleanString(filelines1);
        assertEquals(2609, s.length());
        assertTrue(s.startsWith("Es war einmal ein Mann der hatte einen Esel"));
    }
    
    /**
    * Try writing over existing file to check exception is managed within writeFile method
    */
    @Test(timeout=1000) 
    public void writeNonFile()
    {
        TextFileManager.writeFile(testDir+"english1.txt", null);
        assertTrue(true);  //check you get here to be sure exception is handled within the method
    }

    /**
    * test reading non-existent file
    */
    @Test(timeout=1000) 
    public void readNonFile()
    {
        ArrayList<String> filelines = 
            TextFileManager.readFile(testDir+"romeoandjuliet.txt");
        assertTrue(true); //check you get here to be sure exception is handled within the method
    }

    
}