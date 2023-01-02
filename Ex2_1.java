import java.io.BufferedWriter;
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
                BufferedWriter writer = new BufferedWriter(new FileWriter("file_" + String.valueOf(i + 1), true));
                for (int j = 0; j < x; j++) {
                    writer.append(s);
                }
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            result[i] = String.valueOf(x);
        }
        return result;
    }

    public static void main(String[] args) {
        String[] a = createTextFiles(3, 1, 10);
        System.out.println(Arrays.toString(a));
    }
}