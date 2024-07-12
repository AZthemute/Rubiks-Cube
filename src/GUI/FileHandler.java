package GUI;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    public static ArrayList<String[]> readIntoCSV(String filename) {
        ArrayList<String[]> contents = new ArrayList<>();
        ArrayList<String> rawFileContents = readFromFile(filename);

        for (int i = 0; i < rawFileContents.size(); i++) {
            String[] record = rawFileContents.get(i).split(",");
            System.out.println(record);
            contents.add(record);
        }

        return contents;
    }

    public static ArrayList<String> readFromFile(String fileName) {
        // using best-practice exception handling
        ArrayList<String> fileContents = new ArrayList<>();
        try (
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line = br.readLine();
            while (line != null) {
                fileContents.add(line);
                line = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;
    }
}
