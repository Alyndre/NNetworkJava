package genetics;

import data.Data;
import genetics.fitness.Fitness;
import genetics.genes.ConnectionGene;
import genetics.genes.NodeGene;
import network.GANN.GeneticNeuralNetwork;
import network.GANN.NNTrainer;
import network.Trainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    public List<Genome> genomes;
    private int tournamentSize;
    public Genome bestGenome;
    public float crossoverRate;

    public Population(int size, int tournamentSize) {
        this.tournamentSize = tournamentSize;
        this.crossoverRate = 0.7f;
        this.genomes = new ArrayList<>();

        for (int i = 0; i<size; i++) {
            Genome genome = Genome.generateXORExampleGenome();
            genomes.add(genome);
        }
        this.bestGenome = genomes.get(0);
    }

    public void evaluateCurrentPopulation(Data data, Fitness fitness) {

        //EVAL->HOLO->TOURNAMENT->BREED->MUTATION

        float[][] d = data.getData();
        float[][] e = data.getExpected();
        float totalFitness = 0;
        for (Genome genome : genomes) {
            GeneticNeuralNetwork geneticNeuralNetwork = new GeneticNeuralNetwork(genome);
            genome.fitness = 0;
            for (int i=0; i<d.length; i++) {
                float[] output = geneticNeuralNetwork.evaluate(d[i]);
                genome.fitness += fitness.fit(output, e[i]);
            }
            totalFitness += genome.fitness;
        }

        Collections.sort(genomes);

        List<Genome> sons = new ArrayList<>();

        int numParents = genomes.size()/10;
        if (numParents%2!=0){
            numParents--;
        }

        holocaust(numParents);

        for (int j = 0; j<numParents; j++) {
            Genome[] parents = new Genome[2];
            for (int x = 0; x < 2; x++) {
                float cut = (float) Math.random();
                for (int i = genomes.size() - 1; i >= 0; i--) {
                    Genome g = genomes.get(i);
                    float prob = g.fitness / totalFitness;
                    if (cut <= prob) {
                        parents[x] = g;
                        break;
                    }
                }
            }
            crossover(parents[0], parents[1], sons);
        }


        //Collections.shuffle(parents);

        /*
        Collections.shuffle(genomes);
        List<List<Genome>> tournaments = chopped(genomes, tournamentSize);

        for (List<Genome> t : tournaments){
            Collections.sort(t);

            if (Math.random()<=crossoverRate) {
                crossover(t.get(0), t.get(1), sons);

                parents.add(t.get(0));
                parents.add(t.get(1));
            }
        }*/

        Collections.sort(genomes);
        //holocaust(parents.size());

        genomes.addAll(sons);

        for (Genome g : genomes){
            g.mutation();
        }

        if (genomes.get(0).fitness > bestGenome.fitness) {
            bestGenome = new Genome(genomes.get(0));
        }
    }

    private void crossover(Genome genome1, Genome genome2, List<Genome> sons) {
        Genome son = new Genome();
        Genome daughter = new Genome();
        Genome dad;
        Genome mom;

        if (genome1.fitness >= genome2.fitness) {
            dad = genome1;
            mom = genome2;
        } else {
            dad = genome2;
            mom = genome1;
        }

        float totalFitness = dad.fitness + mom.fitness;
        //double prob1 = dad.fitness / totalFitness;
        if (totalFitness == 0){
            totalFitness = 1;
        }
        double prob2 = mom.fitness / totalFitness;

        for (int x = 0; x<genome1.nodeGenes.size(); x++){
            NodeGene gene1 = dad.nodeGenes.get(x);
            NodeGene gene2 = mom.nodeGenes.get(x);
            if (Math.random()<= prob2) {
                son.nodeGenes.add(gene1);
                daughter.nodeGenes.add(gene2);
            } else {
                son.nodeGenes.add(gene2);
                daughter.nodeGenes.add(gene1);
            }
        }

        for (int x = 0; x<genome1.connectionGenes.size(); x++){
            ConnectionGene gene1 = genome1.connectionGenes.get(x);
            ConnectionGene gene2 = genome2.connectionGenes.get(x);
            if (Math.random()>0.5) {
                son.connectionGenes.add(gene1);
                daughter.connectionGenes.add(gene2);
            } else {
                son.connectionGenes.add(gene2);
                daughter.connectionGenes.add(gene1);
            }
        }
        sons.add(son);
        sons.add(daughter);
    }

    private void holocaust(int deaths) {
        genomes.subList(genomes.size() - deaths, genomes.size()).clear();
    }

    private static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

}
