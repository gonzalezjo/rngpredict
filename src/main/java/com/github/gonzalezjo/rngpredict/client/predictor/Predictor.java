package com.github.gonzalezjo.rngpredict.client.predictor;

import com.github.gonzalezjo.rngpredict.client.solver.Solver;
import com.github.gonzalezjo.rngpredict.client.solver.State;
import com.github.gonzalezjo.rngpredict.client.solver.scaler.Scaler;

final public class Predictor {
    private final Scaler scaler;
    private final Solver solver;

    public Predictor(final Scaler scaler, final Solver solver) {
        this.scaler = scaler;
        this.solver = solver;
    }

    public double[] nextNumbers(final int amount) {
        final short[][] scaled = scaler.processable();

        if (scaled[0][0] == -1) {
            System.out.println("Unable to process scaled output.");
            return new double[amount];
        }

        final State state = solver.solve(scaled);
        final double[] accumulator = new double[amount];

        for (int i = 0; i < amount; i++) {
            accumulator[i] = state.next();
        }

        return scaler.originalScale(accumulator);
    }

}
