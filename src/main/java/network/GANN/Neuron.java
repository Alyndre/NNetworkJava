package network.GANN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Neuron {

    int id = 0;
    List<Neuron> inputs;
    List<Neuron> outputs;
    HashMap<Integer, Float> weights;
    float bias;
    boolean fired;
    private float data;
    float output;
    float derivative;
    private OutputFunction outputFunction;

    public enum OutputFunction {
        SIGMOID, HYPERTAN, NONE
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
        this.weights = new HashMap<>();
        this.outputFunction = outputFunction;
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
            for (Neuron ni : inputs) {
                value += ni.fire() * weights.get(ni.id);
            }
            /*for (int i = 0; i < inputs.size(); i++) {
                value += inputs.get(i).fire() * weights.get(i);
            }*/
            //value += bias;

            switch (outputFunction){
                case SIGMOID:
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

}
