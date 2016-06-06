package shauku.network;

import java.util.ArrayList;
public class Network {

    private ArrayList<Neuron> inList;
    private ArrayList<Neuron> outList;
    private ArrayList<ArrayList<Neuron>> hidLayers;
    private float learningRate;
    private float momentum = 1;

    public Network(long inputs, long outputs, long[] hidden, float learnRate){
        System.out.println("Assembling network...");

        hidLayers = new ArrayList<>();
        inList = new ArrayList<>();
        outList = new ArrayList<>();

        System.out.println("Learning rate of: " + this.learningRate);
        this.learningRate = learnRate;

        System.out.println("Creating input layer...");
        for (Long i = 0L; i < inputs; i++){
            Neuron n = new Neuron(0f);
            inList.add(n);
        }
        System.out.println("Input layer created!");

        System.out.println("Creating hidden layers...");
        System.out.println("Number of hidden layers: " + hidden.length);
        for (int x = 0; x<hidden.length; x++){
            long l = hidden[x];
            ArrayList<Neuron> layer = new ArrayList<>();
            for (long i = 0L; i<l; i++){
                Neuron n = new Neuron(0f);
                if (x==0){
                    n.connect(inList);
                } else {
                    n.connect(hidLayers.get(x-1));
                }
                layer.add(n);
            }
            hidLayers.add(layer);
        }
        System.out.println("Hidden layers created!");

        System.out.println("Creating output layer...");
        for (int i = 0; i < outputs; i++){
            Neuron n = new Neuron(0f);
            n.connect(hidLayers.get(hidLayers.size()-1));
            outList.add(n);
        }
        System.out.println("Output layer created!");

        System.out.println("Network online!");
    }

    public void feed(double[] data) {
        if (data.length == inList.size()){
            for (int x = 0; x<data.length; x++) {
                Neuron n = inList.get(x);
                n.feed(data[x]);
            }
        } else {
            System.out.println("ERROR: Data length is different from input neurons");
        }
    }

    public void train(double[][] trainData, double[][] expected, int iterations){
        System.out.println("Training network...");
        if ((expected.length == trainData.length)&&(iterations>0)) {
            for (int j = 0; j<=iterations; j++) {
                System.out.println("Iteration num:" + j);
                for (int i = 0; i < trainData.length; i++) {
                    System.out.println("Train set num:" + i);
                    feed(trainData[i]);
                    backpropagation(expected[i]);
                }
            }
        }
        System.out.println("Training done!");
    }

    private void backpropagation(double[] expected){

        //TODO: Backpropagate the error rate and change weights of connections

        //Start Backpropagation on the output layer
        int i = 0;
        for (Neuron n : outList) {
            ArrayList<Connection> connections = n.getInputs();
            for (Connection con : connections) {
                double output = n.getOutput(); //ak
                double iNeuronOutput = con.getInput().getOutput();  //ai
                double expectedOutput = expected[i]; //desired
                double errorRate = n.calcError(expectedOutput);
                System.out.println("Error rate of output neuron n" + i + ": " + errorRate);
                double partialDerivative = -output * (1 - output) * iNeuronOutput
                        * errorRate;
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
            i++;
        }

        //Next we backpropagate on every hidden layer
        for (ArrayList<Neuron> hiddenLayer : hidLayers) {
            for (Neuron n : hiddenLayer) {
                ArrayList<Connection> connections = n.getInputs();
                for (Connection cn : connections) {

                }
            }
        }

        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double aj = n.getOutput();
                double ai = con.leftNeuron.getOutput();
                double sumKoutputs = 0;
                int j = 0;
                for (Neuron out_neu : outputLayer) {
                    double wjk = out_neu.getConnection(n.id).getWeight();
                    double desiredOutput = (double) expectedOutput[j];
                    double ak = out_neu.getOutput();
                    j++;
                    sumKoutputs = sumKoutputs
                            + (-(desiredOutput - ak) * ak * (1 - ak) * wjk);
                }

                double partialDerivative = aj * (1 - aj) * ai * sumKoutputs;
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
        }
    }

    public void start() {
        System.out.println("");
        outList.stream().map(Neuron::fire).forEach(v -> System.out.println("Neuron output: " + v));
    }

    public void saveNet() {
        System.out.println("Saving...");
        //TODO: Save all the network params in a BBDD or something
    }

    public void loadNet(long netId) {
        System.out.println("Loading network " + netId);
        //TODO: Load a previously saved network
    }

    public ArrayList<Neuron> getInList() {
        return inList;
    }

    public ArrayList<Neuron> getOutList() {
        return outList;
    }

    public ArrayList<ArrayList<Neuron>> getHidLayers() {
        return hidLayers;
    }
}
