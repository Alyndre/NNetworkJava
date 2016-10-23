package program;

import data.Data;
import data.XORData;
import network.MLP.MultiLayerPerceptron;
import network.Network;

public class Main {

    //http://www.nnwj.de/forwardpropagation.html
    //http://www.nnwj.de/backpropagation.html

    public static void main(String[] args) {
        long inputs = 2L;
        long outputs = 1L;
        long[] hidden = {4L};

        float learnRate = 0.25f;

        Data dataset = new XORData();

        Network net = new MultiLayerPerceptron(inputs, outputs, hidden, learnRate);
        net.train(dataset, 10000);

        for (double[] d : dataset.getData()){
            System.out.println(d[0]+","+d[1]);
            net.feed(d);
            net.start();
        }
    }
}

//PER COMPARAR TOPOLOGIES, ELS PESOS ENTRE AQUESTES AN DE SER LO PUTO IGUAL
