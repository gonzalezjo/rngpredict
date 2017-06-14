package com.github.gonzalezjo.rngpredict.client;

public interface MsvcConstants {
    byte SHIFTS = 16; // 31 - log2ceil(RAND_MAX)
    int MODULUS = 0x7fffffff;
    int M = 214013;
    int C = 2531011;
    short RAND_MAX = 0x7fff;
}
