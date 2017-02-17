package network.GANN;

import java.util.ArrayList;
import java.util.List;

class Neuron {

    int id = 0;
    public List<Neuron> inputs;
    public List<Neuron> outputs;
    float[] weights;
    private float bias;
    boolean fired;
    private float data;
    private float output;
    private float derivative;
    public OutputFunction outputFunction;

    public enum OutputFunction {
        SOFTMAX, HYPERTAN, NONE
    }

    Neuron (int id, OutputFunction outputFunction, float bias) {
        this.id = id;
        this.output = 0;
        this.data = 0;
        this.bias = bias;
        this.derivative = 0;
        this.fired = false;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.weights = new float[inputs.size()];
        this.outputFunction = outputFunction;
        for (int x = 0; x < weights.length; x++){
            float tmpWeight = (float) Math.random();
            tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
            this.weights[x] = tmpWeight;
        }
    }

    void feed (float data) {
        this.data = data;
    }

    float fire () {
        if (fired) {
            return output;
        }
        if (inputs.size() > 0) {
            float value = 0;
            for (int i = 0; i < inputs.size(); i++) {
                value += inputs.get(i).fire() * weights[i];
            }
            value += bias;

            switch (outputFunction){
                case SOFTMAX:
                    output = sigmoid(value);
                    break;
                case HYPERTAN:
                    output = hyperTan(value);
                    break;
                case NONE:
                    output = value;
                    break;
                default:
                    output = value;
                    break;
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

    void setDerivative(float derivative) { this.derivative = derivative; }

    float getDerivative() { return derivative; }

    public void setBias(float bias) { this.bias = bias; }

    public float getBias() { return bias; }

}
