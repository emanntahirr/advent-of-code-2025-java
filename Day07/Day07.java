import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day07 {

    static int R, C;
    static char[][] grid;
    static long[][] memo;
    static boolean[][] seen;

    public static void main(String[] args) throws Exception {

        ArrayList<String> rows = new ArrayList<>();
        Scanner sc = new Scanner(new File("day7.txt"));

        while (sc.hasNextLine()) {
            rows.add(sc.nextLine());
        }
        sc.close();

        R = rows.size();
        C = rows.get(0).length();

        grid = new char[R][C];
        for (int i = 0; i < R; i++) {
            grid[i] = rows.get(i).toCharArray();
        }

        int startR = 0, startC = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (grid[r][c] == 'S') {
                    startR = r;
                    startC = c;
                }
            }
        }

        memo = new long[R][C];
        seen = new boolean[R][C];

        long timelines = dfs(startR + 1, startC);

        System.out.println("Day 7 Part 2 Answer: " + timelines);
    }

    static long dfs(int r, int c) {
        if (r < 0 || r >= R || c < 0 || c >= C) {
            return 1;
        }

        if (seen[r][c]) return memo[r][c];
        seen[r][c] = true;

        char ch = grid[r][c];
        long res;

        if (ch == '.') {
            res = dfs(r + 1, c);
        } else if (ch == '^') {
            long left = dfs(r, c - 1);
            long right = dfs(r, c + 1);
            res = left + right;
        } else if (ch == 'S') {
            res = dfs(r + 1, c);
        } else {
            res = dfs(r + 1, c);
        }

        memo[r][c] = res;
        return res;
    }
}
