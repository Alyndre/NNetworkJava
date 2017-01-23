package program;

import data.Data;
import data.MnistData;
import data.XORData;
import network.MLP.MLPTrainer;
import network.MLP.MultiLayerPerceptron;
import network.Trainer;
import network.Network;
import static spark.Spark.*;

public class Main {

    //localhost:4567/
    //http://www.nnwj.de/forwardpropagation.html
    //http://www.nnwj.de/backpropagation.html

    public static void main(String[] args) {


        get("/", (request, response) -> {
            long inputs = 2L;
            long outputs = 2L;
            long[] hidden = {4L};

            float learnRate = 0.5f;

            Data data = new XORData();

            Network network = new MultiLayerPerceptron(inputs, outputs, hidden);
            network.debug = true;
            Trainer trainer = new MLPTrainer((MultiLayerPerceptron) network, learnRate, data, 1000);
            trainer.start();

            trainer.join();

            int i = 0;
            for (double[] d : data.getData()){
                System.out.println(d[0]+","+d[1]);
                double[] res = network.evaluate(d);
                System.out.println("Expected: " + data.getExpected()[i][0] + " - " + data.getExpected()[i][1]);
                System.out.println("Network eval: " + res[0] + " - " + res[1]);
                i++;
            }

            return "Ok";
        });

        String inputImagePath = "src/main/resources/train-images.idx3-ubyte";
        String inputLabelPath = "src/main/resources/train-labels.idx1-ubyte";
        MnistData data = new MnistData(inputImagePath, inputLabelPath, 10);

        String validateImagePath = "src/main/resources/t10k-images.idx3-ubyte";
        String validateLabelPath = "src/main/resources/t10k-labels.idx1-ubyte";
        MnistData validationData = new MnistData(validateImagePath, validateLabelPath, 10);
        validationData.loadData();

        get("/mnist", (request, response) -> {
            System.out.println("Number of inputs: " + data.numberOfPixels);
            long inputs = data.numberOfPixels;
            long outputs = 10;
            long[] hidden = {30L};
            float learnRate = 3f;

            Network network = new MultiLayerPerceptron(inputs, outputs, hidden);
            Trainer trainer = new MLPTrainer((MultiLayerPerceptron) network, learnRate, data, 5000);

            trainer.start();
            trainer.join();

            for (int i = 0; i < validationData.getData().length; i++){
                System.out.println("Expected: " + validationData.expectedNumber[i]);
                double [] res = network.evaluate(validationData.getData()[i]);
                for (double d : res){
                    System.out.println("Output: " + d);
                }
            }

            return "Number of inputs: " + data.numberOfPixels;
        });
    }
}

//PER COMPARAR TOPOLOGIES, ELS PESOS ENTRE AQUESTES AN DE SER LO PUTO IGUAL
