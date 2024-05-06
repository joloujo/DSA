# Assignment 6

This is the project for [Assignment 6 of DSA](https://olindsa2024.github.io/assignments/assignment_06).

## Overview

This assignment is a chance to do an individual deep dive on an application or algorithm related to the themes of this 
class.

My project explores the traveling salesman problem and different ways to solve it or approximate a solution. I analyze 
the theoretical performance of different solutions and I also empirically measure their performance and compare these 
solutions in terms of the length of the paths and how long they took to find a solution.

## Algorithm Analysis

All results can be found in the [benchmarking markdown file](benchmark.md)

### Brute Force

This is the only exact algorithm I implemented. It works by checking every possible permutation of nodes. At a 10x10 
graph, it was about 10 times slower than the next slowest algorithms, but it got the shortest path every time. For small
graphs, this is definitely the best, but once you reach even 8x8, you need something else.

### Reverse Tree

This was my attempt at creating an algorithm with a balance of accuracy and speed. It recurses through the decision
tree, avoiding any null branches and making more farsighted decisions. It was about 10 times faster than brute force on 
a 10x10 graph, and typically produced paths that were about 5% longer than the shortest path. I think this would be best
for medium graphs, but can be improved with further optimization.

### Nearest Neighbor

This is the greedy algorithm for this problem. It works by starting at a node and then taking the shortest edge to a 
remaining node at every step. Because it tries starting at every node, has to add every node to the path, and at each
step needs to check the weight to each node, it has runtime $O(n^{3})$. It is incredibly fast, at more than 10,000 times
faster than the brute force method for 10x10 graphs. It's accuracy suffers for this, though, and the paths it returns 
are typically about 20% longer than the shortest path.

### 2-Opt

This is a method for optimizing an already known valid hamiltonian path. It works by trying to switch each pair of nodes
and then if there's improvement, keeping the switch. Once no more improvements can be made with a simple switch, it 
returns. This algorithm only added a fraction of a percent of the brute force runtime (<0.5%), and was able to improve 
paths slightly, by about 1% across the board. I wish I had had the chance to test this with a Nearest Neighbor algorithm
that would return possible paths if there was one (the current one will just return will if it gets stuck instead of
backtracking), as I think the best algorithm by far would be a greedy algorithm optimized by 2-opt.