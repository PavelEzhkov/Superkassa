package ru.superkassa;

public class FlightCreate {

    public FlightCreate(String input, String output) {

        GsonExecutor executor = new GsonExecutor();

        String[][] strings = executor.loadGson(input, output);

        System.out.println("File loaded from " + input);

        for (int i = 0; i < strings.length; i++) {
            executor.saveGson(strings[i], i);
        }

        executor.writeJson();

        System.out.println("File saved in " + output);
    }


}
