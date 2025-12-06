import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Day05 {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        System.out.println("Which part do you want to run? (1 or 2)");
        int choice = input.nextInt();
        input.close();

        if (choice == 1) {
            solvePart1();
        } else {
            solvePart2();
        }
    }

    public static void solvePart1() throws Exception {
        Scanner sc = new Scanner(new File("day5.txt"));

        ArrayList<long[]> ranges = new ArrayList<>();

        // Read ranges until blank line
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) break;

            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            ranges.add(new long[]{start, end});
        }

        int freshCount = 0;


        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.matches("\\d+")) continue;

            long id = Long.parseLong(line);

            boolean isFresh = false;
            for (long[] range : ranges) {
                if (id >= range[0] && id <= range[1]) {
                    isFresh = true;
                    break;
                }
            }

            if (isFresh) freshCount++;
        }

        sc.close();
        System.out.println("Part 1 fresh IDs: " + freshCount);
    }

    public static void solvePart2() throws Exception {
        Scanner sc = new Scanner(new File("day5.txt"));

        ArrayList<long[]> ranges = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) break;

            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            ranges.add(new long[]{start, end});
        }

        sc.close();

        Collections.sort(ranges, Comparator.comparingLong(a -> a[0]));

        long totalFresh = 0;

        long currentStart = ranges.get(0)[0];
        long currentEnd = ranges.get(0)[1];

        for (int i = 1; i < ranges.size(); i++) {
            long[] next = ranges.get(i);
            long nextStart = next[0];
            long nextEnd = next[1];

            if (nextStart <= currentEnd) {
                currentEnd = Math.max(currentEnd, nextEnd);
            } else {
                totalFresh += (currentEnd - currentStart + 1);
                currentStart = nextStart;
                currentEnd = nextEnd;
            }
        }

        totalFresh += (currentEnd - currentStart + 1);

        System.out.println("Part 2 total fresh IDs across all ranges: " + totalFresh);
    }
}
