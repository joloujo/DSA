# Assignment 4

This is the project for [Assignment 4 of DSA](https://olindsa2024.github.io/assignments/assignment_04).

## Complexity Analysis

### Quick Sort
I'll use the form of the recurrence relation of the master theorem
$$T(n)=aT(\frac{n}{b})+f(n)$$
and the cases of the master theorem listed in the
[Wikipedia article for the master theorem](https://en.wikipedia.org/wiki/Master_theorem_(analysis_of_algorithms)).

In quick sort, the problem is recursively broken up into 2 pieces that are half as large. This means $a=2$ and $b=2$,
meaning $c_{crit}=\log_b{a}=1$. This fits case 2 of the master theorem. Because the work to recombine the two pieces is 
$\Theta(n)$, this fits case 2a of the master theorem with $k=0$. This gives us
$$\Theta(n^{c_{crit}}\log^{k+1}{n})=\Theta(n^{1}\log^{1}{n})=\Theta(n\log{n})$$

Therefore, quick sort has time complexity $\Theta(n\log{n})$.

### Merge Sort
By the same logic as quick sort, merge sort has time complexity $\Theta(n\log{n})$.

### Selection Sort
In selection sort, to sort the next element, you need to find the minimum element of all unsorted elements. This means 
you have to look at every element, which has time complexity $\Theta(n)$. You then have to do this $\Theta(n)$ operation
for every element in the list, which is $n$ times. $\Theta(n \cdot n)=\Theta(n^{2})$, therefore, selection sort has time 
complexity $\Theta(n^{2})$.

### Radix Sort
For every cycle of radix sort, you need to put every element into a bin based on a specific digit, which has time 
complexity $\Theta(n)$. You then have to do this $\Theta(n)$ operation for every digit in the set of numbers you have, 
which is $d$ times, where $d$ is the order of magnitude (number of digits) of the numbers that need to be sorted. 
$\Theta(n \cdot d)=\Theta(dn)$, therefore, radix sort has time complexity $\Theta(dn)$.

## Benchmarking
Here are the results of benchmarking the sorting algorithms

![Benchmarking results](benchmarking/benchmark.png)

The lists the sorting algorithms were tested on had lengths that were powers of two, ranging from $2^{1}$ to $2^{13}$. 
They contained random integers ranging from 0 to 9999. Each sorting algorithm was tested on each length of list 1000
times.

These results make sense with the time complexities that we determined earlier. It also makes sense that quick sort is 
the most widely used sorting algorithm out of the ones I implemented, because it is consistently the fastest, both with
small lists and large ones. I was surprised by how well radix sort performed, and with very large datasets that have
a small range of numbers, I could see it being very useful. In pretty much any other application, though, I would just
go with quick sort.

## New Frontiers in Sorting?



## Practice with the master theorem
Here are my answers to [the worksheet from MIT 6.046](https://courses.csail.mit.edu/6.046/spring02/handouts/master.pdf)

1. $T(n)=\Theta(n^{2})$
2. $T(n)=\Theta(n^{\log_2{7}})$
3. $T(n)=\Theta(n^{2}\log{n})$
4. $T(n)=\Theta(n\log{n})$
5. $T(n)=\Theta(n^{2})$
6. Doesn't apply
7. $T(n)=\Theta(n^{2}\log^{2}{n})$
8. $T(n)=\Theta(n^{\log_2{5}})$
9. $T(n)=\Theta(n\log{\log{n}})$
10. $T(n)=\Theta(n^{\frac{1}{2}})$
11. $T(n)=\Theta(\log^{2}{n})$
12. Doesn't apply
13. $T(n)=\Theta(n^{\frac{1}{2}})$
14. $T(n)=\Theta(n\log^{2}{n})$
15. Doesn't apply
16. $T(n)=\Theta(n^{\frac{1}{2}}\log{n})$
17. $T(n)=\Theta(n^{0.51})$
18. $T(n)=\Theta(n!)$
19. $T(n)=\Theta(n^{\log_2{3}})$
20. $T(n)=\Theta(n^{2})$
21. $T(n)=\Theta(n\log{n})$
22. $T(n)=\Theta(n^{2})$
23. $T(n)=\Theta(n^{2})$
24. $T(n)=\Theta(2^{n})$
25. $T(n)=\Theta(n^{2})$