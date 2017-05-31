package com.github.gonzalezjo.msvcpredict;

import com.github.gonzalezjo.msvcpredict.solver.Scaler;
import com.github.gonzalezjo.msvcpredict.solver.SerialSolver;
import com.github.gonzalezjo.msvcpredict.solver.Solver;
import com.github.gonzalezjo.msvcpredict.solver.State;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.Scanner;

final class App {
    private static final Engine SOLVER_ENGINE = Engine.JVM_SERIAL;

    private App() {
    }

    private static double[] nextNumbers(final double[] samples,
                                        final int amount) throws OperationNotSupportedException {
        final Scaler scaler = new Scaler(samples);
        // final Solver solver;
        // if (SOLVER_ENGINE == Engine.OPENCL)
        //     solver = (Solver) new OpenCLSolver();
        // else
        //     solver = (Solver) new JVMSolver();

        final Solver solver = new SerialSolver();
        final double[] accumulator = new double[amount];

        final short[][] scaled = scaler.scaled();
        final State state = solver.solve(scaled);

        for (int i = 0; i < amount; i++) {
            accumulator[i] = state.next();
        }

        scaler.setToCurrentScale(accumulator);

        return accumulator;
    }

    public static double nextNumber(final double[] samples) { // todo: check consistency
        double nextNumber = -1;
        try {
            return nextNumbers(
                    Arrays.copyOfRange(samples, 0, samples.length - 1),
                    1)[0];
        } catch (OperationNotSupportedException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final String[] numberInput = scanner.nextLine()
                .replace(" ", "")
                .split(",");

        scanner.close();

        final Double[] doubles = Arrays.stream(numberInput)
                .map(Double::valueOf)
                .toArray(Double[]::new);

        System.out.println("Last number: " + doubles[doubles.length]);
        System.out.println("Next 5: " + Arrays.toString(doubles));
    }

    public enum Engine { OPENCL, JVM_PARALLEL, JVM_SERIAL }
}
