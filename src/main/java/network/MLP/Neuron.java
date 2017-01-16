package network.MLP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Neuron {

    int id = 0;
    private HashMap<Integer, Connection> inputs;
    private double bias;
    private boolean fired;
    private double data;
    private double output;
    private double error;
    private double value;
    private double derivative;

    Neuron (double bias, int id) {
        this.id = id;
        this.error = 0.0d;
        this.output = 0.0d;
        this.data = 0.0d;
        this.bias = bias;
        this.derivative = 0.0d;
        fired = false;
        inputs = new HashMap<>();
    }

    void connect (List<Neuron> neurons) {
        for (Neuron n : neurons) {
            float tmpWeight = (float) Math.random();
            tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
            Connection cn = new Connection(n, this, tmpWeight);
            inputs.put(n.id, cn);
        }
    }

    void feed (double data) {
        this.data = data;
    }

    double fire () {
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

    public boolean isFired () {
        return fired;
    }

    private double sigmoid(double value){
        return 1 / (1+Math.pow(Math.E, (-1*value)));
    }

    double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    HashMap<Integer, Connection> getInputs() { return inputs; }

    public void setError(double error) {
        this.error = error;
    }

    public double getError() { return error; }

    void setDerivative(double derivative) { this.derivative = derivative; }

    double getDerivative() { return derivative; }

    public void setBias(double bias) { this.bias = bias; }

    public double getBias() { return bias; }

    /*
    double calcError(double target){
        //error = output - target;
        double log1 = Math.log(output);
        double log2 = Math.log(1-output);
        if (Double.isNaN(log1)){
            log1 = 0;
        }
        if (Double.isNaN(log2)){
            log2 = 0;
        }
        error = -target*log1-(1-target)*log2;
        return error;
    }
*/
}
