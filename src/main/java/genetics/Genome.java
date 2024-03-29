package genetics;

import genetics.genes.ConnectionGene;
import genetics.genes.NodeGene;

import java.util.ArrayList;
import java.util.List;

public class Genome implements Comparable<Genome> {

    private static int idCounter = 0;
    public int id;
    public List<NodeGene> nodeGenes;
    public List<ConnectionGene> connectionGenes;
    public float fitness;

    public Genome(){
        this.id = idCounter++;
        this.nodeGenes = new ArrayList<>();
        this.connectionGenes = new ArrayList<>();
        this.fitness = 0;
    }

    public Genome(Genome g){
        this.id = g.id;
        this.nodeGenes = new ArrayList<>();
        for (NodeGene gene : g.nodeGenes) {
            NodeGene ng = new NodeGene(gene.id, gene.bias, gene.nodeType);
            this.nodeGenes.add(ng);
        }
        this.connectionGenes = new ArrayList<>();
        for (ConnectionGene gene : g.connectionGenes) {
            ConnectionGene ng = new ConnectionGene(gene.inputId, gene.outputId, gene.weight, gene.enabled);
            this.connectionGenes.add(ng);
        }
        this.fitness = g.fitness;
    }

    public void mutation() {
        float mutation = (float) Math.random();
        for (NodeGene g : nodeGenes) {
            if (mutation<g.mutationRate) {
                g.mutate();
            }
        }

        for (ConnectionGene g : connectionGenes) {
            if (mutation<g.mutationRate) {
                g.mutate();
            }
        }
    }

    public static Genome generateBasicGenome(int inputs, int outputs) {
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

    public static Genome generateXORExampleGenome() {
        Genome genome = new Genome();

        //Input genes
        NodeGene gene1 = new NodeGene(1, 0, NodeGene.NodeType.INPUT);
        genome.nodeGenes.add(gene1);
        NodeGene gene2 = new NodeGene(2, 0, NodeGene.NodeType.INPUT);
        genome.nodeGenes.add(gene2);

        //Hidden genes
        NodeGene gene3 = new NodeGene(3, (float)Math.random(), NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene3);
        NodeGene gene4 = new NodeGene(4, (float)Math.random(), NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene4);
        NodeGene gene5 = new NodeGene(5, (float)Math.random(), NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene5);
        NodeGene gene6 = new NodeGene(6, (float)Math.random(), NodeGene.NodeType.HIDDEN);
        genome.nodeGenes.add(gene6);

        //Output genes
        NodeGene gene7 = new NodeGene(7, 0, NodeGene.NodeType.OUTPUT);
        genome.nodeGenes.add(gene7);
        NodeGene gene8 = new NodeGene(8, 0, NodeGene.NodeType.OUTPUT);
        genome.nodeGenes.add(gene8);

        //Connections genes
        float tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 3, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 4, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 5, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(1, 6, tmpWeight, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 3, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 4, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 5, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(2, 6, tmpWeight, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(3, 7, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(3, 8, tmpWeight, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(4, 7, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(4, 8, tmpWeight, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(5, 7, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(5, 8, tmpWeight, true));

        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(6, 7, tmpWeight, true));
        tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        genome.connectionGenes.add(new ConnectionGene(6, 8, tmpWeight, true));
        return genome;
    }

    @Override
    public int compareTo(Genome anotherGenome) {
        int res = -1;
        if (anotherGenome.fitness > this.fitness) {
            res = 1;
        } else if (anotherGenome.fitness == this.fitness){
            res = 0;
        }
        return res;
    }
}
