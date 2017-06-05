package com.github.gonzalezjo.msvcpredict.scaler;

import com.github.gonzalezjo.msvcpredict.MsvcConstants;

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
        } else if (largest <= (RAND_MAX >> MSVC_THRESHOLD)) {
            maximum = largest;
            mode = InputType.MATH_RANDOM_N;
            // length = 16;
            length = (byte) samples.length;
            // length = (byte) (MSVC_THRESHOLD + Math.ceil(
            //         log2ceil(MODULUS) / (log2floor(MODULUS) - (31 - log2ceil(maximum)))));
            System.out.println(String.format("Attempting math.random(n) mode, where n is %.0f.", maximum));
        } else {
            maximum = RAND_MAX;
            mode = InputType.RAND;
            length = (byte) Math.ceil(
                    log2ceil(MODULUS) / (log2ceil(MODULUS) - SHIFTS));
            System.out.println("Attempting rand() mode (deprecated).");
        }

        // https://forum.wordreference.com/threads/too-few-too-little.2344119/
        if (samples.length < length) {
            throw new IllegalArgumentException(
                    "Too few samples provided.");
        }

        this.samples = Arrays.copyOfRange(
                samples, samples.length - length,
                samples.length);
    }

    public short[][] processable() {
        final short[][] scaled;
        switch (mode) {
            case MATH_RANDOM:
                scaled = new short[1][samples.length];
                for (int i = 0; i < samples.length; i++) { // changed sorted.length to samples.length
                    scaled[0][i] = (short) (samples[i] * RAND_MAX + ROUND_UP);
                    // scaled[0][i] = (short) Math.ceil(samples[i] * RAND_MAX);
                }
                return scaled;
            case MATH_RANDOM_N:
                final double steps = RAND_MAX / maximum;
                scaled = new short[samples.length][RAND_MAX + 1];
                // scaled = new short[samples.length][(int) steps + 1];
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = (samples[i] - 1) * RAND_MAX / maximum; // steps?
                }

                scaled[0][0] = (short) (steps + 1);
                scaled[0][(int) (steps + 2)] = (short) samples.length;
                for (int r = 0; r < steps; r++) {
                    scaled[0][r + 1] = (short) Math.ceil((samples[0] + r));
                }

                // no... this isn't permanent.
                for (int c = 1; c < scaled.length; c++) {
                    scaled[c] = scaled[c -1].clone();
                    for (int r = 0; r < steps; r++) {
                        scaled[c][(int) Math.ceil((samples[c] + r))] = 1;
                    }
                }

                return scaled;
            case RAND:
                scaled = new short[1][samples.length];
                for (int i = 0; i < samples.length; i++) {
                    scaled[0][i] = (short) samples[i];
                }
                return scaled;
            default:
                return new short[][] {{-1}};
        }
    }

    public double[] originalScale(final double[] samples) {
        final double[] output = samples.clone();
        switch (mode) {
            case MATH_RANDOM:
                for (int i = 0; i < output.length; i++) {
                    output[i] = output[i] / RAND_MAX;
                }
                break;
            case MATH_RANDOM_N:
                for (int i = 0; i < output.length; i++) {
                    output[i] = Math.floor(output[i] / (RAND_MAX / maximum) + 1);
                }
                break;
            case RAND:
                break;
        }
        return output;
    }

    private double log2ceil(double n) { // for calculating exactly how much work we can skip
        return Math.ceil(Math.log(n) / Math.log(2));
    }

    private double log2floor(double n) {
        return Math.floor(Math.log(n) / Math.log(2));
    }

    private enum InputType {MATH_RANDOM, MATH_RANDOM_N, RAND}
}
