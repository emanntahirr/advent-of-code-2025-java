import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day06 {

    public static void main(String[] args) throws Exception {

        ArrayList<String> rows = new ArrayList<>();
        Scanner sc = new Scanner(new File("day6.txt"));

        while (sc.hasNextLine()) {
            rows.add(sc.nextLine());
        }
        sc.close();

        int R = rows.size();
        int C = rows.get(0).length();

        long grandTotal = 0;

        int col = 0;
        while (col < C) {

            boolean isEmptyColumn = true;
            for (int r = 0; r < R; r++) {
                if (rows.get(r).charAt(col) != ' ') {
                    isEmptyColumn = false;
                    break;
                }
            }

            if (isEmptyColumn) {
                col++;
                continue;
            }

            int startCol = col;

            while (col < C) {
                boolean stillData = false;
                for (int r = 0; r < R; r++) {
                    if (rows.get(r).charAt(col) != ' ') {
                        stillData = true;
                        break;
                    }
                }
                if (!stillData) break;
                col++;
            }

            int endCol = col;

            ArrayList<Long> numbers = new ArrayList<>();

            for (int c = endCol - 1; c >= startCol; c--) {
                StringBuilder sb = new StringBuilder();

                for (int r = 0; r < R - 1; r++) {
                    char ch = rows.get(r).charAt(c);
                    if (Character.isDigit(ch)) {
                        sb.append(ch);
                    }
                }

                if (sb.length() > 0) {
                    numbers.add(Long.parseLong(sb.toString()));
                }
            }

            char operator = rows.get(R - 1).charAt(startCol);

            long result;
            if (operator == '+') {
                result = 0;
                for (long x : numbers) result += x;
            } else {
                result = 1;
                for (long x : numbers) result *= x;
            }

            grandTotal += result;
        }

        System.out.println("Day 6 Part 2 Answer: " + grandTotal);
    }
}
