# Assignment 7

This is the project for [Assignment 7 of DSA](https://olindsa2024.github.io/assignments/assignment_07).

## Writeup

I implemented Lempel-Ziv compression. The algorithm was able to compress "The Raven" by Edgar Allan Poe from 16560 bits 
to 12146 bits and *Romeo and Juliet* by William Shakespeare from 1139616 to 635721 bits. This is fairly significant, at 
27% space savings for "The Raven" and an impressive 44% space savings for *Romeo and Juliet*.

I wanted to see if converting the string to a bit array would make the compression more or less efficient. It turns out 
that using the strings directly is significantly more efficient in both space and time. Converting to binary actually 
made the encoding of "The Raven" take more space than the original text. In retrospect, this makes sense, because when
you encode with chars, you get more bang for your buck when you get a repetition since they are a byte instead of a bit.

| Text               | Raw     | LZ     | Bits → LZ |
|--------------------|---------|--------|-----------|
| "The Raven"        | 16560   | 12146  | 17693     |
| *Romeo and Juliet* | 1139616 | 635721 | 918442    |

```
The Raven
    Uncompressed size: 16560 bits
    LZ compression with strings/chars
        Compressed size: 12146 bits
        Compression ratio: 1.3634118228223284
        Space Saving: 0.26654589371980675
    LZ compression with bits:
        Compressed size: 17693 bits:
        Compression ratio: 0.9359633753461821
        Space Saving: -0.06841787439613523
Romeo and Juliet
    Uncompressed size: 1139616 bits
    LZ compression with strings/chars
        Compressed size: 635721 bits
        Compression ratio: 1.7926354485694196
        Space Saving: 0.44216209670625894
    LZ compression with bits:
        Compressed size: 918442 bits:
        Compression ratio: 1.2408143355813432
        Space Saving: 0.19407765422738887
```