# Day 05: Cafeteria
Here you will find a walkthrough of the puzzle, th eimportant concepts and ideas behind it and the reasoning behind the java solution.
the goal is to ensure everyone understands why the solutions works the way it does.

---

# Understanding the Problem
The database file is split into two sections.
1) Fresh ingredient ID ranges
each line is a ring in the form:
*start - end*
these ranges are inclusive, meaning 3-5 includes 3, 4, 5.
these ranges can overlap, and an ingredient ID is considered fresh if it appears in any range.

2) Available ingredient IDs (Part 1)
After a blank line, we got a list of individual ingredient IDs:
1
5
8
11
17
32
these are the ingredients currently in stock.

---

# Our Task:

### Part 1
count how many of the available ingredients IDs are fresh.
For each ID:
- Check if it falls inside any of the fresh ranges.

### Part 2
ignore the individual ingredient IDS completely
instead:
- count how many unique ingredients IDS are considered fresh by the ranges alone.
- overlapping ranges must not be double counted.

---

# Key Concept: Range membership vs Range Coverage
PART 1 -> RANGE MEMBERSHIP
does this specific ID fall inside any of the ranges??

PART 2 -> RANGE COVERAGE
how many unique numbers do these ranges cover in total?

---

# Data Structures Used

| Structure              | Why We Use It                             |
| ---------------------- | ----------------------------------------- |
| `ArrayList<long[]>`    | Stores `[start, end]` for each range      |
| `long`                 | Ingredient IDs are very large             |
| Nested loops           | Needed for checking IDs in Part 1         |
| Sorting + Greedy Merge | Needed to avoid double counting in Part 2 |

# Part 1
checking for individual ingredients IDs
- check against every range
- if it fits inside one -> it is fresh
- stop checking further ranges for that ID

Pseudocode
```java
Read all ranges into a list

freshCount = 0

For each ingredient ID:
    For each range:
        If ID is inside the range:
            freshCount++
            Stop checking this ID

Print freshCount
```
### why we do it like this????

- every ingredient ID is validated against all valid ranges.
- as soon as one range matches, we stop.
- this guarantees correctness without unnecessary checks

### Time & Space Complexity (Part 1)
| Metric | Complexity | Explanation                    |
| ------ | ---------- | ------------------------------ |
| Time   | O(n × m)   | n IDs checked against m ranges |
| Space  | O(m)       | Only the ranges are stored     |

---

# Part 2
now the second part of the input is irrelevant.
we only care about *how many unique numbers are covered by the ranges?*
The challenge here is that:
- ranges overlap
- overlaps cant be counted twice
so we must **MERGE** overlapping ranges first

### VISUAL
```java
3–5
10–14
12–18
16–20
```
*this is what we are given*
```java
3───5

10────14
    12────────18
         16────20
```
*on a number line*
```java
3───5

10──────────────20
```
*after merging^^^*
now we can count:
- 3 -> 5 = 3 numbers
- 10 -> 20 = 11 numbers
Total = 14

### KEY CONCEPT
before merging we sort all ranges by their value

Pseudocode:
```java
Read all ranges
Sort ranges by starting value

currentStart = first range start
currentEnd = first range end

totalFresh = 0

For each next range:
    If it overlaps current range:
        Extend currentEnd
    Else:
        Add (currentEnd - currentStart + 1) to total
        Move currentStart and currentEnd to new range

Add final merged range size to total
Print totalFresh
```

### Why this algorithm is optimal
- we never examine every number inside the ranges
- sort once
- merge in one linear pass
this keeps the algorithm fast even for huge ranges

### Time and Space Complexity (Part 2)
| Metric | Complexity | Explanation          |
| ------ | ---------- | -------------------- |
| Time   | O(n log n) | Sorting + merge pass |
| Space  | O(n)       | Storing all ranges   |




