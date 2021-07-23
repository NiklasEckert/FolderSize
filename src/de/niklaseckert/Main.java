package de.niklaseckert;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        boolean onlyHelp = false;
        String path;
        SortingType sortingType = SortingType.PATH;
        int depth = 3;

        int i = 0;
        while (i < args.length) {
            switch (args[i]) {
                case "--help" -> {
                    printHelp();
                    onlyHelp = true;
                }
                case "-t" -> sortingType = args[++i].equals("SIZE") ? SortingType.SIZE : SortingType.PATH;
                case "-d" -> depth = Integer.parseInt(args[++i]);
            }
            i++;
        }
        path = args[args.length - 1];

        if (!onlyHelp) {
            try {
                Directory dir = new Directory(path);
                dir.draw(sortingType, depth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
