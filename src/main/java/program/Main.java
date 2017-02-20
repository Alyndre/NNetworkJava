package program;

import data.Data;
import data.MnistData;
import data.XORData;
import genetics.Genome;
import genetics.genes.ConnectionGene;
import genetics.genes.Gene;
import genetics.genes.NodeGene;
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
        Genome genome = new Genome();

        //Input genes
        NodeGene gene1 = new NodeGene(1, 0, NodeGene.NodeType.INPUT);
        genome.nodeGenes.add(gene1);
        NodeGene gene2 = new NodeGene(2, 0, NodeGene.NodeType.INPUT);
        genome.nodeGenes.add(gene2);

        //Hidden genes
        NodeGene gene3 = new NodeGene(3, 0, NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene3);
        NodeGene gene4 = new NodeGene(4, 0, NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene4);
        NodeGene gene5 = new NodeGene(5, 0, NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene5);
        NodeGene gene6 = new NodeGene(6, 0, NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene6);

        //Output genes
        NodeGene gene7 = new NodeGene(7, 0, NodeGene.NodeType.OUTPUT);
        genome.nodeGenes.add(gene7);
        NodeGene gene8 = new NodeGene(8, 0, NodeGene.NodeType.OUTPUT);
        genome.nodeGenes.add(gene8);

        //Connections genes
        float tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 3, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 4, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 5, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 6, tmpWeight, 0, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 3, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 4, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 5, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 6, tmpWeight, 0, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(3, 7, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(3, 8, tmpWeight, 0, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(4, 7, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(4, 8, tmpWeight, 0, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(5, 7, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(5, 8, tmpWeight, 0, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(6, 7, tmpWeight, 0, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(6, 8, tmpWeight, 0, true));

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
