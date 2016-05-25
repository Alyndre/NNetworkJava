package shauku.neuronal;

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

        double[] expected = {0, 1, 1, 0};

        Network net = new Network(inputs, outputs, hidden, learnRate);
        net.train(data, expected, 5);
        //net.feed(data[0]);
        net.start();
    }
}
