package shauku.network;

import shauku.network.MLP.Neuron;

public class Connection {

    private int id;
    private Neuron input;
    private Neuron output;
    private double weight;

    public Connection(Neuron input, Neuron output, double weight){
        this.input = input;
        this.output = output;
        this.weight = weight;
    }

    public Neuron getInput() {
        return input;
    }

    public void setInput(Neuron input) {
        this.input = input;
    }

    public Neuron getOutput() {
        return output;
    }

    public void setOutput(Neuron output) {
        this.output = output;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }
}
