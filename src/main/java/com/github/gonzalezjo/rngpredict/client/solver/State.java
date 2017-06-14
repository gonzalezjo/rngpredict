package com.github.gonzalezjo.rngpredict.client.solver;

import com.github.gonzalezjo.rngpredict.client.MsvcConstants;

public final class State implements MsvcConstants {
    private int internalState;

    public State(final int state) {
        this.internalState = state;
    }

    public short next() {
        internalState = (M * internalState + C) & MODULUS;
        return (short) (internalState >> SHIFTS);
    }
}
