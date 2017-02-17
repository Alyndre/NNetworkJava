package network.MLP;

import data.Data;
import network.Trainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MLPTrainer extends Trainer {

    private MultiLayerPerceptron multiLayerPerceptron;
    private Data data;
    private float learningRate;
    private float momentum;
    private int iterations;

    public MLPTrainer(MultiLayerPerceptron multiLayerPerceptron, float learningRate, float momentum, Data data, int iterations){
        super();
        this.multiLayerPerceptron = multiLayerPerceptron;
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.data = data;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        train(data, iterations);
        running = false;
    }

    @Override
    protected void train(Data data, int iterations){
        this.multiLayerPerceptron.log("Training network...");
        try {
            for (int j = 0; j<iterations; j++) {
                System.out.println("Epoch:" + j);
                for(int i = 0; i<data.getTotalData(); i++) {//data.getTotalData()
                    float[] d = data.getData()[i];
                    this.multiLayerPerceptron.log("Data: " + d[0] + " - " + d[1]);
                    this.multiLayerPerceptron.evaluate(d);
                    backpropagation(data.getExpected()[i]);
                }
                //data.resetData();
                //data.shuffle();
            }
        } catch (Exception e){
            this.multiLayerPerceptron.log("Train error: " + e.getMessage());
        }
        this.multiLayerPerceptron.log("Training done!");
    }

    @Override
    protected void train(Data data, float stopError) {

    }

    private void backpropagation(float[] expected){
        //SEEMS TO WORK, BUT MAYBE SOMETHING IS WRONG: CHECK https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
        //56s -> 20s!

        Neuron[] outputList = this.multiLayerPerceptron.getOutputList();
        ArrayList<Neuron[]> hiddenLayers = this.multiLayerPerceptron.getHiddenLayers();

        String exp = "Expected: ";
        for(float d : expected) {
            exp+=d+" - ";
        }
        this.multiLayerPerceptron.log(exp);

        //Output layer
        double error = 0;
        int x = 0;
        for (Neuron n : outputList){
            float out = n.getOutput();
            float g = expected[x]-out;
            error -= expected[x] * Math.log(out);
            n.setDerivative(g);
            x++;
        }

        this.multiLayerPerceptron.log("CROSS ENTROPY ERROR: " + error);

        for (int i = hiddenLayers.size()-1; i >= 0; i--){
            Neuron[] l = hiddenLayers.get(i);
            for (int z = 0; z<l.length; z++) {
                Neuron n = l[z];
                //Calculate the sum of the delta of the neurons of next layer times weight of that connection
                float sumK = 0;
                Neuron[] nextLayer;
                if (i+1 == hiddenLayers.size()){
                    nextLayer = outputList;
                } else {
                    nextLayer = hiddenLayers.get(i+1);
                }
                for (int w = 0; w<nextLayer.length; w++){
                    sumK += nextLayer[w].getDerivative() * nextLayer[w].weights[z];
                }
                float oJ = n.getOutput();
                float derivativeJ = derivate(oJ) * sumK;
                n.setDerivative(derivativeJ);
            }
        }

        //Calc deltaWeight
        for (Neuron n : outputList){
            Neuron[] inputs = n.getInputs();
            for (int i = 0; i<inputs.length; i++){
                float deltaWeight = 1*learningRate*n.getDerivative()*inputs[i].getOutput();
                n.weights[i] = n.weights[i] + (deltaWeight * momentum);
            }
            float deltaBias = -1*learningRate*n.getDerivative();
            n.setBias(n.getBias()+deltaBias);
        }

        for (int l = hiddenLayers.size()-1; l >= 0; l--) {
            Neuron[] layer = hiddenLayers.get(l);
            for (Neuron n : layer) {
                Neuron[] inputs = n.getInputs();
                for (int i = 0; i<inputs.length; i++){
                    float deltaWeight = 1*learningRate*n.getDerivative()*inputs[i].getOutput();
                    n.weights[i] = n.weights[i] + (deltaWeight * momentum);
                }
                float deltaBias = 1*learningRate*n.getDerivative();
                n.setBias(n.getBias()+deltaBias);
            }
        }
    }

    private float calcError(float target, float output){
        float error;
        //error = output - target;
        float log1 = (float) Math.log(output);
        float log2 = (float) Math.log(1-output);
        if (Double.isNaN(log1)){
            log1 = 0;
        }
        if (Double.isNaN(log2)){
            log2 = 0;
        }
        error = -target*log1-(1-target)*log2;
        return error;
    }

    private float derivate(float val){
        return val * (1 - val);
    }
}
