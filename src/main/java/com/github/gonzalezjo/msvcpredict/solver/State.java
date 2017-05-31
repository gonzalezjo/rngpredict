package com.github.gonzalezjo.msvcpredict.solver;

import com.github.gonzalezjo.msvcpredict.MsvcConstants;

public final class State implements MsvcConstants {
    private int state;

    public State(final int state) {
        this.state = state;
    }

    public short next() {
        state = (M * state + C) & MODULUS;
        return (short) (state >> SHIFTS);
    }
}
