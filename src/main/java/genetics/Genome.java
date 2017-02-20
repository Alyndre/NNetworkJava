package genetics;

import genetics.genes.ConnectionGene;
import genetics.genes.NodeGene;

import java.util.ArrayList;
import java.util.List;

public class Genome {

    public List<NodeGene> nodeGenes;
    public List<ConnectionGene> connectionGenes;

    public Genome(){
        nodeGenes = new ArrayList<>();
        connectionGenes = new ArrayList<>();
    }

    public static Genome generateRandomGenome(int inputs, int outputs) {
        Genome genome = new Genome();
        //TODO: Generate a new random genome
        for (int i = 0; i<inputs; i++) {
            NodeGene nodeGene = new NodeGene(i, (float)Math.random(), NodeGene.NodeType.INPUT);
            genome.nodeGenes.add(nodeGene);
        }

        for (int i = inputs; i<(outputs+inputs); i++) {
            NodeGene nodeGene = new NodeGene(i, (float)Math.random(), NodeGene.NodeType.OUTPUT);
            genome.nodeGenes.add(nodeGene);
        }

        return genome;
    }
}
