package shauku.neuronal;

//import java.lang.Math;

public class Main {

    public static void main(String[] args) {
        Float learnRate = 0.25f;

        Boolean[][] inputs = {
                {false, false},
                {false, true},
                {true, false},
                {true, true}
        };

        //Output Layer
        Neuron xor = new Neuron(0.5f);

        //Hidden Layer
        Neuron a = new Neuron(1.5f);
        Neuron b = new Neuron(0.5f);
        Neuron c = new Neuron(1.0f);
        Neuron d = new Neuron(0.0f);

        /* Semi Optimal Weights */
        /*a.setWeight(-1.0f);
        b.setWeight(1.0f);
        c.setWeight(-0.5f);
        d.setWeight(0.5f);*/

        System.out.println("Weights: ");
        System.out.println("A: " + a.getWeight());
        System.out.println("B: " + b.getWeight());
        System.out.println("C: " + c.getWeight());
        System.out.println("D: " + d.getWeight());

        xor.connect(a, b, c, d);

        train(inputs, a, b, c, d, xor);
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
