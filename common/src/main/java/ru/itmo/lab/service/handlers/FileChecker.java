package ru.itmo.lab.service.handlers;

import java.io.File;
import java.io.FileNotFoundException;

public class FileChecker {

    public static void checkFile(String filePath) throws FileNotFoundException {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("file not found");
        } else if (file.isDirectory()) {
            throw new FileNotFoundException("it isn't a file, it's a directory");
        } else if (!file.canRead()) {
            throw new FileNotFoundException("no read  access");
        } else if (!file.canWrite()) {
            throw new FileNotFoundException("no write access");
        }
    }

}
