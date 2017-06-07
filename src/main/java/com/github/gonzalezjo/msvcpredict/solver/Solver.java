package com.github.gonzalezjo.msvcpredict.solver;

import com.github.gonzalezjo.msvcpredict.MsvcConstants;

public interface Solver extends MsvcConstants {
    byte DOUBLER = 1;
    State solve(final short[][] samples);
}