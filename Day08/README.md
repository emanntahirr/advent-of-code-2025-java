# Day 08: Playground
Here you will find a walkthrough of the puzzle, the important concepts and ideas behind it, and the reasoning behind the Java solution.
This day was quite challenging because the problem looks like a geometry problem at first, but it is actually a graph + union–find problem in disguise.
The goal is to clearly understand why this solution works and how it connects to similar problems in the future.

---

# Understanding the Problem
we are given a list of junction boxes in 3D space.
each line is in the form:
```java
x,y,z
```
each junction box starts off alone in its own circuit
when two boxes are connected:
- electricity can flow between them
- they become part of the same circuit
connections are always made in order of distance (closest first).

---

# Our Task

### Part 1
- consider the 1000 closest pairs of junction boxes
- for each pair:
- - if they are already in the same circuit → nothing happens.
- - otherwise -> their circuits merge
- After these 1000 connections:
- - find the sizes of all circuits
- - multiply the sizes of the 3 largest circuits

### Part 2
- continue making valid connections
- keep going until all junctions boxes are in one single circuit
- take the last two boxes that were connected
- multiply their X co oridnates together

---

# Key Concept: Dynamic Connectivity
the core of this problem is:
- we keep connecting nodes and need to quickly know whether two nodes are already in the same group

This is exactly what the union-find data structure is made for
It supports:
- find(x) -> which circuit does node belong to
- union(a, b) -> merge two circuits
in almost constant time

### Closest Connections first
every pair of boxes forms a possible connection
each connection has a distance:
```java
(x1 - x2)² + (y1 - y2)² + (z1 - z2)²
```
We:
1. compute all distances
2. sort them from smallest to largest
3. process them in that order
same greedy idea used in Kruskals Algorithm

---

# DATA STRUCTURES USED
| Structure                   | Why We Use It                                          |
| --------------------------- | ------------------------------------------------------ |
| `ArrayList<Point>`          | Stores all junction box coordinates                    |
| `ArrayList<Edge>`           | Stores every possible connection                       |
| `UnionFind`                 | Tracks which boxes are in which circuit                |
| `HashMap<Integer, Integer>` | Counts final circuit sizes                             |
| Sorting                     | Needed to process connections from shortest to longest |

---

# Part 1: after 1000 closest connections
pseudocode:
```java
Read all points
Build all possible edges with squared distances
Sort edges by distance

Initialize Union-Find

For first 1000 edges:
    Union the two endpoints

Count size of each circuit
Sort sizes descending
Multiply the largest three
```
why????
- we must respect the exact order of closest pairs
- even if a pair is already connected, it still counts as one of the 1000
- union-find prevents double-merging
- we never manually track every node in a ciruit, dsu does it for us

---

# Time and Space Complexity (Part 1)
| Metric | Complexity  | Explanation                        |
| ------ | ----------- | ---------------------------------- |
| Time   | O(N² log N) | Building and sorting all distances |
| Space  | O(N²)       | Storing all edges                  |

---

# Part 2: connecting everything into one circuit
we only care about conections that actually merge two circuits.
pseudocode:
```java
Count how many components currently exist

For remaining edges in order:
    If union(u, v) is successful:
        Decrease component count
        If only 1 component remains:
            This is the final needed edge
            Stop
```
why????
- Every successful union reduces the number of components by exactly one.
- When only one component remains, the entire system is connected.
- Because we process edges in distance order, this final edge is the shortest possible edge that fully connects everything.

---

# Time and space complexity (Part 2)
| Metric | Complexity | Explanation              |
| ------ | ---------- | ------------------------ |
| Time   | O(N²)      | Scanning remaining edges |
| Space  | O(N)       | Union–Find + maps        |
