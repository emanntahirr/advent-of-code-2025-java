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
