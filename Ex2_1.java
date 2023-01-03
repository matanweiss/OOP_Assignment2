import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;

public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound) {
        String s = "Ahalan Shalom\n";
        String[] result = new String[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(bound);
            try {
                String fileName = "file_" + String.valueOf(i + 1);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                for (int j = 0; j < x; j++) {
                    writer.append(s);
                }
                result[i] = fileName;
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    public static int countLines(String fileName) {
        int result = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (reader.readLine() != null)
                result++;
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static int getNumOfLines(String[] fileNames) {
        int result = 0;
        for (String fileName : fileNames) {
            result += countLines(fileName);
        }
        return result;
    }

    public static class NumLinesThread extends Thread {
        private String fileName;
        private int lines;

        public NumLinesThread(String fileName) {
            this.fileName = fileName;
        }

        public void run() {
            lines = countLines(fileName);
        }

        public int getLines() {
            return lines;
        }
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        int result = 0;
        NumLinesThread[] threads = new NumLinesThread[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            threads[i] = new NumLinesThread(fileNames[i]);
            threads[i].start();
        }
        for (int i = 0; i < fileNames.length; i++) {
            threads[i] = new NumLinesThread(fileNames[i]);
            threads[i].start();
        }
        for (int i = 0; i < fileNames.length; i++) {
            try {
                threads[i].join();
                result += threads[i].getLines();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[] a = createTextFiles(3, 1, 10);
        System.out.println(Arrays.toString(a));
        System.out.println(getNumOfLines(a));
        System.out.println(getNumOfLinesThreads(a));
    }
}