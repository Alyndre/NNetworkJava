package shauku.neuronal;

//import java.lang.Math;

import shauku.network.Network;
import shauku.network.Neuron;

public class Main {

    public static void main(String[] args) {
        Long inputs = 2L;
        Long outputs = 1L;
        Long[] hidden = {4L};

        Float learnRate = 0.25f;

        Boolean[][] data = {
                {false, false},
                {false, true},
                {true, false},
                {true, true}
        };
        Network net = new Network(inputs, outputs, hidden);
        net.start();
    }

    public static void train(Boolean[][] inputs, Neuron a, Neuron b, Neuron c, Neuron d, Neuron xor) {
        //http://www.nnwj.de/forwardpropagation.html
        //http://www.nnwj.de/backpropagation.html
        int iteration = 0;
        for (Boolean[] values : inputs) {
            for (Boolean val : values) {
                //Input Layer on the fly
                Neuron inputN = new Neuron(0.0f);
                inputN.setWeight(val);
                a.connect(inputN);
                b.connect(inputN);
                c.connect(inputN);
                d.connect(inputN);
            }

            xor.fire();
            System.out.println(iteration + " - Values: " + values[0] + "," + values[1] + " - Result: " + xor.isFired());
            iteration++;
        }
    }
}
