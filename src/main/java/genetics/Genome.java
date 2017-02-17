package genetics;

import genetics.genes.ConnectionGene;
import genetics.genes.NodeGene;
import network.GANN.GeneticNeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class Genome {

    public List<NodeGene> nodeGenes;
    public List<ConnectionGene> connectionGenes;

    public Genome(){
        nodeGenes = new ArrayList<>();
        connectionGenes = new ArrayList<>();
    }

    public Genome(GeneticNeuralNetwork neuralNetwork) {

    }
}
