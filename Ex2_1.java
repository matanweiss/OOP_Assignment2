import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ex2_1 {

    /**
     * This method creates text files with the String "Ahalan Shalom" in a random
     * number of lines
     * 
     * @param n     The number of files to be created
     * @param seed  The initial value of the internal state of the pseudorandom
     *              number generator
     * @param bound The maximum number of lines for each file
     * @return An array of Strings with the created file names
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] result = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(bound);
            String fileName = "file_" + String.valueOf(i + 1);
            String s = "Ahalan Shalom\n";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                s = s.repeat(x);
                writer.write(s);
                result[i] = fileName;
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * This method counts the number of lines of a text file
     * 
     * @param fileName The file's name
     * @return An Integer representing the result
     */
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

    /**
     * This method counts the number of lines of multiple files
     * 
     * @param fileNames An array of Strings representing the file names
     * @return An Integer representing the result
     */
    public static int getNumOfLines(String[] fileNames) {
        int result = 0;
        for (String fileName : fileNames) {
            result += countLines(fileName);
        }
        return result;
    }

    /**
     * A Thread that counts the number of lines of a file
     */
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

    /**
     * This method counts the number of lines of multiple files using
     * NumLinesThreads
     * 
     * @param An array of Strings representing the file names
     * @return An Integer representing the result
     */
    public static int getNumOfLinesThreads(String[] fileNames) {
        int result = 0;
        NumLinesThread[] threads = new NumLinesThread[fileNames.length];
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

    /**
     * A Callable that counts the number of lines of a file
     */
    public static class NumLinesCallable implements Callable<Integer> {

        private String fileName;

        public NumLinesCallable(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public Integer call() {
            return countLines(fileName);
        }

    }

    /**
     * This method counts the number of lines of multiple files using
     * ThreadPoolExecutor
     * 
     * @param An array of Strings representing the file names
     * @return An Integer representing the result
     */
    public static int getNumOfLinesThreadPool(String[] fileNames) {
        int result = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();
        for (String fileName : fileNames) {
            list.add(threadPool.submit(new NumLinesCallable(fileName)));
        }
        for (Future<Integer> future : list) {
            try {
                result += future.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        threadPool.shutdown();
        return result;
    }

    public static void main(String[] args) {
        String[] a = createTextFiles(20, 1, 10);
        Instant start = Instant.now();
        System.out.println(getNumOfLines(a));
        Instant finish = Instant.now();
        System.out.println("getNumOfLines: " + Duration.between(start, finish).toMillis());
        start = Instant.now();
        System.out.println(getNumOfLinesThreads(a));
        finish = Instant.now();
        System.out.println("getNumOfLinesThreads: " + Duration.between(start, finish).toMillis());
        start = Instant.now();
        System.out.println(getNumOfLinesThreadPool(a));
        finish = Instant.now();
        System.out.println("getNumOfLinesThreadPool: " + Duration.between(start, finish).toMillis());
    }
}