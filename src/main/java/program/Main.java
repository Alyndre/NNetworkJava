package program;

import data.Data;
import data.MnistData;
import data.XORData;
import genetics.Genome;
import genetics.Population;
import genetics.genes.ConnectionGene;
import genetics.genes.NodeGene;
import network.GANN.GeneticNeuralNetwork;
import network.GANN.NNTrainer;
import network.MLP.MLPTrainer;
import network.MLP.MultiLayerPerceptron;
import network.Trainer;
//import static spark.Spark.*;

public class Main {

    //localhost:4567/
    //http://www.nnwj.de/forwardpropagation.html
    //http://www.nnwj.de/backpropagation.html

    public static void main(String[] args) {

        startXORGANN();
        //startXORMLP();
        //startMnist();
    }

    private static void startXORGANN() {
        Population population = new Population(100);
    }

    private static void startWorkingXORGANN() {

        GeneticNeuralNetwork geneticNeuralNetwork = new GeneticNeuralNetwork(Genome.generateXORExampleGenome());

        float momentum = 3.6f;
        float learnRate = 0.5f;
        Data data = new XORData();

        Trainer trainer = new NNTrainer(geneticNeuralNetwork, learnRate, momentum, data, 500);

        trainer.start();
        try {
            trainer.join();
        } catch (InterruptedException e){
            System.out.println("InterruptedException: " + e.getMessage());
        }

        int i = 0;
        for (float[] d : data.getData()){
            System.out.println(d[0]+","+d[1]);
            float[] res = geneticNeuralNetwork.evaluate(d);
            System.out.println("Expected: " + data.getExpected()[i][0] + " - " + data.getExpected()[i][1]);
            System.out.println("Network eval: " + res[0] + " - " + res[1]);
            i++;
        }
    }

    private static void startXORMLP() {
        int inputs = 2;
        int outputs = 2;
        int[] hidden = {4};
        float momentum = 3.6f;
        float learnRate = 0.5f;

        Data data = new XORData();

        MultiLayerPerceptron network = new MultiLayerPerceptron(inputs, outputs, hidden);
        network.debug = false;
        Trainer trainer = new MLPTrainer( network, learnRate, momentum, data, 500);

        trainer.start();
        try {
            trainer.join();
        } catch (InterruptedException e){
            System.out.println("InterruptedException: " + e.getMessage());
        }

        int i = 0;
        for (float[] d : data.getData()){
            System.out.println(d[0]+","+d[1]);
            float[] res = network.evaluate(d);
            System.out.println("Expected: " + data.getExpected()[i][0] + " - " + data.getExpected()[i][1]);
            System.out.println("Network eval: " + res[0] + " - " + res[1]);
            i++;
        }
    }

    private static void startMnist() {
        String inputImagePath = "src/main/resources/train-images.idx3-ubyte";
        String inputLabelPath = "src/main/resources/train-labels.idx1-ubyte";
        MnistData data = new MnistData(inputImagePath, inputLabelPath, 10);

        String validateImagePath = "src/main/resources/t10k-images.idx3-ubyte";
        String validateLabelPath = "src/main/resources/t10k-labels.idx1-ubyte";
        MnistData validationData = new MnistData(validateImagePath, validateLabelPath, 10);
        validationData.loadData();

        System.out.println("Number of inputs: " + data.numberOfPixels);
        int inputs = data.numberOfPixels;
        int outputs = 10;
        int[] hidden = {30};
        float learnRate = 3f;
        float momentum = 0.6f;

        data.loadData();
        System.out.println("Data loaded!");

        MultiLayerPerceptron network = new MultiLayerPerceptron(inputs, outputs, hidden);
        Trainer trainer = new MLPTrainer(network, learnRate, momentum, data, 500);

        trainer.start();
        try {
            trainer.join();
        } catch (InterruptedException e){
            System.out.println("InterruptedException: " + e.getMessage());
        }

        for (int i = 0; i < 100; i++){
            System.out.println("Expected: " + validationData.expectedNumber[i]);
            float [] res = network.evaluate(validationData.getData()[i]);
            for (double d : res){
                System.out.println("Output: " + d);
            }
        }
    }
}

//PER COMPARAR TOPOLOGIES, ELS PESOS ENTRE AQUESTES AN DE SER LO PUTO IGUAL
