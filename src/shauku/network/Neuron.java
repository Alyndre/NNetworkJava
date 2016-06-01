package shauku.network;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private ArrayList<Connection> inputs;
    private float threshold;
    private boolean fired;
    private double data;
    private double output;

    public Neuron (float threshold) {
        this.output = 0.0d;
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
            for (Connection c : inputs){
                totalInput += (c.getInput().fire() * c.getWeight());
            }

            output = sigmoid(totalInput);
            fired = true;
            return output;
        }
        else {
            fired = true;
            output = data;
            return data;
        }
    }

    public double calcError(double expected){
        return expected - output;
    }
    public boolean isFired () {
        return fired;
    }

    private double sigmoid(double value){
        return 1 / (1+Math.pow(Math.E, (-1*value)));
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }
}
