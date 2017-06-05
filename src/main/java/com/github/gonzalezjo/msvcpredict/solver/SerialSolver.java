package com.github.gonzalezjo.msvcpredict.solver;

import com.github.gonzalezjo.msvcpredict.MsvcConstants;

import java.util.BitSet;

@SuppressWarnings("Duplicates")
public final class SerialSolver implements Solver, MsvcConstants {
    private static final byte DOUBLER = 1;
    private final BitSet bitSet = new BitSet(RAND_MAX);

    private int calculateState(final short[] samples) {
        final int sample = samples[0] << SHIFTS;
        long solution = 0; // -1 maybe?

        Exit: for (int i = 0; i <= (int) RAND_MAX << DOUBLER; i++) {
            solution = sample + i;
            for (int p = 1; p < samples.length; p++) {
                if (samples[p] != (M * solution + C & MODULUS) >> SHIFTS) { // recalculating this every time. unnecessary?
                    solution = 0; // -- 1 maybe?
                    continue Exit;
                }
                solution = M * solution + C & MODULUS;
            }
            solution = M * solution + C & MODULUS;
            break;
        }
        return (int) solution;
    }


    private int solveMultipleValueSet(final short[][] values) {
        final short[] subsamples = values[0];
        int i = 0;
        for (short potentialValue : subsamples) {
            i++;
            // if (i > 20)
            //     continue;
            // if (i < 52)
            //     continue;
            if (i < 246)
                continue;
            final long base = potentialValue << SHIFTS;
            for (int lsb = 0; lsb <= (int) RAND_MAX << DOUBLER; lsb++) {
                long validState = getValidState(base + lsb, values);
                if (validState != -1) {
                    System.out.println("Valid state!");
                    return (int) validState;
                }
            }
            System.out.println(i);
        }
        return -1;
    }

    private long getValidState(long possibleState, short[][] values) {
        final long state = possibleState;
        final boolean[] numbers = new boolean[1 + (RAND_MAX)];
        bitSet.clear();
        // for (int i = 0; i < numbers.length; i++)
        //     if (!numbers[i])
        //         System.out.println(i);
        // bitSet.clear();
        // for (short[] value : values) {
        //     for (short i : value) {
        //         bitSet.set(i);
        //     }
        // }
        for (int i = 0; i < values.length; i++) { // i = 1
            possibleState = state;
            for (short v : values[i]) {
                numbers[v] = true;
                bitSet.set(v);
            }
            for (int k = 0; k <= values.length; k++) {
                if (!numbers[(int) ((M * possibleState + C & MODULUS) >> SHIFTS)]) {
                    break;
                } else {
                    if (k == values.length) {
                        System.out.println("Returning a valid state. K: " + k + " I: " + i);
                        return possibleState;
                    }
                    possibleState = (M * possibleState + C) & MODULUS;
                }
            }
        }

        return -1;
        // return possibleState;
    }

    @Override
    public State solve(final short[][] samples) {
        final int solution;
        final long time = System.nanoTime();

        if (samples.length == 1) {
            System.out.println("Solving 1D set of length: %d" + samples[0].length);
            solution = calculateState(samples[0]);
        } else {
            System.out.println(String.format(
                    "Solving 2D set of size: %d x %d", samples.length, samples[0].length));
            solution = solveMultipleValueSet(samples);
        }

        System.out.println("Total time: " + (System.nanoTime() - time));

        if (solution == -1)
            throw new RuntimeException("No solution found.");

        return new State(solution);
    }

}
