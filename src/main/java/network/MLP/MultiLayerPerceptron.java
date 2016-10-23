package network.MLP;

import data.Data;
import network.Connection;
import network.Network;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MultiLayerPerceptron extends Network {

    private ArrayList<Neuron> inputList;
    private ArrayList<Neuron> outputList;
    private ArrayList<ArrayList<Neuron>> hiddenLayers;
    private float learningRate;
    private float momentum = 1;

    public MultiLayerPerceptron(long inputs, long outputs, long[] hidden, float learnRate){
        super(Type.MLP);
        System.out.println("Assembling network...");
        System.out.println("Type of network: " + this.getType());

        int id = 0;
        long sumNeurons = 0;

        hiddenLayers = new ArrayList<>();
        inputList = new ArrayList<>();
        outputList = new ArrayList<>();

        System.out.println("Learning rate of: " + this.learningRate);
        this.learningRate = learnRate;

        System.out.println("Creating input layer...");

        for (long i = 0L; i < inputs; i++){
            id++;
            Neuron n = new Neuron(0d, id);
            inputList.add(n);
        }
        System.out.println("Input layer created!");

        System.out.println("Creating hidden layers...");
        System.out.println("Number of hidden layers: " + hidden.length);
        for (int x = 0; x<hidden.length; x++){
            long l = hidden[x];
            ArrayList<Neuron> layer = new ArrayList<>();
            for (long i = 0L; i<l; i++){
                id++;
                Neuron n = new Neuron(0d, id);
                if (x==0){
                    n.connect(inputList);
                } else {
                    n.connect(hiddenLayers.get(x-1));
                }
                layer.add(n);
            }
            hiddenLayers.add(layer);
        }
        System.out.println("Hidden layers created!");

        System.out.println("Creating output layer...");
        for (long i = 0; i < outputs; i++){
            id++;
            Neuron n = new Neuron(0d, id);
            n.connect(hiddenLayers.get(hiddenLayers.size()-1));
            outputList.add(n);
        }
        System.out.println("Output layer created!");

        setNumNeurons(sumNeurons);

        System.out.println("Network online!");
    }

    public void feed(double[] data) {
        if (data.length == inputList.size()){
            for (int x = 0; x<data.length; x++) {
                Neuron n = inputList.get(x);
                n.feed(data[x]);
            }
        } else {
            System.out.println("ERROR: Data length is different from input neurons");
        }
    }

    public void train(Data dataset, int iterations){
        double[][] trainData = dataset.getData();
        double[][] expected = dataset.getExpected();
        System.out.println("Training network...");
        if ((expected.length == trainData.length) && (iterations>0)) {
            for (int j = 0; j<iterations; j++) {
                System.out.println("Iteration num:" + j);
                for (int i = 0; i < trainData.length; i++) {
                    System.out.println("Train set num:" + i);
                    feed(trainData[i]);
                    backpropagation(expected[i]);
                }
                shuffleTrainSet(trainData, expected);
            }
        }
        System.out.println("Training done!");
    }

    private void backpropagation(double[] expected){

        //TODO: Backpropagate the error rate and change weights of connections

        //SOMETHING IS WRONG: CHECK https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/

        //Feedfoward
        outputList.stream().forEach((n)-> {
            n.fire();
        });

        //Output layer
        int x = 0;
        for (Neuron n : outputList){
            double oK = n.getOutput();
            double eK = n.calcError(expected[x]);
            double derivativeK = oK*(1-oK)*eK;
            n.setDerivative(derivativeK);
            x++;
        }

        for (int i = hiddenLayers.size()-1; i >= 0; i--){
            ArrayList<Neuron> l = hiddenLayers.get(i);
            for (Neuron n : l) {
                //Calculate the sum of the delta of the neurons of next layer times weight of that connection
                double sumK = 0;
                ArrayList<Neuron> nextLayer;
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

    public void start() {
        System.out.println("Started!");
        outputList.stream().map(Neuron::fire).forEach(v -> System.out.println("Neuron output: " + v));
    }

    protected static void shuffleTrainSet(double[][] trainData, double[][] expected)
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

    public ArrayList<Neuron> getInputList() {
        return inputList;
    }

    public ArrayList<Neuron> getOutputList() {
        return outputList;
    }

    public ArrayList<ArrayList<Neuron>> getHiddenLayers() {
        return hiddenLayers;
    }

}
