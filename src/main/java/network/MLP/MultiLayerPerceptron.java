package network.MLP;

import java.util.ArrayList;

public class MultiLayerPerceptron {

    public boolean debug = false;
    private long numNeurons = 0;

    private Neuron[] inputList;
    private Neuron[] outputList;
    private ArrayList<Neuron[]> hiddenLayers;

    public MultiLayerPerceptron(int inputs, int outputs, int[] hidden){
        log("Assembling network...");
        log("Type of network: MLP");

        int id = 0;
        int sumNeurons = 0;

        inputList = new Neuron[inputs];
        hiddenLayers = new ArrayList<>();
        outputList = new Neuron[outputs];

        log("Creating input layer...");

        for (int i = 0; i < inputs; i++){
            id++;
            Neuron n = new Neuron(id, new Neuron[0], Neuron.OutputFunction.SOFTMAX, 0);
            inputList[i] = n;
        }
        log("Input layer created!");

        log("Creating hidden layers...");
        log("Number of hidden layers: " + hidden.length);
        for (int x = 0; x<hidden.length; x++){
            int l = hidden[x];
            Neuron[] prevLayer;
            Neuron[] layer = new Neuron[l];
            for (int i = 0; i<l; i++){
                id++;
                if (x==0){
                    prevLayer = inputList;
                } else {
                    prevLayer = hiddenLayers.get(x-1);
                }
                Neuron n = new Neuron(id, prevLayer, Neuron.OutputFunction.SOFTMAX, (float)Math.random());
                layer[i] = n;
            }
            hiddenLayers.add(layer);
        }
        log("Hidden layers created!");

        log("Creating output layer...");
        for (int i = 0; i < outputs; i++){
            id++;
            Neuron n = new Neuron(id, hiddenLayers.get(hiddenLayers.size()-1), Neuron.OutputFunction.NONE, 0);
            n.isOutputUnit = true;
            outputList[i] = n;
        }
        log("Output layer created!");

        setNumNeurons(sumNeurons);

        log("Network online!");
    }

    public float[] evaluate(float[] data){
        resetNeurons();
        float[] results = new float[outputList.length];
        if (data.length == inputList.length){
            for (int x = 0; x<data.length; x++) {
                Neuron n = inputList[x];
                n.feed(data[x]);
            }
            for (int x = 0; x<outputList.length; x++){
                Neuron n = outputList[x];
                results[x] = n.fire();
            }
        } else {
            log("ERROR: Data length is different from input neurons");
        }
        float[] softmaxed = softmax(results);
        for (int x = 0; x<outputList.length; x++){
            Neuron n = outputList[x];
            n.setOutput(softmaxed[x]);
        }

        return softmaxed;
    }

    private float[] softmax (float[] outputs) {
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

    private void resetNeurons() {
        for(Neuron n : inputList) {
            n.fired = false;
        }

        for(Neuron n : outputList) {
            n.fired = false;
        }

        for(Neuron[] neurons : hiddenLayers) {
            for (Neuron n : neurons) {
                n.fired = false;
            }
        }
    }

    Neuron[] getInputList() {
        return inputList;
    }

    Neuron[] getOutputList() {
        return outputList;
    }

    ArrayList<Neuron[]> getHiddenLayers() {
        return hiddenLayers;
    }

    public void log(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

    public void setNumNeurons(long numNeurons) {
        this.numNeurons = numNeurons;
    }

}
