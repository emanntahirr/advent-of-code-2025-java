import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day04 {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.println("Which part do you want to run? (1 or 2)");
        int choice = input.nextInt();

        char[][] grid = loadGrid();

        if (choice == 1) {
            solvePart1(grid);
        } else {
            solvePart2(grid);
        }

        input.close();
    }

    private static char[][] loadGrid() throws Exception {
        Scanner sc = new Scanner(new File("day04.txt"));
        List<String> lines = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        sc.close();

        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        return grid;
    }

    public static void solvePart1(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        int accessibleCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '@') continue;

                int neighbors = 0;

                for (int k = 0; k < 8; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];

                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;

                    if (grid[nr][nc] == '@') {
                        neighbors++;
                    }
                }

                if (neighbors < 4) {
                    accessibleCount++;
                }
            }
        }

        System.out.println("Part 1 accessible rolls: " + accessibleCount);
    }

    public static void solvePart2(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        int totalRemoved = 0;

        while (true) {
            boolean[][] toRemove = new boolean[rows][cols];
            int removedThisRound = 0;

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] != '@') continue;

                    int neighbors = 0;

                    for (int k = 0; k < 8; k++) {
                        int nr = r + dr[k];
                        int nc = c + dc[k];

                        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;

                        if (grid[nr][nc] == '@') {
                            neighbors++;
                        }
                    }

                    if (neighbors < 4) {
                        toRemove[r][c] = true;
                    }
                }
            }

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (toRemove[r][c]) {
                        grid[r][c] = '.';
                        removedThisRound++;
                    }
                }
            }

            if (removedThisRound == 0) {
                break;
            }

            totalRemoved += removedThisRound;
        }

        System.out.println("Part 2 total rolls removed: " + totalRemoved);
    }
}
