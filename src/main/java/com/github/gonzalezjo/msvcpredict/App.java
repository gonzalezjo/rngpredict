package com.github.gonzalezjo.msvcpredict;

import com.github.gonzalezjo.msvcpredict.solver.Scaler;
import com.github.gonzalezjo.msvcpredict.solver.SerialSolver;
import com.github.gonzalezjo.msvcpredict.solver.Solver;
import com.github.gonzalezjo.msvcpredict.solver.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

final class App {
    private static final Engine SOLVER_ENGINE = Engine.JVM_SERIAL;

    private App() {
    }

    private static double[] nextNumbers(final double[] samples,
                                        final int amount) {
        final Scaler scaler = new Scaler(samples);
        final Solver solver = new SerialSolver();
        final short[][] scaled = scaler.processable(); // would like to make an interface, then allow custom inputs and scalers

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

    public static double nextNumber(final double[] samples) { // todo: check consistency
        return nextNumbers(Arrays.copyOfRange(
                samples, 0, samples.length - 1),
                1)[0];
    }

    public static void main(final String[] args) {
        System.out.println("Input a series of randomly generated numbers.");

        final Scanner userInputScanner = new Scanner(System.in);
        final String userInput = userInputScanner
                .nextLine()
                .replaceAll("[^\\d]+", " ")
                .trim();
        userInputScanner.close();

        final Scanner numbersScanner = new Scanner(userInput);
        final List<Double> numberList = new ArrayList<>();
        while (numbersScanner.hasNextDouble())
            numberList.add(numbersScanner.nextDouble());

        final double[] parsedInput = numberList.stream()
                .sequential()
                .mapToDouble(Double::doubleValue)
                .toArray();

        System.out.println("Last number: " + parsedInput[parsedInput.length - 1]);
        System.out.println("Next 5: " + Arrays.toString(
                nextNumbers(parsedInput, 5)));
    }

    public enum Engine { OPENCL, JVM_PARALLEL, JVM_SERIAL }
}
