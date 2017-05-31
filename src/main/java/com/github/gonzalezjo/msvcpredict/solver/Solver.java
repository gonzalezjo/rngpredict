package com.github.gonzalezjo.msvcpredict.solver;

import com.github.gonzalezjo.msvcpredict.MsvcConstants;

public interface Solver extends MsvcConstants {
    State solve(final short[][] samples);
}