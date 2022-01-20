package main.java;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {
    private static String filePath = "";

    public static void main(String[] args) throws SQLException {

        /* Get initial file name*/
        if (args.length > 0) filePath = args[0];

        /* Store file data into arraylist */
        ArrayList<LineStat> toDb = FileParse.readFile(checkedFile(filePath));

        /*  Connect to database and save there arraylist*/
        insertLinesMethod(toDb); // Insert data into db
        insertTotalMethod(toDb);//  insert totals into db

        /*Choose next action (get total or line statistics or quit*/
        chooser();
    }  // End of Main

    /**
     * Ensure that will be processed correct file
     */
    private static File checkedFile(String f) {
        File file = new File(f);
        boolean isChecked = false;
        do {
            if (file.isFile()) {
                isChecked = true;
            } else {
                System.out.println("There wasn't correct path entered in command line \n" +
                        "Input legal file name to inspect custom file, or input  \\\"Quit\\\" to exit\".");
                Scanner voidscan = new Scanner(System.in);
                filePath = voidscan.nextLine();
                if (filePath.toUpperCase().equals("QUIT")) {
                    System.out.println("Program stopped.");
                    System.exit(0);
                } else {
                    file = new File(filePath);
                }
            }
        } while (!isChecked);
        return file;
    } //End of checkedFile

    /**
     * Choose options of data you want to see
     */
    private static void chooser() throws SQLException {
        String myString = "";
        while (!myString.equals("QUIT")) {
            System.out.println("File processed.\n" +
                    "Enter 1 to get line statistics.\n" +
                    "Enter 2 to get total statistics.\n" +
                    "Enter QUIT to exit ");
            Scanner voidscan = new Scanner(System.in);
            myString = voidscan.nextLine().toUpperCase(Locale.ROOT);
            switch (myString) {
                case "1": {
                    selectAllMethod();
                    break;
                }
                case "2": {
                    selectTotalMethod();
                    break;
                }
                case "QUIT": {
                    System.exit(0);
                }
            }
        }
    } // End of chooser

    /**
     * Connect to database and insert there data
     */
    private static void insertLinesMethod(ArrayList<LineStat> toDb) throws SQLException {

        JDBCConnector myconnector = new JDBCConnector();
        myconnector.insert("TRUNCATE TABLE textstatistics.linestatistics;");
        System.out.println("Start insert Line values into db");
        for (LineStat lineStat : toDb) {
            String insertQuery = "INSERT INTO `textstatistics`.`linestatistics` " +
                    "(`line`, `longestWord`, `shortestWord`, `lineLength`, `averageLength`, `duplicates`) VALUES ('" +
                    lineStat.getLine() + "','" + lineStat.getLongestWord() + "','" + lineStat.getShortestWord() + "','"
                    + lineStat.getLineLength() + "','" + lineStat.getAverageLength() + "','" + lineStat.getDuplicates() + "');";

            myconnector.insert(insertQuery);
        }
        myconnector.closeConnection();
        System.out.println("Insert completed");
    } //End of insertLinesMethod

    private static void insertTotalMethod(ArrayList<LineStat> toDb) throws SQLException {
        StringBuilder totalLine = new StringBuilder();
        //file name we took from the filePath
        int lines = 0;//Total lines quantity
        String longestTotal = toDb.get(0).getLongestWord();//total longest word
        String shortestTotal = toDb.get(0).getShortestWord();//total shortest word
        int totalLength = 0;// total line length
        float totalAverage = 0;// total average size
        int totalDuplicates = 0;// total quantity of duplicates (from all lines)
        HashMap<String, Integer> hashDuplicates = new HashMap<>();//temp help to calculate totalDupplicates

        /** generate totals */
        //calculates lines, totalLength, totalLongest, totalShortest
        for (LineStat a : toDb) {
            lines++;
            totalLength += a.getLineLength();
            totalLine.append(a.getLine()).append(" ");
            if (longestTotal.length() < a.getLongestWord().length()) {
                longestTotal = a.getLongestWord();
            }
            if (shortestTotal.length() > a.getShortestWord().length()) {
                shortestTotal = a.getShortestWord();
            }
        }

        //Generate total wordArray and calculates totalAverage, totalDuplicates
        String delimeters = " «».,!?;:\uFEFF";
        StringTokenizer st = new StringTokenizer(totalLine.toString(), delimeters, false);
        int size = st.countTokens();
        String[] arr = new String[size];
        if (size > 0) {
            int index = 0;
            while (st.hasMoreTokens()) {
                arr[index] = (st.nextToken()).toUpperCase();
                totalAverage += arr[index].length();
                if (hashDuplicates.containsKey(arr[index])) {
                    hashDuplicates.put(arr[index], (hashDuplicates.get(arr[index]) + 1));
                } else {
                    hashDuplicates.put(arr[index], 1);
                }
                index++;
            }
            totalAverage = (float) ((float) Math.round(100.0 * totalAverage / arr.length) / 100.0);

            // Calculates totalDuplicates quantity
            if (arr.length > 1) {
                for (HashMap.Entry<String, Integer> a : hashDuplicates.entrySet()) {
                    if (a.getValue() > 1) {
                        totalDuplicates += a.getValue();
                    }
                }
            }
        }

        /** Insert Query */
        JDBCConnector myconnector = new JDBCConnector();
        myconnector.insert("TRUNCATE TABLE textstatistics.filestatistics;");
        String insertQuery = String.format("INSERT INTO `textstatistics`.`filestatistics` " +
                "(`file`, `lines`, `longestWord`, `shortestWord`, `lineLength`, `averageLength`, `duplicates`) VALUES ('" +
                filePath + "','" + lines + "','" + longestTotal + "','" + shortestTotal + "','" + totalLength + "','" + totalAverage + "','" + totalDuplicates + "');");

        myconnector.insert(insertQuery);
    }//End of insertTotalMethod

    /**
     * Check line data statistics
     */
    private static void selectAllMethod() throws SQLException {

        JDBCConnector myconnector = new JDBCConnector();
        String selectQuery = "SELECT * FROM `textstatistics`.`linestatistics`";
        ResultSet myresult = myconnector.select(selectQuery);

        System.out.println("\n\n Trying to get line statistics from db \n\n" +
                "line № |      line      | longestWord| shortestWord | lineLength | averageLength | duplicates\n");

        while (myresult.next()) {
            System.out.println(
                    myresult.getRow() + " | " +
                            myresult.getString("Line") + " | " +
                            myresult.getString("longestWord") + " | " +
                            myresult.getString("shortestWord") + " | " +
                            myresult.getInt("lineLength") + " | " +
                            myresult.getFloat("averageLength") + " | " +
                            myresult.getInt("duplicates") + " |"
            );
        }
        myconnector.closeConnection();
    }//End of selectAllMethod

    /* Check total data statistics */
    private static void selectTotalMethod() throws SQLException {
        JDBCConnector myconnector = new JDBCConnector();
        String selectQuery = "SELECT * FROM `textstatistics`.`filestatistics` ";
        ResultSet myresult = myconnector.select(selectQuery);

        System.out.println("\nTrying to get total file statistics from db \n");

        while (myresult.next()) {
            System.out.println("File name = " + myresult.getString("file") +
                    "\nTotal lines = " + myresult.getString("Lines") +
                    "\nLongest word = " + myresult.getString("longestWord") +
                    "\nShortest word = " + myresult.getString("shortestWord") +
                    "\nTotal Length = " + myresult.getInt("lineLength") +
                    "\nAverage word length = " + myresult.getFloat("averageLength") +
                    "\nQuantity of duplicates = " + myresult.getInt("duplicates")
            );
        }
        myconnector.closeConnection();
    }// End of selectTotalMethod
} // End of Main Class
