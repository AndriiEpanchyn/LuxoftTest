package main.java;

import java.util.HashMap;
import java.util.StringTokenizer;

public class LineStat {
    private String line;
    private String longestWord;
    private String shortestWord;
    private int lineLength;
    private float averageLength;
    private int duplicates;

    /**
     * Convert String into LineStat object
     */
    public static LineStat convertString(String string) {

        //Create array of words from read string
        String delimeters = " «».,!?;:\uFEFF";
        StringTokenizer st = new StringTokenizer(string, delimeters, false);
        int size = st.countTokens();
        String[] arr = new String[size];
        if (size == 0) {
            return new LineStat();
        }
        int index = 0;
        while (st.hasMoreTokens()) {
            arr[index] = (st.nextToken()).toUpperCase();
            index++;
        }

        //evaluate longest, shortest and average
        String longest = arr[0];
        String shortest = arr[0];
        float average = 0.0f;

        for (String s : arr) {
            if (longest.length() < s.length()) {
                longest = s;
            }
            if (shortest.length() > s.length()) {
                shortest = s;
            }
            average += s.length();
        }
        average = (float) ((float) Math.round(100.0 * average / arr.length) / 100.0);

        //evaluate duplicates
        int duplicates = 0;
        HashMap<String, Integer> hashDuplicates = new HashMap<>();

        if (arr.length > 1) {
            for (String a : arr) {
                if (hashDuplicates.containsKey(a)) {
                    hashDuplicates.put(a, (hashDuplicates.get(a) + 1));
                } else {
                    hashDuplicates.put(a, 1);
                }
            }
        }

        for (HashMap.Entry<String, Integer> a : hashDuplicates.entrySet()) {
            if (a.getValue() > 1) {
                duplicates += a.getValue();
            }
        }

        //return instance of LineStat
        LineStat answer = new LineStat(string, longest, shortest, string.length(), average, duplicates);
        return answer;
    }  //End of convertString

    public LineStat() {
    }

    public LineStat(String line, String longestWord, String shortestWord, int lineLength, float averageLength,
                    int duplicates) {
        this.line = line;
        this.longestWord = longestWord;
        this.shortestWord = shortestWord;
        this.lineLength = lineLength;
        this.averageLength = averageLength;
        this.duplicates = duplicates;
    }

    public String getLine() {
        return line;
    }

    public String getLongestWord() {
        return longestWord;
    }

    public String getShortestWord() {
        return shortestWord;
    }

    public int getLineLength() {
        return lineLength;
    }

    public float getAverageLength() {
        return averageLength;
    }

    public int getDuplicates() {
        return duplicates;
    }

    @Override
    public String toString() {
        return "LineStat{" +
                "line= " + line +
                ", longestWord= " + longestWord +
                ", shortestWord= " + shortestWord +
                ", lineLength= " + lineLength +
                ", averageLength= " + averageLength +
                ", duplicates= " + duplicates +
                '}';
    }
}
