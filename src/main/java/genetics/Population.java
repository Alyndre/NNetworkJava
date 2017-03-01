package genetics;

import network.GANN.GeneticNeuralNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    public List<Genome> genomes;

    public Population(int size) {
        genomes = new ArrayList<>();

        for (int i = 0; i<size; i++) {
            Genome genome = Genome.generateXORExampleGenome();
            genomes.add(genome);
        }
    }

    public void evaluateCurrentPopulation(float[] data, Fitness fitness) {
        for (Genome genome : genomes) {
            GeneticNeuralNetwork geneticNeuralNetwork = new GeneticNeuralNetwork(genome);
            float[] output = geneticNeuralNetwork.evaluate(data);
            genome.fitness = fitness.fitness(output, output);
        }
        Collections.sort(genomes);
    }

}
