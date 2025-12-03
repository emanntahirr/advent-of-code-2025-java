import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Day03 {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.println("Which part do you want to run? (1 or 2)");
        int choice = input.nextInt();

        if (choice == 1) {
            solvePart1();
        } else {
            solvePart2();
        }

        input.close();
    }

    public static void solvePart1() throws Exception {
        Scanner sc = new Scanner(new File("day3.txt"));

        long totalSum = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            int maxForThisLine = 0;

            for (int i = 0; i < line.length(); i++) {
                for (int j = i + 1; j < line.length(); j++) {

                    int first = line.charAt(i) - '0';
                    int second = line.charAt(j) - '0';

                    int value = first * 10 + second;

                    if (value > maxForThisLine) {
                        maxForThisLine = value;
                    }
                }
            }

            totalSum += maxForThisLine;
        }

        sc.close();
        System.out.println("Part 1: " + totalSum);
    }

    public static void solvePart2() throws Exception {
        Scanner sc = new Scanner(new File("day3.txt"));

        long totalSum = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            long best = maxTwelveDigitJoltage(line);
            totalSum += best;
        }

        sc.close();
        System.out.println("Part 2 total output joltage: " + totalSum);
    }
    
    private static long maxTwelveDigitJoltage(String line) {
        int keep = 12;
        int toRemove = line.length() - keep;

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : line.toCharArray()) {
            while (!stack.isEmpty() && toRemove > 0 && stack.peekLast() < c) {
                stack.removeLast();
                toRemove--;
            }
            stack.addLast(c);
        }

        StringBuilder result = new StringBuilder();
        int count = 0;

        for (char c : stack) {
            if (count == keep) break;
            result.append(c);
            count++;
        }

        return Long.parseLong(result.toString());
    }
}