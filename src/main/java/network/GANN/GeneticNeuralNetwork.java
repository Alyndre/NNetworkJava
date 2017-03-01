package network.GANN;

import genetics.Genome;
import genetics.genes.ConnectionGene;
import genetics.genes.NodeGene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneticNeuralNetwork {

    public boolean debug = false;
    public Genome genome;
    List<Neuron> inputNeurons;
    List<Neuron> hiddenNeurons;
    List<Neuron> outputNeurons;
    HashMap<Integer, Neuron> neuronsMap;

    public GeneticNeuralNetwork(Genome genome){
        log("Assembling network...");

        this.genome = genome;

        inputNeurons = new ArrayList<>();
        hiddenNeurons = new ArrayList<>();
        outputNeurons = new ArrayList<>();
        neuronsMap = new HashMap<>();

        for (NodeGene gene : genome.nodeGenes) {
            Neuron n;
            switch (gene.nodeType) {
                case INPUT:
                    n = new Neuron(gene.id, Neuron.OutputFunction.SIGMOID, 0);
                    inputNeurons.add(n);
                    neuronsMap.put(gene.id, n);
                    break;
                case OUTPUT:
                    n = new Neuron(gene.id, Neuron.OutputFunction.NONE, 0);
                    outputNeurons.add(n);
                    neuronsMap.put(gene.id, n);
                    break;
                case HIDDEN:
                    n = new Neuron(gene.id, Neuron.OutputFunction.SIGMOID, gene.bias);
                    hiddenNeurons.add(n);
                    neuronsMap.put(gene.id, n);
                    break;
            }
        }

        for (ConnectionGene gene : genome.connectionGenes){
            if (gene.enabled) {
                Neuron n = neuronsMap.get(gene.outputId);
                Neuron ni = neuronsMap.get(gene.inputId);
                n.weights.put(gene.inputId, 0.5f); //gene.weight
                n.inputs.add(ni);
                ni.outputs.add(n);
            }
        }

        log("Network online!");
    }

    public float[] evaluate(float[] data){
        resetNeurons();
        float[] results = new float[outputNeurons.size()];
        if (data.length == inputNeurons.size()){
            for (int x = 0; x<data.length; x++) {
                Neuron n = inputNeurons.get(x);
                n.feed(data[x]);
            }
            for (int x = 0; x<outputNeurons.size(); x++){
                Neuron n = outputNeurons.get(x);
                results[x] = n.fire();
            }
        } else {
            log("ERROR: Data length is different from input neurons");
        }
        float[] softmaxed = softmax(results);
        for (int x = 0; x<outputNeurons.size(); x++){
            Neuron n = outputNeurons.get(x);
            n.output = softmaxed[x];
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
        neuronsMap.forEach((k,v) -> v.fired=false);
    }

    public void log(String message) {
        if (debug) {
            System.out.println(message);
        }
    }
}
