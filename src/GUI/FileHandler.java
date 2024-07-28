package GUI;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    public static ArrayList<String[]> readIntoTSV(String filename) {
        ArrayList<String[]> contents = new ArrayList<>();
        ArrayList<String> rawFileContents = readFromFile(filename);

        for (String entry : rawFileContents) {
            // Ignore comments. Not sure if # is better, but I'm using // because java.
            if (entry.startsWith("//")) continue;

            String[] record = entry.split("\t");
            contents.add(record);
        }

        return contents;
    }

    public static ArrayList<String> readFromFile(String fileName) {
        ArrayList<String> fileContents = new ArrayList<>();
        try (
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr)
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
