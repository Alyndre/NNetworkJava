package network.MLP;

class Connection {

    private int id;
    private Neuron input;
    private Neuron output;
    private float weight;

    public Connection(Neuron input, Neuron output, float weight){
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }
}
