package network.GANN;

import data.Data;
import network.Trainer;

import java.util.*;

public class NNTrainer extends Trainer {

    private GeneticNeuralNetwork geneticNeuralNetwork;
    private Data data;
    private float learningRate;
    private float momentum;
    private int iterations;

    public NNTrainer(GeneticNeuralNetwork geneticNeuralNetwork, float learningRate, float momentum, Data data, int iterations){
        super();
        this.geneticNeuralNetwork = geneticNeuralNetwork;
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
        this.geneticNeuralNetwork.log("Training network...");
        try {
            for (int j = 0; j<iterations; j++) {
                long startTime = new Date().getTime();
                for(int i = 0; i<data.getTotalData(); i++) {//data.getTotalData()
                    float[] d = data.getData()[i];
                    this.geneticNeuralNetwork.log("Data: " + d[0] + " - " + d[1]);
                    this.geneticNeuralNetwork.evaluate(d);
                    backpropagation(data.getExpected()[i]);
                }
                //data.resetData();
                //data.shuffle();
                long finishTime = new Date().getTime();
                long time = finishTime - startTime;
                //System.out.println("Epoch num. " + (j+1) + " of " + iterations + " Completed in: " + time + " data processed: " + data.getTotalData());
            }
        } catch (Exception e){
            this.geneticNeuralNetwork.log("Train error: " + e.getMessage());
        }
        this.geneticNeuralNetwork.log("Training done!");
    }

    @Override
    protected void train(Data data, float stopError){

    }

    private void backpropagation(float[] expected){
        List<Neuron> outputNeurons = this.geneticNeuralNetwork.outputNeurons;
        List<Neuron> hiddenNeurons = this.geneticNeuralNetwork.hiddenNeurons;

        //Output layer
        double error = 0;
        int x = 0;
        for (Neuron n : outputNeurons){
            float out = n.output;
            float g = expected[x]-out;
            error -= expected[x] * Math.log(out);
            n.derivative = g;
            x++;
        }

        this.geneticNeuralNetwork.log("CROSS ENTROPY ERROR: " + error);


        for (Neuron n : outputNeurons){
            List<Neuron> inputs = n.inputs;
            calcDerivative(inputs);
        }

        //Calc deltaWeight of outputLayer
        for (Neuron n : outputNeurons){
            List<Neuron> inputs = n.inputs;
            for (Neuron ni : inputs) {
                float deltaWeight = 1*learningRate*n.derivative*ni.output;
                float lastWeight = n.weights.get(ni.id);
                n.weights.put(ni.id, lastWeight + (deltaWeight*momentum));
            }
            float deltaBias = -1*learningRate*n.derivative;
            n.bias += deltaBias;
        }

        for (Neuron n : hiddenNeurons) {
            List<Neuron> inputs = n.inputs;
            for (Neuron ni : inputs) {
                float deltaWeight = 1*learningRate*n.derivative*ni.output;
                float lastWeight = n.weights.get(ni.id);
                n.weights.put(ni.id, lastWeight + (deltaWeight*momentum));
            }
            float deltaBias = -1*learningRate*n.derivative;
            n.bias += deltaBias;
        }
    }

    private void calcDerivative(List<Neuron> neurons) {
        for (int x = 0; x < neurons.size(); x++){
            Neuron n = neurons.get(x);
            float sumK = 0;
            for (Neuron no : n.outputs){
                sumK += no.derivative * no.weights.get(n.id);
            }
            float output = n.output;
            n.derivative = derivate(output) * sumK;
        }

        for (Neuron n : neurons){
            if (n.inputs.size() != 0){
                calcDerivative(n.inputs);
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
