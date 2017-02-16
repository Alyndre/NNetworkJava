package network.NN;

import data.Data;
import network.Trainer;

import java.util.*;

public class MLPTrainer extends Trainer {

    private NeuralNetwork neuralNetwork;
    private Data data;
    private float learningRate;
    private float momentum;
    private int iterations;

    public MLPTrainer(NeuralNetwork neuralNetwork, float learningRate, float momentum, Data data, int iterations){
        super();
        this.neuralNetwork = neuralNetwork;
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
        this.neuralNetwork.log("Training network...");
        try {
            for (int j = 0; j<iterations; j++) {
                long startTime = new Date().getTime();
                for(int i = 0; i<data.getTotalData(); i++) {//data.getTotalData()
                    float[] d = data.getData()[i];
                    this.neuralNetwork.log("Data: " + d[0] + " - " + d[1]);
                    this.neuralNetwork.evaluate(d);
                    backpropagation(data.getExpected()[i]);
                }
                //data.resetData();
                //data.shuffle();
                long finishTime = new Date().getTime();
                long time = finishTime - startTime;
                System.out.println("Epoch num. " + (j+1) + " of " + iterations + " Completed in: " + time + " data processed: " + data.getTotalData());
            }
        } catch (Exception e){
            this.neuralNetwork.log("Train error: " + e.getMessage());
        }
        this.neuralNetwork.log("Training done!");
    }

    @Override
    protected void train(Data data, float stopError){

    }

    private void backpropagation(float[] expected){
        //56s -> 20s!

        Neuron[] outputList = this.neuralNetwork.getOutputList();
        ArrayList<Neuron[]> hiddenLayers = this.neuralNetwork.getHiddenLayers();

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

        this.neuralNetwork.log("CROSS ENTROPY ERROR: " + error);

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
                //TODO: cambiar hiddenLayers per a que sigui nomes un llistat de neurones. S'ha de cambiar com agafar les neurones que reben a X com a input
                for (int w = 0; w<nextLayer.length; w++){
                    sumK += nextLayer[w].getDerivative() * nextLayer[w].weights[z];
                }
                float oJ = n.getOutput();
                float derivativeJ = derivate(oJ) * sumK;
                n.setDerivative(derivativeJ);

                Neuron[] inputs = n.getInputs();
                for (int w = 0; w<inputs.length; w++){
                    float deltaWeight = 1*learningRate*n.getDerivative()*inputs[w].getOutput();
                    n.weights[w] = n.weights[w] + (deltaWeight * momentum);
                }
                float deltaBias = 1*learningRate*n.getDerivative();
                n.setBias(n.getBias()+deltaBias);

            }
        }

        //Calc deltaWeight of outputLayer
        for (Neuron n : outputList){
            Neuron[] inputs = n.getInputs();
            for (int i = 0; i<inputs.length; i++){
                float deltaWeight = 1*learningRate*n.getDerivative()*inputs[i].getOutput();
                n.weights[i] = n.weights[i] + (deltaWeight * momentum);
            }
            float deltaBias = -1*learningRate*n.getDerivative();
            n.setBias(n.getBias()+deltaBias);
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
