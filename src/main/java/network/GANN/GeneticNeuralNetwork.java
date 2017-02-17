package network.GANN;

import genetics.Genome;
import genetics.genes.ConnectionGene;
import genetics.genes.NodeGene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneticNeuralNetwork {

    public boolean debug = false;
    private long numNeurons = 0;

    private List<Neuron> inputNeurons;
    private List<Neuron> hiddenNeurons;
    private List<Neuron> outputNeurons;
    private HashMap<Integer, Neuron> neuronsMap;

    public GeneticNeuralNetwork(Genome genome){
        log("Assembling network...");

        inputNeurons = new ArrayList<>();
        hiddenNeurons = new ArrayList<>();
        outputNeurons = new ArrayList<>();

        for (NodeGene gene : genome.nodeGenes) {
            Neuron n;
            switch (gene.nodeType) {
                case INPUT:
                    n = new Neuron(gene.id, Neuron.OutputFunction.SOFTMAX, 0);
                    inputNeurons.add(n);
                    neuronsMap.put(gene.id, n);
                    break;
                case OUTPUT:
                    n = new Neuron(gene.id, Neuron.OutputFunction.NONE, 0);
                    outputNeurons.add(n);
                    neuronsMap.put(gene.id, n);
                    break;
                case HIDDEN:
                    n = new Neuron(gene.id, Neuron.OutputFunction.SOFTMAX, 0);
                    hiddenNeurons.add(n);
                    neuronsMap.put(gene.id, n);
                    break;
            }
        }

        for (ConnectionGene gene : genome.connectionGenes){
            if (gene.enabled) {
                Neuron n = neuronsMap.get(gene.outputId);
                Neuron ni = neuronsMap.get(gene.inputId);
                n.weights.add(gene.weight);
                n.inputs.add(ni);
                ni.outputs.add(n);
            }
        }

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

    public void log(String message) {
        if (debug) {
            System.out.println(message);
        }
    }
}
