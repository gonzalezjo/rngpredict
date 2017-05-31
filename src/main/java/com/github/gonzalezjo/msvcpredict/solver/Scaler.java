package com.github.gonzalezjo.msvcpredict.solver;

import com.github.gonzalezjo.msvcpredict.MsvcConstants;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;

public final class Scaler implements MsvcConstants {
    private static final byte MSVC_THRESHOLD = 3;
    private static final double ROUND_UP = 0.5;
    private final byte length;
    private final double maximum;
    private final double[] samples;
    private final InputType mode;
    public Scaler(final double[] samples) {
        final double[] sorted = Arrays.stream(samples).sorted().toArray();
        final double smallest = sorted[0], largest = sorted[sorted.length - 1];

        if (smallest < 0)
            throw new UnsupportedOperationException(
                    "Cannot handle negative numbers.");
        if (largest == 0)
            throw new UnsupportedOperationException(
                    "Cannot handle largest value of 0.");

        if (smallest >= 0 && largest < 1) {
            maximum = 1;
            mode = InputType.MATH_RANDOM;
            length = (byte) Math.ceil(
                    log2ceil(MODULUS) / (log2ceil(MODULUS) - SHIFTS));
            System.out.println("Attempting math.random() mode.");
        } else if (smallest > (RAND_MAX >> MSVC_THRESHOLD)) {
            maximum = RAND_MAX;
            mode = InputType.MATH_RANDOM_N;
            length = (byte) Math.ceil(
                    log2ceil(MODULUS) / (log2ceil(MODULUS) - SHIFTS));
            System.out.println("Attempting rand() mode (deprecated).");
        } else {
            maximum = largest;
            mode = InputType.RAND;
            length = (byte) (MSVC_THRESHOLD + Math.ceil(
                    log2ceil(MODULUS) / (log2floor(MODULUS) - (31 - log2ceil(maximum)))));
            System.out.println(String.format("Attempting math.random(n) mode, where n is %.0f.", maximum));
        }

        // https://forum.wordreference.com/threads/too-few-too-little.2344119/
        if (samples.length < length) {
            throw new IllegalArgumentException(
                    "Too few samples provided.");
        }

        this.samples = Arrays.copyOfRange(samples, samples.length - length, samples.length);
    }

    public short[][] scaled() throws OperationNotSupportedException {
        final short[][] scaled;
        switch (mode) {
            case RAND:
                scaled = new short[1][samples.length];
                for (int i = 0; i < samples.length; i++) {
                    scaled[0][i] = (short) samples[i];
                }
                break;
            case MATH_RANDOM:
                scaled = new short[1][samples.length];
                for (int i = 0; i < samples.length; i++) { // changed sorted.length to samples.length
                    scaled[0][i] = (short) (samples[i] * RAND_MAX + ROUND_UP);
                }
                break;
            case MATH_RANDOM_N:
                scaled = new short[1][samples.length];
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = (samples[i] - 1) * RAND_MAX / maximum;
                }
                for (int c = 0; c < samples.length; c++) {
                    for (int r = 0; r < scaled[0].length; r++) {
                        scaled[c][r] = (short) Math.ceil((samples[c] + r));
                    }
                }
                break;
            default:
                scaled = null;
                throw new OperationNotSupportedException("Mode supplied is unsupported.");
        }
        return scaled;
    }

    public void setToCurrentScale(final double[] samples) {
        switch (mode) {
            case RAND:
                return;
            case MATH_RANDOM:
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = samples[i] / RAND_MAX;
                }
                return;
            case MATH_RANDOM_N:
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = Math.floor(samples[i] / (RAND_MAX / maximum) + 1);
                }
                return;
        }
    }

    private double log2ceil(double n) { // for calculating exactly how much work we can skip
        return Math.ceil(Math.log(n) / Math.log(2));
    }

    private double log2floor(double n) {
        return Math.floor(Math.log(n) / Math.log(2));
    }

    private enum InputType {RAND, MATH_RANDOM, MATH_RANDOM_N}
}
