package de.niklaseckert;

public class Main {

    public static void main(String[] args) {

    }

    private static void printHelp() {
        System.out.println("FolderSize Help:");
        System.out.println("Run command: java -jar folderSize.jar [OPTIONS] path");
        System.out.println();
        System.out.println("    --help  Show help");
        System.out.println("    -d      Set display depth (Default = 3)");
        System.out.println("    -t      Set Sorting Type (\"PATH\" (Default), \"SIZE\")");
    }
}
