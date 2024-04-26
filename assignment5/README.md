# Assignment 1

This is the project for [Assignment 5 of DSA](https://olindsa2024.github.io/assignments/assignment_05).

## Results
| Size        | Naive         | Strassen      |
|-------------|---------------|---------------|
| 2 x 2       | 667.399us     | 1.612300ms    |
| 4 x 4       | 256.199us     | 8.825601ms    |
| 8 x 8       | 247.599us     | 2.897500ms    |
| 16 x 16     | 1.285499ms    | 8.181ms       |
| 32 x 32     | 7.640001ms    | 19.144700ms   |
| 64 x 64     | 19.888400ms   | 181.441100ms  |
| 128 x 128   | 39.911600ms   | 285.036499ms  |
| 256 x 256   | 318.877401ms  | 1.239878100s  |
| 512 x 512   | 2.796458300s  | 8.689817300s  |
| 1024 x 1024 | 23.115566800s | 56.392482800s |

It seems like Strassen is really only practical for very large matrices. For the last four matrices there is roughly a 
x10 increase each time for the Naive method and a x8 increase for the Strassen method. With this, I estimate you would 
need a matrix with a size of roughly $2^{14}$, or a 16,384 x 16,384 matrix, to see any improvement. This would take over
two days to run each matrix multiplication. That said, that's a very hand-wavy estimation and could very well be a power
of 2 or two off. 