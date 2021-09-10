import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.apache.commons.io.FileUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ExecTest {

    @Test
    void main() throws Exception {
        /*
        Test scenarios:
        - File doesnt exist
        - File is not valid - lines aren't 27 long
        - File is not valid - a line is missing
        - File is valid, including readable chars only
        - File is valid, including both readable and non-readable chars

         */

        Assertions.assertThrows(Exception.class , ()-> {
            Exec.main(new String[]{"nofile"});
        });

        Assertions.assertThrows(Exception.class, ()-> {
            Exec.main(new String[]{"src/test/TestFiles/TestFile2.txt"});
        });

        Assertions.assertThrows(Exception.class, ()-> {
            Exec.main(new String[]{"src/test/TestFiles/TestFile3.txt"});
        });

        Exec.main(new String[]{"src/test/TestFiles/TestFile1.txt"});
        File expectedFile1 = new File("src/test/TestFiles/expectedResultFile1.txt");
        File fileResult1 = new File("src/test/TestFiles/TestFile1_OUTPUT.txt");
        assertEquals(FileUtils.readFileToString(expectedFile1, "utf-8"), FileUtils.readFileToString(fileResult1, "utf-8"));


        Exec.main(new String[]{"src/test/TestFiles/TestFile4.txt"});
        File expectedFile2 = new File("src/test/TestFiles/expectedResultFile2.txt");
        File fileResult2 = new File("src/test/TestFiles/TestFile4_OUTPUT.txt");
        assertEquals(FileUtils.readFileToString(expectedFile2, "utf-8"), FileUtils.readFileToString(fileResult2, "utf-8"));


        try{
        fileResult1.delete();
        fileResult2.delete();

        }catch (Exception c){
            System.out.println("couldn't auto delete output files for test - please remove manually");
        }
    }

}