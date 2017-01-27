package network.MLP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Neuron {

    int id = 0;
    private HashMap<Integer, Connection> inputs;
    private float bias;
    private boolean fired;
    private float data;
    private float output;
    private float error;
    private float value;
    private float derivative;
    public boolean isOutputUnit = false;

    Neuron (float bias, int id) {
        this.id = id;
        this.error = 0;
        this.output = 0;
        this.data = 0;
        this.bias = bias;
        this.derivative = 0;
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

    void feed (float data) {
        this.data = data;
    }

    float fire () {
        if (inputs.size() > 0) {
            float totalInput = 0;
            for (Map.Entry<Integer, Connection> cEntry : inputs.entrySet()){
                Connection c = cEntry.getValue();
                totalInput += (c.getInput().fire() * c.getWeight());
            }
            value = totalInput+bias;
            if (isOutputUnit) {
                output = value;
            } else {
                output = sigmoid(value);
            }
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

    private float sigmoid(float value){
        return (float) (1 / (1+Math.exp((-1*value))));
    }

    private float hyperTan(float x)
    {
        if (x < -20.0) return -1; // approximation is correct to 30 decimals
        else if (x > 20.0) return 1;
        else return (float) Math.tanh(x);
    }

    float getOutput() {
        return output;
    }

    public void setOutput(float output) {
        this.output = output;
    }

    HashMap<Integer, Connection> getInputs() { return inputs; }

    public void setError(float error) {
        this.error = error;
    }

    public float getError() { return error; }

    void setDerivative(float derivative) { this.derivative = derivative; }

    float getDerivative() { return derivative; }

    public void setBias(float bias) { this.bias = bias; }

    public float getBias() { return bias; }

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
