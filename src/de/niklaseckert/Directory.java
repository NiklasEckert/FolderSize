package de.niklaseckert;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.stream.Collectors;

public class Directory {

    String path;
    private long size;
    private List<Directory> subDirectories;

    Directory(String path) throws IOException {
        this.path = FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
        this.subDirectories = new ArrayList<>();
        this.size = 0;
        this.findSubDirectories();
        this.calculateSize();
    }

    public void draw(SortingType sortingType, int maxDepth) {
        draw(sortingType, 0, 0, maxDepth, "");
    }

    private void draw(SortingType sortingType, int printOffset, int currDepth, int maxDepth, String lastPath) {
        if (maxDepth > 0 && currDepth >= maxDepth)
            return;

        String sizeString;
        if (this.size < 1000) {
            sizeString = String.format("( %d Byte )", this.size);
        } else if (this.size < 1e+6) {
            sizeString = String.format("( %.2f KB )", ((double) this.size) / Math.pow(1000, 1));
        } else if (this.size < 1e+9) {
            sizeString = String.format("( %.2f MB )", ((double) this.size) / Math.pow(1000, 2));
        } else if (this.size < 1e+12) {
            sizeString = String.format("( %.2f GB )", ((double) this.size) / Math.pow(1000, 3));
        } else if (this.size < 1e+15) {
            sizeString = String.format("( %.2f TB )", ((double) this.size) / Math.pow(1000, 4));
        } else {
            sizeString = String.format("( %.2f PT )", ((double) this.size) / Math.pow(1000, 5));
        }

        String formatString = printOffset == 0 ? "%1s|-- %s " + sizeString + "%n" : "%" + printOffset + "s|-- %s " + sizeString + " %n";
        System.out.printf(formatString, " ", path.replace(lastPath, ""));

        List<Directory> sortedDirectories = new ArrayList<>();
        switch (sortingType) {
            case PATH -> sortedDirectories = subDirectories.stream().sorted(Comparator.comparing(Directory::getPath)).collect(Collectors.toList());
            case SIZE -> sortedDirectories = subDirectories.stream().sorted(Comparator.comparingLong(Directory::getSize).reversed()).collect(Collectors.toList());
        }

        for (Directory dir : sortedDirectories) {
            dir.draw(sortingType, printOffset + 4, currDepth + 1, maxDepth, this.path);
        }
    }

    private void calculateSize() {
        File dir = new File(path);

        try {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile()) {
                    size += file.length();
                }
            }
        } catch (NullPointerException e) {

        }

        for (Directory sub : subDirectories) {
            size += sub.getSize();
        }
    }

    private void findSubDirectories() throws IOException {
        File dir = new File(path);

        try {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file != null) {
                    if (file.isDirectory()) {
                        subDirectories.add(new Directory(file.getCanonicalPath()));
                    }
                }
            }
        } catch (NullPointerException e) {

        }
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public List<Directory> getSubDirectories() {
        return subDirectories;
    }
}
