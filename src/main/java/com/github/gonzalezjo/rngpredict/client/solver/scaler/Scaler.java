package com.github.gonzalezjo.rngpredict.client.solver.scaler;

public interface Scaler {
    short[][] processable();
    double[] originalScale(final double[] samples);
}
