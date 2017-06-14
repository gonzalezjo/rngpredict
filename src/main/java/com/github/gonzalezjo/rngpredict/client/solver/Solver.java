package com.github.gonzalezjo.rngpredict.client.solver;

import com.github.gonzalezjo.rngpredict.client.MsvcConstants;

public interface Solver extends MsvcConstants {
    byte DOUBLER = 1;
    State solve(final short[][] samples);
}
