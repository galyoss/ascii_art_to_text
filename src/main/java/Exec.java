import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Exec {

    //main func, run this with relevant file to convert to text
    // check inputs -> clean enters -> parse each time a "block" i.e 3 lines and insert them to the new file

    public static void main(String[] args) throws Exception {
        if (args.length != 1 && args.length != 2) {
            throw new Exception("Number of parameters does not match: expected 1/2 but received" + args.length);
        }
        String filePath = args[0];
        ArrayList<String> fileData = readFile(filePath);
        cleanExcessEnters(fileData);
        if (!checkTextFile(fileData)) {
            throw new Exception("Text file is not in required format");
        }
        ArrayList<String> configFileData;
        if (args.length == 2) { //received no-default config text file
            String configFilePath = args[1];
            configFileData = readFile(configFilePath);
        }
        else {
            configFileData = readFile("src/main/resources/DefaultNumbersConfig.txt"); //default configFile
        }
        HashMap<String, String> AsciiArtValueDict = parseConfigFile(configFileData);

        try {
            FileWriter outputFileWriter = new FileWriter(filePath.substring(0, filePath.length() - 4) + "_OUTPUT.txt");
            while (!fileData.isEmpty()) {
                String nextLine = parseNextLine(fileData, AsciiArtValueDict);
                outputFileWriter.write(nextLine + "\n");
            }
            outputFileWriter.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }


    private static HashMap<String, String> parseConfigFile(ArrayList<String> configInfo) {
        //This method gets as input path to config file,reads the file and returns a dictionary from ascii-art to value

        HashMap<String, String> dict = new HashMap<>();
        for (int i = 0; i < configInfo.size() - 3; i += 4) {
            String val = configInfo.get(i);
            String key = configInfo.get(i + 1) + configInfo.get(i + 2) + configInfo.get(i + 3);
            dict.put(key, val);
        }
        return dict;
    }

    private static boolean checkTextFile(ArrayList<String> fileData) {
        //This method goes over the files and checks for any structure problems or contract violations. return True if test passed.
        for (int i=0;i<fileData.size();i++)
            if (fileData.get(i).length()!=27)
                return false;
        return true;
    }

    private static void cleanExcessEnters(ArrayList<String> fileData) {
        //This function receives the file data and removes the empty strings (matching to the empty lines in the txt file)
        while (fileData.contains(""))
            fileData.remove("");
    }

    private static String parseNextLine(ArrayList<String> fileData, HashMap<String, String> AsciiArtValueDict) {
        //This method parses the next 3 lines (i.e Ascii-art "block") and returns a matching string.
        String[] lines = new String[]{fileData.remove(0), fileData.remove(0), fileData.remove(0)};
        StringBuilder stringBuilder = new StringBuilder();
        while (lines[0].length() > 0)
            stringBuilder.append(getNextChar(lines, AsciiArtValueDict));
        String res = stringBuilder.toString();
        return res.contains("?") ? res + " ILLEGAL" : res;
    }

    private static String getNextChar(String[] lines, HashMap<String, String> AsciiArtValueDict) {
        //This method takes the 3 line strings, removes the first 3 chars of each line, and returns the matching character they represent in ascii-art.
            String letter = lines[0].substring(0, 3) + lines[1].substring(0, 3) + lines[2].substring(0, 3);
            for (int i = 0; i < 3; i++)
                lines[i] = lines[i].substring(3);
            String res = AsciiArtValueDict.get(letter);
            return res != null ? res : "?";

    }

    private static ArrayList<String> readFile(String path) throws Exception{
        //This method receives path to txt file, and returns an arraylist of strings, each string represents a line in the txt file.
        ArrayList<String> res = new ArrayList<>();
        File file = new File(path);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            res.add(data);
        }
        myReader.close();
        return res;
    }
}
