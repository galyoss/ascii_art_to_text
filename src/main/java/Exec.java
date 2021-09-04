import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Exec {

    private static HashMap<String, String> AsciiArtValueDict = new HashMap<>();

    //main func, run this with relevant file to convert to text
    // check inputs -> clean enters -> parse each time a "block" i.e 3 lines and insert them to the new file
    public static void main(String[] args) throws Exception {
        if (args.length != 1 && args.length != 2) {
            throw new Exception("Number of paramets does not match: expected 1/2 but received" + args.length);
        }
        String filePath = args[0];
        if (args.length == 2) { //received no-default config text file
            String configFilePath = args[1];
            parseConfigFile(configFilePath);
        } else {
            parseConfigFile("src/main/resources/DefaultNumbersConfig.txt");
        }
        ArrayList<String> fileData = readFile(filePath);
        cleanExcessEnters(fileData);
        if (!checkTextFile(fileData)) {
            throw new Exception("Text file is not in required format");
        }

        try {
            FileWriter myWriter = new FileWriter(filePath.substring(0, filePath.length() - 4) + "_TEXT.txt");
            while (!fileData.isEmpty()) {
                String nextLine = parseNextLine(fileData);
                myWriter.write(nextLine + "\n");
            }
            myWriter.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    private static void parseConfigFile(String configFilePath) {
        ArrayList<String> configInfo = readFile(configFilePath);
        for (int i = 0; i < configInfo.size() - 3; i += 4) {
            String val = configInfo.get(i);
            String key = configInfo.get(i + 1) + configInfo.get(i + 2) + configInfo.get(i + 3);
            AsciiArtValueDict.put(key, val);
        }
    }

    private static boolean checkTextFile(ArrayList<String> fileData) {
        if (fileData.size() % 3 != 0) //check num of lines matches to block size (3 lines for each block)
            return false;
        for (String s : fileData)
            if (s.length() != 27)
                return false;
        return true;
    }

    private static void cleanExcessEnters(ArrayList<String> fileData) {
        while (fileData.contains(""))
            fileData.remove("");
    }

    private static String parseNextLine(ArrayList<String> fileData) {
        String[] lines = new String[]{fileData.remove(0), fileData.remove(0), fileData.remove(0)};
        StringBuilder stringBuilder = new StringBuilder();
        while (lines[0].length() > 0)
            stringBuilder.append(getNextChar(lines));
        String res = stringBuilder.toString();
        return res.contains("?") ? res + " ILLEGAL" : res;
    }

    private static String getNextChar(String[] lines) {
        try {
            String letter = lines[0].substring(0, 3) + lines[1].substring(0, 3) + lines[2].substring(0, 3);
            for (int i = 0; i < 3; i++)
                lines[i] = lines[i].substring(3);
            String res = AsciiArtValueDict.get(letter);
            return res != null ? res : "?";
        } catch (Exception e) {
            System.out.println("error in processing text file");
            System.exit(1);
        }
        return "";
    }

    public static ArrayList<String> readFile(String path) {
        ArrayList<String> res = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                res.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read file. Please make sure file path is correct");
            System.exit(1);
        }
        return res;
    }
}
