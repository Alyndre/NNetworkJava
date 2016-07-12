package shauku.network;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    public int id = 0;
    private ArrayList<Connection> inputs;
    private float bias;
    private boolean fired;
    private double data;
    private double output;
    private double error;
    private double value;

    public Neuron (float bias, int id) {
        this.id = id;
        this.error = 0.0d;
        this.output = 0.0d;
        this.data = 0.0d;
        this.bias = bias;
        fired = false;
        inputs = new ArrayList<>();
    }

    public void connect (List<Neuron> neurons) {
        for (Neuron n : neurons) {
            float tmpWeight = (float) Math.random();
            tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
            Connection cn = new Connection(n, this, tmpWeight, id);
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
            value = totalInput+bias;
            output = sigmoid(value);
            fired = true;
            return output;
        }
        else {
            fired = true;
            output = data;
            return output;
        }
    }

    public double calcError(double expected){
        error = expected - output;
        return error;
    }

    public boolean isFired () {
        return fired;
    }

    private double sigmoid(double value){
        return 1 / (1+Math.pow(Math.E, (-1*value)));
    }

    private double derivativeSigmoid() {
        if (fired) {
            return output - Math.pow(output, 2);
        } else {
            return 0;
        }
    }

    public double getOutput() {
        if (fired) {
            return output;
        } else {
            return fire();
        }
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public ArrayList<Connection> getInputs() { return inputs; }

    public void setError(double error) {
        this.error = error;
    }

    public double getError() { return error; }
}
