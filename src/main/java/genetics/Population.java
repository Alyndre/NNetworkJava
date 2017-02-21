package genetics;

import network.GANN.GeneticNeuralNetwork;

import java.util.ArrayList;
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

    public void evaluateCurrentPopulation(float[] data) {
        for (Genome genome : genomes) {
            GeneticNeuralNetwork geneticNeuralNetwork = new GeneticNeuralNetwork(genome);
            geneticNeuralNetwork.evaluate(data);
        }
    }

}
