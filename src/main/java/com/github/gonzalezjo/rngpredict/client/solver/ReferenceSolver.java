package com.github.gonzalezjo.rngpredict.client.solver;

import com.github.gonzalezjo.rngpredict.client.MsvcConstants;

public final class ReferenceSolver implements Solver, MsvcConstants {
    private int solveSingleSet(final short[] samples) {
        final int sample = samples[0] << SHIFTS;
        long solution = -1;

        Exit: for (long i = 0; i <= (int) RAND_MAX << DOUBLER; i++) {
            solution = sample + i;
            for (int p = 1; p < samples.length; p++) {
                if (samples[p] != (M * solution + C & MODULUS) >> SHIFTS) {
                    solution = -1;
                    continue Exit;
                }
                solution = M * solution + C & MODULUS;
            }
            break;
        }

        return (int) solution;
    }


    private int solveMultiSet(final short[][] values) {
        final short[] subSamples = values[0];

        short potentialValue;
        for (int i = 1; i <= values[0][0]; i++) {
            potentialValue = subSamples[i];
            final long base = potentialValue << SHIFTS; // inline?
            for (int lsb = 0; lsb <= (int) RAND_MAX << DOUBLER; lsb++) {
                long validState = getValidState(base + lsb, values);
                if (validState != -1) {
                    return (int) validState;
                }
            }
        }

        return -1;
    }

    private long getValidState(final long possibleState,
                               final short[][] values) {
        long solution = -1;
        long currentState;

        Exit: for (int i = 1; i < values.length; i++) {
            currentState = possibleState;
            for (int k = 0; k < values[0][values[0][0] + 1]; k++) {
                solution = currentState;
                if (values[i][(int) ((currentState = M * currentState + C & MODULUS) >> SHIFTS)] == 0) {
                    solution = -1;
                    continue Exit;
                }
            }
            return solution;
        }

        return solution;
    }

    @Override
    public State solve(final short[][] samples) {
        final int solution;
        final long time = System.nanoTime();

        if (samples[0].length - 1 != RAND_MAX) {
            System.out.println(
                    "Solving 1D set of length: " + samples[0].length);
            solution = solveSingleSet(samples[0]);
        } else {
            System.out.println(String.format(
                    "Solving 2D set of size: %d x %d",
                    samples.length,
                    samples[0].length));
            solution = solveMultiSet(samples);
        }

        System.out.println("Total time: " + (System.nanoTime() - time));

        if (solution == -1) {
            throw new RuntimeException("No solution found.");
        }

        return new State(solution);
    }

}
