package network.MLP;

class Neuron {

    int id = 0;
    private Neuron[] inputs;
    float[] weights;
    private float bias;
    boolean fired;
    private float data;
    private float output;
    private float derivative;
    boolean isOutputUnit = false;

    Neuron (float bias, int id, Neuron[] inputs) {
        this.id = id;
        this.output = 0;
        this.data = 0;
        this.bias = bias;
        this.derivative = 0;
        fired = false;
        this.inputs = inputs;
        this.weights = new float[inputs.length];
        for (int x = 0; x < weights.length; x++){
            float tmpWeight = (float) Math.random();
            tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
            weights[x] = tmpWeight;
        }
    }

    void feed (float data) {
        this.data = data;
    }

    float fire () {
        if (fired) {
            return output;
        }
        if (inputs.length > 0) {
            float value = 0;
            for (int i = 0; i < inputs.length; i++) {
                value += inputs[i].fire() * weights[i];
            }
            value += bias;
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

    Neuron[] getInputs() { return inputs; }

    void setDerivative(float derivative) { this.derivative = derivative; }

    float getDerivative() { return derivative; }

    public void setBias(float bias) { this.bias = bias; }

    public float getBias() { return bias; }

}
