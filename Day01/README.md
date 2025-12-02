# DAY 01: Secret Entrance

Here you will find a walk through of the puzzle, the important concepts and ideas behind it and the reasoning behind the java solution.
The goal is to make sure everyone understands why the solutions work the way they do.

---

# Understanding the Problem
We have a circular dial numbered 0-99 and the dial:

- starts at 50
- rotates left (L) or right (R)
- moves in discrete clicks
- Wraps around (if you get to 0 and move left by 1 click, you are back at 99) **IMPORTANT**
                0
         99            1
      98                  2
    97                      3
    ...                   ...
      52                48
         51         49
              50  ← start
The input consists of rotation instructions like:
L68
R48
L5

---

# Our task:

### Part 1
count how many times the dial points at 0 at the end of each rotation

### Part 2
count how many times the dial hits 0 during any rotation
| Instruction | Start | Steps Taken | End | Hits 0? |
|------------|--------|-------------|-----|---------|
| L5         | 50     | 50→49→48→47→46→45 | 45  | No |
| R8         | 45     | 45→46→47→48→49→50→51→52→53 | 53 | No |

---

# Key concept : this dial is essentially a circular array with modulo

to handle wrap around, we use modulo arithmetic:
```java
(pos - movement + 100) % 100
```
this is the cleanest way to model circular behaviour.

---

# Part 1
part 1 treats each rotation as one big jump.
For example:
R60 means “move 60 steps right, instantly.”
We do not simulate every click as we only care about the final number the dial lands on.
### Why this works
- One calculation per line
- No nested loops
- Very efficient (O(n))
```java
if (direction == 'R') {
  pos = (pos + distance) % 100;
} else {
  pos = (pos - distance + 100) % 100;
}

if (pos == 0) count++:
```

---

# Part 2
part 2 must count every click.
this means R60 must be simulated one click at a time:
- move 1 position
- check if its 0
- repeat
so we need a nested loop
Why we will use a step variale????
```java
int step = (direction == 'R') ? 1 : -1;
```
to keep the inner loop clean.
```java
for (int i = 0; i < distance; i++) {
    pos = (pos + step + 100) % 100;
    if (pos == 0) count++;
}
```
no repeated logic inside the loop
For loop as we know the number of clicks (distance), so a counted loop is perfect.

---

Time & Space Complexity
### Part 1

| Metric     | Complexity | Explanation             |
|------------|------------|-------------------------|
| Time       | O(n)       | One operation per line  |
| Space      | O(1)       | Only integers stored    |

### Part 2

| Metric     | Complexity              | Explanation             |
|------------|--------------------------|-------------------------|
| Time       | O(sum of all distances) | Every click is simulated |
| Space      | O(1)                    | Still constant space     |
