import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import java.nio.charset.Charset;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/**
 * TextFileManager is a small utility class with 
 * static methods to load and save text files.
 * It also provides String pre-processing methods for text analysis.
 * See the Java tutorial
 * http://docs.oracle.com/javase/tutorial/essential/io/file.html
 * 
 * @author George Krlevski 
 * @version 1.0
 */
public class TextFileManager
{
    /**
     * Pre-processing: clean a text string to characters, preserving case, and single white spaces
     * @param line input string containing text, whitespace, punctuation and control characters
     * @return String containing the alphabetic characters and single white spaces only
     */
    public static String cleanString(String line) 
    {
        StringBuilder s = new StringBuilder();
        char lastc = 'a';
        for (int i=0; i<line.length(); i++) {
            char c = line.charAt(i);
            if (('a' <= c && c <= 'z')||('A' <= c && c <= 'Z')||(c==' ' && lastc!=' ')) {
                s.append(c);
                lastc = c;
            }
        }
        return s.toString();
    }

    /**
     * clean an arraylist of strings to make a single char-only string
     */
    public static String cleanString(ArrayList<String> lines ) 
    {
        StringBuilder sb = new StringBuilder();
        for (String s : lines) {
            sb.append(cleanString(s));
        }
        return sb.toString();
    }
        
        
    //ADD additional String pre-processing functions here if needed for experiments

    /**
     * List the names of all text files in a given directory
     * @param String dirname name of the directory to read
     * @return ArrayList<String> names of all the .txt files in dirname
     */ 
    public static ArrayList<String> getAllTxtFileNames(String dirname)
    {
        ArrayList<String> filenames = new ArrayList<>();
        Path dir = Paths.get(dirname);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,"*.txt")) {
            for (Path file: stream) {
                filenames.add(file.getFileName().toString());
            }
        } catch (Exception x) {
            //(IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println("Directory listing failed");
        }
        return filenames;
    }

    /** Read a text file into an arraylist of lines of text.
     * @param String filename the text file to be read
     * @return ArrayList<String> of lines of text from the file
     */
    public static ArrayList<String> readFile(String filename)
    {
        Path path = Paths.get(filename);
        //UTF-8 Eight-bit UCS Transformation Format
        List<String> lines;
        ArrayList<String> arrlines = null;
        try {
            lines = Files.readAllLines(path, Charset.forName("UTF-8"));
            arrlines = ((ArrayList<String>) lines);
        } catch (Exception x) { //most likely an IO exceptoin
            System.err.println("File " + filename + " not found");
        }
        return (arrlines);

    }

    /**
     * Write the lines of text in an array list to a given filename
     * @param filename  The filename to write to
     * @param text      An arraylist of text lines to be written
     */
    public static void writeFile(String filename, ArrayList<String> text)
    {
        Path file = Paths.get(filename);
        try {
            Files.write(file, text, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        } catch (Exception x) { //most likely an IO exceptoin
            System.err.println("File " + filename + " can not be written");

        }

    }

}
