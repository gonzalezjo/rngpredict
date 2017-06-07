package com.github.gonzalezjo.msvcpredict;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PredictorTest {
    private static final double[] rand = new double[] {4397, 27865, 15742, 29251, 10948, 23559, 23132, 434, 23200, 305, 24287};
    private static final double[] mathRandom = new double[] {0.84536271248512, 0.24369029816584, 0.87108981597339, 0.056245612964263, 0.19486068300424, 0.50862147892697, 0.44248786889248, 0.32364879299295, 0.20734275337992};
    private static final double[] mathRandomN = new double[] {15, 95, 15, 91, 70, 31, 43, 8, 97, 69, 16, 88, 83, 59, 20, 18, 82, 48, 16, 51, 74, 41, 28, 57, 69, 76, 73, 48, 13, 37, 84, 4, 52, 67, 43, 11, 95, 93, 55, 35, 48, 38, 85, 32, 46, 28, 99, 30, 74, 57, 20, 77, 84, 40, 51, 90, 3, 100, 58, 6, 54, 20, 85, 63, 66, 20, 85, 13, 11, 75, 32, 95, 29, 34, 15, 74, 84, 71, 61, 75, 26, 15, 1, 7, 81, 86, 22, 12, 56, 2, 12, 46, 76, 69, 55, 8, 44, 21, 70, 30};
    // private static final double[] mathRandomN = new double[]{1,1,1,1,1,2,2,2,1,2,2,2,2,1,2,2,1,2,2,1,2,2,2,2,1,1,1,1,2,1,1,1,2,1,1,1,2,1,2,2,1,2,1,2,2,1,2,2,1,2,1,2,2,1,2,2,1,1,1,1,2,2,2,2,1,1,2,1,1,2,1,2,2,2,2,1,1,1,2,2,1,2,1,2,1,2,2,1,2,1,1,1,1,1,1,2,1,1,1,2,2,1,2,2,2,2,1,2,1,2,1,2,2,2,2,2,1,1,1,2,1,1,1,2,1,1,1,1,1,2,2,2,1,1,2,2,2,2,1,2,2,1,1,1,1,2,1,1,1,1,2,2,1,2,2,2,2,2,2,1,2,1,2,2,2,2,2,1,1,1,1,1,2,1,2,1,2,2,2,1,2,2,2,2,1,2,2,2,2,2,2,1,2,1,1,2,2,1,2,1,1,2,2,1,1,2,1,2,1,2,2,1,2,2,1,1,1,2,2,2,1,2,1,2,1,1,2,1,1,2,1,1,1,1,2,1,1,1,1,2,2,1,2,2,2,2,1,1,1,1,2,1,1,2,2,2,2,2,1,2,2,2,2,2,2,2,2,2,1,1,1,2,1,1,1,2,2,1,1,1,2,2,1,1,1,1,1,2,2,1,2,2,1,1,1,1,2,2,1,2,1,2,2,2,1,1,1,2,1,1,2,1,2,2,1,1,2,2,2,2,2,2,2,1,2,1,2,2,1,2,1,2,2,2,2,1,2,2,2,1,2,2,2,2,2,1,1,2,2,1,1,1,2,2,2,1,2,1,2,2,1,1,2,1,2,1,1,2,1,1,2,1,1,1,2,1,1,2,2,1,1,1,1,1,2,2,2,2,2,2,2,2,2,1,1,1,2,2,1,1,2,2,1,1,2,1,1,1,1,2,1,2,2,2,2,2,2,1,2,1,2,2,1,1,1,1,2,1,2,1,1,1,1,1,1,2,2,2,1,1,1,2,1,1,2,1,2,1,2,2,2,1,1,2,2,2,1,2,2,1,2,1,2,2,2,1,2,2,2,2,2,1,1,1,1,2,1,2,2,1,1,1,2,1,1,2,2,1,2,2,1,1,2,1,1,1,1,2,1,1,2,1,2,1,2,1,1,1,1,2,2,2,2,2,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,1,1,1,1,2,2,2,1,2,2,2,2,1,2,1,2,2,2,1,2,1,1,1,1,1,2,2,1,2,2,1,1,2,1,2,2,2,1,2,2,2,2,1,2,1,1,1,2,1,1,1,2,1,2,1,2,2,1,1,2,2,2,1,1,2,1,1,1,2,2,2,2,2,1,2,2,1,2,1,1,2,1,2,1,2,1,2,1,2,2,2,2,1,2,2,2,1,2,1,2,1,1,1,2,1,1,1,1,2,2,1,2,1,2,1,2,2,1,2,1,2,1,2,1,1,1,2,2,2,2,2,1,1,1,1,1,2,1,2,2,2,1,1,2,2,1,1,2,2,1,1,1,2,2,1,2,1,2,1,1,1,1,1,2,1,1,1,1,2,1,1,2,2,1,1,1,2,2,2,2,2,2,2,1,2,1,2,2,1,2,1,1,1,1,1,2,1,1,1,2,2,2,2,1,2,2,2,1,1,1,1,1,1,2,1,1,1,1,2,1,1,2,1,2,1,2,1,1,1,2,1,2,1,2,2,2,2,2,1,1,2,2,2,1,2,2,1,2,2,1,1,2,2,2,2,1,1,1,2,1,1,1,1,2,2,2,2,1,2,1,2,1,1,2,2,1,1,1,1,1,2,2,2,2,2,1,2,1,2,2,1,1,1,1,2,2,2,2,2,2,2,2,2,2,1,1,2,1,1,1,2,1,1,1,2,2,2,1,1,2,2,1,1,1,1,2,1,1,1,1,2,2,1,1,1,1,2,1,2,1,1,2,1,1,1,2,2,2,2,1,2,2,2,2,2,2,1,2,1,1,1,1,1,2,1,2,1,2,1,1,2,1,1,2,2,1,2,1,2,2,1,1,1,2,1,1,1,1,2,2,2,2,1,1,2,2,1,2,2,2,2,2,2,1,2,1,2,2,1,2,1,1,1,2,1,1,1,1,2,2,1,1,1,2,1,2,2,2,2,1,1,1,2,2,2,1,1,2,1,2,1,2,2,1,1,1,2,2,1,1,1,1,1,2,2,1,1,2,1,2,1,1,1,1,2,2,1,1,2,2,1,2,2,1,1,1,1,1,1,2,1,1,2,1,1,2,1,2,1,1,2,1,1,2,2,1,2,1,2,2,1,1,1,1,1,2,2,1,1,2,2,1,1,2,2,2,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,2,2,2,1,2,1,1,1,2,1,2,1,1,2,2,2,2,1,1,1,2,2,2,2,1,2,1,1,2,1,1,1,2,2,1,1,2,1,1,2,1,2,1,1,1,2,2,2,2,1,1,2,1,2,2,1,1,1,1,1,1,2,2,2,1,2,2,2,2,2,1,1,1,2,1,2,1,2,1,1,2,2,1,1,2,1,1,2,2,2,2,1,2,2,1,1,1,2,2,1,1,1,1,1,2,1,1,1,2,2,2,1,1,2,1,2,2,1,1,2,2,1,2,1,2,1,1,1,2,2,2,1,1,1,1,1,1,1,2,2,1,2,2,1,1,2,1,1,1,2,2,2,1,2,2,1,2,2,2,1,1,2,2,1,2,2,1,2,1,1,2,2,1,2,2,1,1,1,2,1,2,2,1,2,1,1,1,1,1,1,1,2,1,1,2,2,2,1,2,1,1,2,1,2,2,1,1,1,2,2,1,1,2,2,2,1,1,2,1,1,2,1,1,2,1,1,2,2,1,1,2,1,2,1,2,1,2,1,1,1,1,2,2,1,1,2,1,2,1,2,1,2,2,2,1,2,2,1,2,2,1,2,1,1,2,2,1,2,1,1,2,1,1,1,2,1,1,1,2,2,2,2,1,1,2,1,1,1,1,1,2,1,1,2,2,2,1,2,1,2,1,2,2,1,2,1,2,2,2,1,1,2,2,1,2,2,1,1,2,2,1,2,2,2,1,2,1,1,1,1,2,1,2,2,2,2,2,2,1,1,1,2,2,1,1,2,2,1,1,2,1,2,1,2,2,1,2,1,2,1,2,2,1,2,1,1,2,2,2,2,2,2,2,2,2,2,1,2,1,1,2,2,2,2,1,1,2,1,1,2,2,1,2,1,2,1,1,2,1,1,1,1,1,1,1,1,1,2,2,2,1,1,1,2,2,1,2,2,2,2,2,1,1,2,2,2,1,2,1,2,2,2,1,1,1,1,2}; // fails where lua doesn't D :

    @Test
    public void testRand() {
        final double[] truncatedSamples = Arrays.copyOfRange(rand, 0, rand.length - 1);
        final double expected = rand[rand.length - 1];
        final double outcome = App.nextNumber(truncatedSamples);
        if (outcome != expected) {
            Assert.fail(String.format("%nExpected: %f%nGot: %f", expected, outcome));
        }
        System.out.println("Passed rand() test.");
    }

    @Test
    public void testMathRandom() {
        final double[] truncatedSamples = Arrays.copyOfRange(mathRandom, 0, mathRandom.length - 1);
        final double expected = mathRandom[mathRandom.length - 1];
        final double outcome = App.nextNumber(truncatedSamples);
        if ((Math.abs(expected - outcome) > 0.01)) {
            Assert.fail(String.format("%nExpected: %f%nGot: %f", expected, outcome));
        }
        System.out.println("Passed math.random() test.");
    }

    @Test
    public void testMathRandomN() {
        final double[] truncatedSamples = Arrays.copyOfRange(mathRandomN, 0, mathRandomN.length - 1);
        final double expected = mathRandomN[mathRandomN.length - 1];
        final double outcome = App.nextNumber(truncatedSamples);
        if (outcome != expected) {
            Assert.fail(String.format("%nExpected: %f%nGot: %f", expected, outcome));
        }
        System.out.println("Passed math.random(n) test.");
    }

}
