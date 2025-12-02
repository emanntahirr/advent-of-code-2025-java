import java.io.File;
import java.util.Scanner;

public class Day02 {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.println("Which part do you want to run? (1 or 2)");
        int choice = input.nextInt();

        if (choice == 1) {
            solvePart1();
        } else {
            solvePart2();
        }
    }

    public static void solvePart1() throws Exception {
        Scanner sc = new Scanner(new File("day2.txt"));
        String line = sc.nextLine().trim();
        sc.close();

        String[] ranges = line.split(",");

        long sum = 0;

        for (String r : ranges) {
            if (r.isEmpty()) continue;

            String[] parts = r.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            for (long id = start; id <= end; id++) {
                if (isRepeatedExactlyTwice(id)) {
                    sum += id;
                }
            }
        }

        System.out.println("Part 1 answer: " + sum);
    }

    public static void solvePart2() throws Exception {
        Scanner sc = new Scanner(new File("day2.txt"));
        String line = sc.nextLine().trim();
        sc.close();

        String[] ranges = line.split(",");

        long sum = 0;

        for (String r : ranges) {
            if (r.isEmpty()) continue;

            String[] parts = r.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            for (long id = start; id <= end; id++) {
                if (isRepeatedPatternAtLeastTwice(id)) {
                    sum += id;
                }
            }
        }

        System.out.println("Part 2 answer: " + sum);
    }

    private static boolean isRepeatedExactlyTwice(long id) {
        String s = Long.toString(id);
        int n = s.length();

        if (n % 2 != 0) return false;

        String first = s.substring(0, n / 2);
        String second = s.substring(n / 2);

        return first.equals(second);
    }

    private static boolean isRepeatedPatternAtLeastTwice(long id) {
        String s = Long.toString(id);
        int n = s.length();

        for (int len = 1; len <= n / 2; len++) {
            String chunk = s.substring(0, len);

            StringBuilder sb = new StringBuilder();
            while (sb.length() < n) {
                sb.append(chunk);
            }

            if (sb.toString().equals(s)) {
                return true;
            }
        }

        return false;
    }
}