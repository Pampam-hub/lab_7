package ru.itmo.lab.service;

public class OutputMessage {
    public static void printErrorMessage(String message) {
        System.out.println("\u001B[31m" + "Error: " + message + "\u001B[0m");
    }

    public static void printSuccessfulMessage(String message) {
        System.out.println("\u001B[34m" + message + "\u001B[0m");
    }
}
