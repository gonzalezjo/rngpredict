package com.github.gonzalezjo.rngpredict;

import com.github.gonzalezjo.rngpredict.client.predictor.Predictor;
import com.github.gonzalezjo.rngpredict.client.solver.ParallelSolver;
import com.github.gonzalezjo.rngpredict.client.solver.scaler.LuaMsvcScaler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

final class App {

    private App() {
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
        while (numbersScanner.hasNextDouble()) {
            numberList.add(numbersScanner.nextDouble());
        }
        numbersScanner.close();

        final double[] numbers = numberList.stream()
                .sequential()
                .mapToDouble(Double::doubleValue)
                .toArray();

        Predictor predictor = new Predictor(new LuaMsvcScaler(numbers),
                new ParallelSolver());

        System.out.println("Last number: " + numbers[numbers.length - 1]);
        System.out.println("Next 5: " + Arrays.toString(
                predictor.nextNumbers(5)));
    }

}
