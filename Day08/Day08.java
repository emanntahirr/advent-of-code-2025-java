import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Day08 {

    static class Point {
        int x, y, z;
        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static class Edge {
        int u, v;
        long dist;
        Edge(int u, int v, long dist) {
            this.u = u;
            this.v = v;
            this.dist = dist;
        }
    }

    static class UnionFind {
        int[] parent;
        int[] size;

        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return false;

            if (size[ra] < size[rb]) {
                parent[ra] = rb;
                size[rb] += size[ra];
            } else {
                parent[rb] = ra;
                size[ra] += size[rb];
            }
            return true;
        }
    }

    public static void main(String[] args) throws Exception {

        ArrayList<Point> points = new ArrayList<>();
        Scanner sc = new Scanner(new File("day8.txt"));

        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().trim().split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            points.add(new Point(x, y, z));
        }
        sc.close();

        int N = points.size();
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                Point a = points.get(i);
                Point b = points.get(j);

                long dx = (long)a.x - b.x;
                long dy = (long)a.y - b.y;
                long dz = (long)a.z - b.z;
                long dist = dx * dx + dy * dy + dz * dz;

                edges.add(new Edge(i, j, dist));
            }
        }

        edges.sort(Comparator.comparingLong(e -> e.dist));

        UnionFind uf = new UnionFind(N);

        int limit = Math.min(1000, edges.size());
        for (int i = 0; i < limit; i++) {
            Edge e = edges.get(i);
            uf.union(e.u, e.v);
        }

        HashMap<Integer, Integer> circuitSizes = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int root = uf.find(i);
            circuitSizes.put(root, circuitSizes.getOrDefault(root, 0) + 1);
        }

        ArrayList<Integer> sizes = new ArrayList<>(circuitSizes.values());
        sizes.sort(Collections.reverseOrder());

        long part1 = 1;
        for (int i = 0; i < Math.min(3, sizes.size()); i++) {
            part1 *= sizes.get(i);
        }

        System.out.println("Day 8 Part 1: " + part1);

        HashSet<Integer> roots = new HashSet<>();
        for (int i = 0; i < N; i++) {
            roots.add(uf.find(i));
        }
        int components = roots.size();

        Edge lastEdge = null;

        for (int i = limit; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (uf.union(e.u, e.v)) {
                components--;
                if (components == 1) {
                    lastEdge = e;
                    break;
                }
            }
        }

        Point p1 = points.get(lastEdge.u);
        Point p2 = points.get(lastEdge.v);
        long part2 = 1L * p1.x * p2.x;

        System.out.println("Day 8 Part 2: " + part2);
    }
}
