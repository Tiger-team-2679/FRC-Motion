package org.team2679.motion.util;

import java.io.*;

/**
 * used to export data to a file
 */
public class Exporter {

    private char separator;
    private File file;
    private FileOutputStream fileStream;
    private PrintStream filePrint;

    /**
     * creates a new data exporter
     * @param separator the line separator between arguments
     * @param Directory the file directory
     * @param Name the name of the exporter
     */
    public Exporter(char separator, String Directory, String Name){
        this.separator = separator;
        this.file = new File(Directory,  Name + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        filePrint = new PrintStream(fileStream);
    }

    /**
     * adds args to the exported file, with the separator between them
     * @param args args to write
     */
    public void exportPoint(double... args){
        for (int i = 0; i < args.length; i++) {
            filePrint.print(args[i]);
            if(i != args.length - 1){
                filePrint.print(separator);
            }
        }
        filePrint.println("");
    }

    /**
     * adds the entire array to the exported file
     * @param array the array to write
     */
    public void exportArray(double[][] array){
        for (int i = 0; i < array.length; i++) {
            exportPoint(array[i]);
        }
    }
}
