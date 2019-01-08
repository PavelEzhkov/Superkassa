package ru.superkassa;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start!");
        new FlightCreate("input/input.json", "output/output.json");
        System.out.println("Finish!");
    }
}
