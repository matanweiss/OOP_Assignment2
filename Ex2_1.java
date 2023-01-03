import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] result = new String[n];
        Random rand = new Random();
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
            try {
                threads[i].join();
                result += threads[i].getLines();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

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
        String[] a = createTextFiles(3, 1, 10);
        System.out.println(Arrays.toString(a));
        System.out.println(getNumOfLines(a));
        System.out.println(getNumOfLinesThreads(a));
        System.out.println(getNumOfLinesThreadPool(a));
    }
}