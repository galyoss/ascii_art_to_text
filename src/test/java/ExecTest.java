import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ExecTest {

    @Test
    void parseConfigFile() {
        System.out.println("Testing parseConfigFile...");
        assertEquals(new HashMap<String, String>() {{
            put("     |  |", "1");
            put(" _  _| |_", "2");
            put(" _  _| _|", "3");
        }},
                Exec.parseConfigFile(new ArrayList<String>(Arrays.asList("1","   ","  |","  |","2"," _ "," _|"," |_","3"," _ "," _|"," _|"))));
    }

    @Test
    void checkTextFile() {
        ArrayList<String> TextFile1 =  Exec.readFile("src/test/TestFiles/TestFile1.txt");
        Exec.cleanExcessEnters(TextFile1);
        assertEquals(true, Exec.checkTextFile(TextFile1));
        ArrayList<String> TextFile2 =  Exec.readFile("src/test/TestFiles/TestFile2.txt");
        Exec.cleanExcessEnters(TextFile2);
        assertEquals(false, Exec.checkTextFile(TextFile2));
    }

    @Test
    void parseNextLine() {
        HashMap<String, String> AsciiArtValueDict = Exec.parseConfigFile(Exec.readFile("src/main/resources/DefaultNumbersConfig.txt"));
        assertEquals("791630536" ,Exec.parseNextLine(new ArrayList<String>(Arrays.asList(" _  _     _  _  _  _  _  _ ","  ||_|  ||_  _|| ||_  _||_ ","  | _|  ||_| _||_| _| _||_|")), AsciiArtValueDict));
    }
}