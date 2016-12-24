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
    private int iterations;

    public MLPTrainer(MultiLayerPerceptron multiLayerPerceptron, float learningRate, Data data, int iterations){
        super();
        this.multiLayerPerceptron = multiLayerPerceptron;
        this.learningRate = learningRate;
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
        System.out.println("Training network...");
        try {
            for (int j = 0; j<iterations; j++) {
                System.out.println("Iteration num:" + j);
                for(int i = 0; i<data.getTotalData(); i++) {
                    this.multiLayerPerceptron.evaluate(data.getNextData());
                    backpropagation(data.getNextExpected());
                }
            }
        } catch (IOException e){
            System.out.println("Train error: " + e.getMessage());
        }
        System.out.println("Training done!");
    }

    private void backpropagation(double[] expected){
        //SEEMS TO WORK, BUT MAYBE SOMETHING IS WRONG: CHECK https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/

        List<Neuron> outputList = this.multiLayerPerceptron.getOutputList();
        List<ArrayList<Neuron>> hiddenLayers = this.multiLayerPerceptron.getHiddenLayers();

        //Output layer
        int x = 0;
        for (Neuron n : outputList){
            double oK = n.getOutput();
            double eK = n.calcError(expected[x]);
            System.out.println("Actual error rate: " + eK);
            double derivativeK = oK*(1-oK)*eK;
            n.setDerivative(derivativeK);
            x++;
        }

        for (int i = hiddenLayers.size()-1; i >= 0; i--){
            ArrayList<Neuron> l = hiddenLayers.get(i);
            for (Neuron n : l) {
                //Calculate the sum of the delta of the neurons of next layer times weight of that connection
                double sumK = 0;
                List<Neuron> nextLayer;
                if (i+1 == hiddenLayers.size()){
                    nextLayer = outputList;
                } else {
                    nextLayer = hiddenLayers.get(i+1);
                }
                for (Neuron nL : nextLayer){
                    sumK += nL.getDerivative() * nL.getInputs().get(n.id).getWeight();
                }
                double oJ = n.getOutput();
                double derivativeJ = oJ * (1 - oJ) * sumK;
                n.setDerivative(derivativeJ);
            }
        }

        //Calc deltaWeight
        for (Neuron n : outputList){
            for (Map.Entry<Integer, Connection> cEntry : n.getInputs().entrySet()){
                Connection c = cEntry.getValue();
                double deltaWeight = -1*learningRate*n.getDerivative()*c.getInput().getOutput();
                double newWeight = c.getWeight() + deltaWeight;
                c.setWeight(newWeight);
            }
            double deltaBias = -1*learningRate*n.getDerivative();
        }

        for (int i = hiddenLayers.size()-1; i >= 0; i--) {
            ArrayList<Neuron> l = hiddenLayers.get(i);
            for (Neuron n : l) {
                for (Map.Entry<Integer, Connection> cEntry : n.getInputs().entrySet()){
                    Connection c = cEntry.getValue();
                    double deltaWeight = -1*learningRate*n.getDerivative()*c.getInput().getOutput();
                    double newWeight = c.getWeight() + deltaWeight;
                    c.setWeight(newWeight);
                }
                double deltaBias = -1*learningRate*n.getDerivative();
            }
        }
    }

    private static void shuffleTrainSet(double[][] trainData, double[][] expected)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = trainData.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            double[] t = trainData[index];
            trainData[index] = trainData[i];
            trainData[i] = t;

            double[] e = expected[index];
            expected[index] = expected[i];
            expected[i] = e;
        }
    }
}
