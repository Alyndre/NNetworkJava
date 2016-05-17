package shauku.network;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private ArrayList<Connection> inputs;
    private float threshold;
    private boolean fired;
    private double data;

    public Neuron (float threshold) {
        this.data = 0.0d;
        this.threshold = threshold;
        fired = false;
        inputs = new ArrayList<>();
    }

    public void connect (List<Neuron> neurons) {
        for (Neuron n : neurons) {
            float tmpWeight = (float) Math.random();
            tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
            Connection cn = new Connection(n, this, tmpWeight);
            inputs.add(cn);
        }
    }

    public void feed (double data) {
        this.data = data;
    }

    public double fire () {
        if (inputs.size() > 0) {
            double totalInput = 0.0;
            double totalOutput;
            for (Connection c : inputs){
                totalInput += (c.getInput().fire() * c.getWeight());
            }

            totalOutput = sigmoid(totalInput);
            fired = true;
            return totalOutput;
        }
        else {
            fired = true;
            return data;
        }
    }

    public boolean isFired () {
        return fired;
    }

    private double sigmoid(double value){
        return 1 / (1+Math.pow(Math.E, (-1*value)));
    }
}
