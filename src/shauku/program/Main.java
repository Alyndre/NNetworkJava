package shauku.program;

import shauku.network.Network;

public class Main {

    //http://www.nnwj.de/forwardpropagation.html
    //http://www.nnwj.de/backpropagation.html

    public static void main(String[] args) {
        long inputs = 2L;
        long outputs = 1L;
        long[] hidden = {4L};

        float learnRate = 0.25f;

        double[][] data = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        double[][] expected = {{0}, {1}, {1}, {0}};

        Network net = new Network(inputs, outputs, hidden, learnRate);
        net.train(data, expected, 10000);

        for (double[] d : data){
            System.out.println(d[0]+","+d[1]);
            net.feed(d);
            net.start();
        }
    }
}

//PER COMPARAR TOPOLOGIES, ELS PESOS ENTRE AQUESTES AN DE SER LO PUTO IGUAL
