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

    public Population(int size, int tournamentSize) {
        this.tournamentSize = tournamentSize;
        this.genomes = new ArrayList<>();

        for (int i = 0; i<size; i++) {
            Genome genome = Genome.generateXORExampleGenome();
            genomes.add(genome);
        }
        this.bestGenome = genomes.get(0);
    }

    public void evaluateCurrentPopulation(Data data, Fitness fitness) {

        for (Genome g : genomes){
            g.mutation();
        }

        float[][] d = data.getData();
        float[][] e = data.getExpected();
        for (Genome genome : genomes) {
            GeneticNeuralNetwork geneticNeuralNetwork = new GeneticNeuralNetwork(genome);
            genome.fitness = 0;
            for (int i=0; i<d.length; i++) {
                float[] output = geneticNeuralNetwork.evaluate(d[i]);
                genome.fitness += fitness.fit(output, e[i]);
            }
        }

        Collections.shuffle(genomes);
        List<List<Genome>> tournaments = chopped(genomes, tournamentSize);
        List<Genome> parents = new ArrayList<>();

        int sumParentsFitness = 0;
        for (List<Genome> t : tournaments){
            Collections.sort(t);
            if (Math.floor(Math.random()*2) == 1) {
                crossover(t.get(0), t.get(1));
            } else {
                crossover(t.get(1), t.get(0));
            }

            parents.add(t.get(0));
            parents.add(t.get(1));/*
            sumParentsFitness += (t.get(0).fitness + t.get(1).fitness);*/
        }

        //TODO: SELECT PARENTS BY % OF THEIR FITNESS

        Collections.sort(genomes);
        holocaust(parents.size());

        if (genomes.get(0).fitness > bestGenome.fitness) {
            bestGenome = new Genome(genomes.get(0));
        }
    }

    private void crossover(Genome genome1, Genome genome2) {
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

        int totalFitness = dad.fitness + mom.fitness;
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
        genomes.add(son);
        genomes.add(daughter);
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
