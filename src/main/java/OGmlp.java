import data.MnistData;

import java.util.Arrays;
import java.util.Random;

public class OGmlp {

    public static class MLPLayer {

        float[] output;
        float[] input;
        float[] weights;
        float[] dweights;
        boolean isSigmoid = true;

        public MLPLayer(int inputSize, int outputSize, Random r) {
            output = new float[outputSize];
            input = new float[inputSize + 1];
            weights = new float[(1 + inputSize) * outputSize];
            dweights = new float[weights.length];
            initWeights(r);
        }

        public void setIsSigmoid(boolean isSigmoid) {
            this.isSigmoid = isSigmoid;
        }

        public void initWeights(Random r) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] = (r.nextFloat() - 0.5f) * 4f;
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
            } else {
                //output = softmax(output);
            }
            return Arrays.copyOf(output, output.length);
        }

        public float[] softmax (float[] outputs) {
            // determine max output sum
            float max = outputs[0];
            for (int i = 0; i < outputs.length; ++i)
                if (outputs[i] > max) max = outputs[i];

            // determine scaling factor -- sum of exp(each val - max)
            double scale = 0.0;
            for (int i = 0; i < outputs.length; ++i)
                scale += Math.exp(outputs[i] - max);

            float[] result = new float[outputs.length];
            for (int i = 0; i < outputs.length; ++i)
                result[i] = (float) (Math.exp(outputs[i] - max) / scale);

            return result; // now scaled so that xi sum to 1.0
        }

        public float[] train(float[] error, float learningRate, float momentum) {
            int offs = 0;
            float[] nextError = new float[input.length];
            for (int i = 0; i < output.length; i++) {
                float d = error[i];
                if (isSigmoid) {
                    d *= output[i] * (1 - output[i]);
                }
                for (int j = 0; j < input.length; j++) {
                    int idx = offs + j;
                    nextError[j] += weights[idx] * d;
                    float dw = input[j] * d * learningRate;
                    weights[idx] += dweights[idx] * momentum + dw;
                    dweights[idx] = dw;
                }
                offs += input.length;
            }
            return nextError;
        }
    }
    MLPLayer[] layers;

    public OGmlp(int inputSize, int[] layersSize) {
        layers = new MLPLayer[layersSize.length];
        Random r = new Random(1234);
        for (int i = 0; i < layersSize.length; i++) {
            int inSize = i == 0 ? inputSize : layersSize[i - 1];
            layers[i] = new MLPLayer(inSize, layersSize[i], r);
        }
    }

    public MLPLayer getLayer(int idx) {
        return layers[idx];
    }

    public float[] run(float[] input) {
        float[] actIn = input;
        for (int i = 0; i < layers.length; i++) {
            actIn = layers[i].run(actIn);
        }
        return actIn;
    }

    public void train(float[] input, float[] targetOutput, float learningRate, float momentum) {
        float[] calcOut = run(input);
        float[] error = new float[calcOut.length];
        for (int i = 0; i < error.length; i++) {
            error[i] = targetOutput[i] - calcOut[i]; // negative error
        }
        for (int i = layers.length - 1; i >= 0; i--) {
            error = layers[i].train(error, learningRate, momentum);
        }
    }

    public static void main(String[] args) throws Exception {
        String inputImagePath = "src/main/resources/train-images.idx3-ubyte";
        String inputLabelPath = "src/main/resources/train-labels.idx1-ubyte";
        MnistData data = new MnistData(inputImagePath, inputLabelPath, 10);
        data.loadData();
        System.out.println("DATA LOADED!");

        String validateImagePath = "src/main/resources/t10k-images.idx3-ubyte";
        String validateLabelPath = "src/main/resources/t10k-labels.idx1-ubyte";
        MnistData validationData = new MnistData(validateImagePath, validateLabelPath, 10);
        validationData.loadData();
        System.out.println("VALIDATION LOADED!");

        float[][] train = data.getData();
        float[][] res = data.getExpected();

        OGmlp mlp = new OGmlp(data.numberOfPixels, new int[]{30, 10});
        mlp.getLayer(1).setIsSigmoid(false);

        int en = 10000;
        for (int e = 0; e < en; e++) {

            for (int i = 0; i < res.length; i++) {
                mlp.train(train[i], res[i], 2.5f, 0.6f);
            }
            System.out.println("Epoch: " + e);

            if ((e + 1) % 500 == 0) {
                System.out.println();
                for (int i = 0; i < validationData.getData().length; i++) {
                    float[] t = validationData.getData()[i];
                    float[] results = mlp.run(t);
                    System.out.print("Validation Epoch: " + (e+1));
                    System.out.println(" - Expected: " + validationData.expectedNumber[i]);
                    int w = 0;
                    for (float f : results) {
                        System.out.println("Output nº" + w + " Get: " + f + " - ");
                        w++;
                    }
                }
            }
        }
    }
}