package network.MLP;

import network.Network;
import java.util.ArrayList;

public class MultiLayerPerceptron extends Network {

    private ArrayList<Neuron> inputList;
    private ArrayList<Neuron> outputList;
    private ArrayList<ArrayList<Neuron>> hiddenLayers;
    private float momentum = 1;

    public MultiLayerPerceptron(long inputs, long outputs, long[] hidden){
        super(Type.MLP);
        log("Assembling network...");
        log("Type of network: " + this.getType());

        int id = 0;
        long sumNeurons = 0;

        hiddenLayers = new ArrayList<>();
        inputList = new ArrayList<>();
        outputList = new ArrayList<>();

        log("Creating input layer...");

        for (long i = 0L; i < inputs; i++){
            id++;
            Neuron n = new Neuron(0, id);
            inputList.add(n);
        }
        log("Input layer created!");

        log("Creating hidden layers...");
        log("Number of hidden layers: " + hidden.length);
        for (int x = 0; x<hidden.length; x++){
            long l = hidden[x];
            ArrayList<Neuron> layer = new ArrayList<>();
            for (long i = 0L; i<l; i++){
                id++;
                Neuron n = new Neuron((float)Math.random(), id);
                if (x==0){
                    n.connect(inputList);
                } else {
                    n.connect(hiddenLayers.get(x-1));
                }
                layer.add(n);
            }
            hiddenLayers.add(layer);
        }
        log("Hidden layers created!");

        log("Creating output layer...");
        for (long i = 0; i < outputs; i++){
            id++;
            Neuron n = new Neuron(0, id);
            n.isOutputUnit = true;
            n.connect(hiddenLayers.get(hiddenLayers.size()-1));
            outputList.add(n);
        }
        log("Output layer created!");

        setNumNeurons(sumNeurons);

        log("Network online!");
    }

    public float[] evaluate(float[] data){
        float[] results = new float[outputList.size()];
        if (data.length == inputList.size()){
            for (int x = 0; x<data.length; x++) {
                Neuron n = inputList.get(x);
                n.feed(data[x]);
            }
            for (int x = 0; x<outputList.size(); x++){
                Neuron n = outputList.get(x);
                results[x] = n.fire();
            }
        } else {
            log("ERROR: Data length is different from input neurons");
        }
        float[] softmaxed = softmax(results);
        for (int x = 0; x<outputList.size(); x++){
            Neuron n = outputList.get(x);
            n.setOutput(softmaxed[x]);
        }
        return softmaxed;
    }

    public float[] softmax (float[] outputs) {
        // determine max output sum
        float max = outputs[0];
        for (int i = 0; i < outputs.length; ++i)
            if (outputs[i] > max) max = outputs[i];

        // determine scaling factor -- sum of exp(each val - max)
        double scale = 0.0;
        for (int i = 0; i < outputs.length; ++i)
            scale += Math.exp(outputs[i] - max);

        float[] result = new float[outputs.length];
        for (int i = 0; i < outputs.length; ++i)
            result[i] = (float) (Math.exp(outputs[i] - max) / scale);

        return result; // now scaled so that xi sum to 1.0
    }

    ArrayList<Neuron> getInputList() {
        return inputList;
    }

    ArrayList<Neuron> getOutputList() {
        return outputList;
    }

    ArrayList<ArrayList<Neuron>> getHiddenLayers() {
        return hiddenLayers;
    }

}
