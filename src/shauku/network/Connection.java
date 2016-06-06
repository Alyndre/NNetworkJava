package shauku.network;

public class Connection {

    private Neuron input;
    private Neuron output;
    private double weight;
    private double deltaWeight = 0;
    private double prevDeltaWeight = 0;

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

    public double getDeltaWeight() {
        return deltaWeight;
    }

    public void setDeltaWeight(double deltaWeight) {
        prevDeltaWeight = this.deltaWeight;
        this.deltaWeight = deltaWeight;
    }

    public double getPrevDeltaWeight() {
        return prevDeltaWeight;
    }

    public void setPrevDeltaWeight(double prevDeltaWeight) {
        this.prevDeltaWeight = prevDeltaWeight;
    }
}
