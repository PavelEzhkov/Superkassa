package ru.superkassa;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.LinkedHashSet;


public class GsonExecutor {
    private String[][] strings;
    private LinkedHashSet<String[]> outStrings = new LinkedHashSet<>();
    private String outputDir;
    private int maxInt;

    Gson gson = new Gson();

    public String[][] loadGson(String fileName, String outputDir) {
        this.outputDir = outputDir;
        try (JsonReader reader = new JsonReader(new FileReader(fileName))) {
            strings = gson.fromJson(reader, String[][].class);
        } catch (FileNotFoundException e) {
            System.err.println("File " + fileName + " not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        maxInt = (int) (Math.pow(2, strings[0].length) - 1);
        return strings;
    }


    public void saveGson(String[] string, int i) {
        String[] originalString = string;
        for (int j = i; j < strings.length; j++) {
            string = combineStrings(string, strings[j]);
            if (intFromString(string) == maxInt) {
                outStrings.add(string);
                string = originalString;
            } else if (string != originalString)
                saveGson(string, j++);
        }
    }

    private String[] combineStrings(String[] string1, String[] string2) {
        String[] resultString = new String[strings[0].length];
        for (int i = 0; i < strings[0].length; i++) {
            if (string1[i] != null && string2[i] != null)
                return string1;
            else if (string1[i] == null)
                resultString[i] = string2[i];
            else resultString[i] = string1[i];
        }
        return resultString;
    }

    private void createJson() {
        File file = new File(outputDir);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeJson() {
        createJson();

        try (Writer writer = new FileWriter(outputDir)) {
            gson.toJson(outStrings, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int intFromString(String[] strings) {
        int value = 0;
        for (int j = 0; j < strings.length; j++) {
            if (strings[j] != null)
                value += Math.pow(2, strings.length - 1 - j);
        }
        return value;
    }
}
