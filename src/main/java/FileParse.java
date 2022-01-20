package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParse {
    private static final ArrayList<LineStat> fileStat = new ArrayList<>();

    /**
     * Read file
     */
    public static ArrayList<LineStat> readFile(File f) {
        FileReader newReader = null;
        ArrayList<LineStat> answer = new ArrayList<>();
        try {
            newReader = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner newScan = new Scanner(newReader);
        while (newScan.hasNextLine()) {
            String currentString = newScan.nextLine();
            if (currentString.length() > 0) {
                answer.add(LineStat.convertString(currentString));
            }
            try {
                newReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return answer;
    } //End of readFile
}//End of Class FileParse
