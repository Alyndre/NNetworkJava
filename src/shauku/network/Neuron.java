package shauku.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Neuron {

    public int id = 0;
    private HashMap<Integer, Connection> inputs;
    private double bias;
    private boolean fired;
    private double data;
    private double output;
    private double error;
    private double value;
    private double derivative;

    public Neuron (double bias, int id) {
        this.id = id;
        this.error = 0.0d;
        this.output = 0.0d;
        this.data = 0.0d;
        this.bias = bias;
        this.derivative = 0.0d;
        fired = false;
        inputs = new HashMap<>();
    }

    public void connect (List<Neuron> neurons) {
        for (Neuron n : neurons) {
            float tmpWeight = (float) Math.random();
            tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
            Connection cn = new Connection(n, this, tmpWeight);
            inputs.put(n.id, cn);
        }
    }

    public void feed (double data) {
        this.data = data;
    }

    public double fire () {
        if (inputs.size() > 0) {
            double totalInput = 0.0;
            for (Map.Entry<Integer, Connection> cEntry : inputs.entrySet()){
                Connection c = cEntry.getValue();
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

    public double calcError(double target){
        error = output - target;
        return error;
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

    public HashMap<Integer, Connection> getInputs() { return inputs; }

    public void setError(double error) {
        this.error = error;
    }

    public double getError() { return error; }

    public void setDerivative(double derivative) { this.derivative = derivative; }

    public double getDerivative() { return derivative; }

    public void setBias(double bias) { this.bias = bias; }

    public double getBias() { return bias; }
}
