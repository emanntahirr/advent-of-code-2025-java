# Day 04: Printing Department

Here you will find a walk through of the puzzle, the important concepts and ideas behind it and the reasoning behind the java solution.
The goal is to make sure everyone understands why the solutions work the way they do.

---

# Understanding the Problem
This problem is all about 2D grids, neighbour traversal and iterative simulation.

We are given a grid of characters:
- @ -> roll of paper
- . -> empty space
For each @ we look for the 8 surrounding cells (up, down, left, right, and 4 diagnols)
A forklift can access a roll of paper only if fewer than 4 of its 8 surrounding positions contain @.

---

# Our Task:

### Part 1
count how mny rolls of paper (@) are accessible based on the rule:
A roll is accessible if it has less than 4 neighboring @.

### Part 2
once a roll becomes accessible:
- it is removed (.)
- this may expose new rolls (@)
- we must repeat this process until no more rolls can be removed
we must count the total no of rolls removed across all rounds.

---

# Key Concept: This is a 2D Grid Neighbor Traversal Problem

This problem is fundamentally the same idea as common LeetCode problems such as:
- Number of Islands
- Game of Life
- Flood Fill
- Rotting Oranges

### How we check the 8 neighbors
we use two direction arrays:
```java
int [] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
```

each dr[k], dc[k] pair represents one direction:

```java
(-1, -1)  (-1, 0)  (-1, 1)
( 0, -1)   ( r,c ) ( 0, 1)
( 1, -1)  ( 1, 0)  ( 1, 1)
```
instead of writing 8 seperate checks, we loop through these arrays to visit all neighbours cleanly.

---

# Part 1
in part 1, each roll is checked independently

for every @:
- look at all 8 neighbors
- count how many are @
- if that count is < 4, it is accessible.
Why this works?
- we check every cell once
- neighbor checks are constant
- no mutations to the grid
- simple brute force neighbor counting
```java
for each cell in grid:
    if cell == '@':
        count neighbors
        if neighbors < 4:
            accessible++
```

---

# Part 2
part 2 turns the problem into a repeated simulation.
once a roll can be accessed:
- it is removed
- this may unlock new accessible rolls
- the process must repeat until nothing changes

why we must use a toRemove grid??
we cannot removee rolls immediatly while scanning, because that would
- affect neighbor counts
- produce incorrect results
Instead we:
1. mark all removable rolls in a boolean[][] toRemove
2. remove them all at once
3. repeat
```java
while (true):
    mark accessible rolls
    remove all marked rolls
    if nothing removed:
        break
```
this guarantees correctness

---

# Data structures used
| Structure              | Why it is Used                    |
| ---------------------- | --------------------------------- |
| `char[][] grid`        | Fast 2D access for the map        |
| `boolean[][] toRemove` | Prevents mid-scan mutation issues |
| `int[] dr, dc`         | Clean neighbor traversal          |
no queues, stacks, or recursion required, handled with nested loops and arrays.

---

# Time and Space Complexity
### Part 1
| Metric | Complexity | Explanation             |
| ------ | ---------- | ----------------------- |
| Time   | O(R × C)   | Each cell scanned once  |
| Space  | O(R × C)   | Only the grid is stored |

### Part 2
| Metric | Complexity   | Explanation                            |
| ------ | ------------ | -------------------------------------- |
| Time   | O(K × R × C) | Grid is scanned once per removal round |
| Space  | O(R × C)     | Grid + temporary `toRemove` array      |

Where K is the number of simulation rounds.
Since at least one roll is removed each round, K is always bounded.