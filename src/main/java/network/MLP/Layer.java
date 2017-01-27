package network.MLP;

import java.util.Arrays;

public class Layer {

    public boolean isSigmoid = true;
    float[] output;
    float[] input;
    float[] weights;
    float[] dweights;

    public Layer (int inputSize, int outputSize) {
        output = new float[outputSize];
        input = new float[inputSize];
        weights = new float[inputSize * outputSize];
        dweights = new float[weights.length];
        initWeights();
    }

    private void initWeights() {
        for (int i = 0; i < weights.length; i++) {
            float tmpWeight = (float) Math.random();
            tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
            weights[i] = tmpWeight;
        }
    }

    public float[] run(float[] in) {
        int offsetPerIteration = in.length;
        int inputArrayLengthExcludingLast = in.length-1;
        for (int i = 0; i < output.length; i++) {
            output[i] = weights[(i * offsetPerIteration) + inputArrayLengthExcludingLast];
        }
        int offs = 0;
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < inputArrayLengthExcludingLast; j++) {
                output[i] += weights[offs + j] * in[j];
            }
            offs += offsetPerIteration;
        }
        if (isSigmoid) {
            for (int i = 0; i < output.length; i++) {
                output[i] = (float) (1 / (1 + Math.exp(-output[i])));
            }
        }
        return Arrays.copyOf(output, output.length);
    }
}
