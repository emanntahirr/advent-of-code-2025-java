package src.Day01;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class Day01 {

    public static void main(String[] args) throws Exception {
        List<String> input = readInput("src/Day01/input.txt");

        System.out.println("Part 1: " + solvePart1(input));
        System.out.println("Part 2: " + solvePart2(input));
    }

    private static int solvePart1(List<String> input) {
        return 0; // TODO
    }

    private static int solvePart2(List<String> input) {
        return 0; // TODO
    }

    private static List<String> readInput(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }
}
