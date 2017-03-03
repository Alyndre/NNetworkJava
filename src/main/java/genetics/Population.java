package genetics;

import data.Data;
import genetics.fitness.Fitness;
import network.GANN.GeneticNeuralNetwork;
import network.GANN.NNTrainer;
import network.Trainer;

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

    public void evaluateCurrentPopulation(Data data, Fitness fitness) {
        float[][] d = data.getData();
        float[][] e = data.getExpected();
        for (Genome genome : genomes) {
            GeneticNeuralNetwork geneticNeuralNetwork = new GeneticNeuralNetwork(genome);
            for (int i=0; i<d.length; i++) {
                float[] output = geneticNeuralNetwork.evaluate(d[i]);
                genome.fitness += fitness.fit(output, e[i]);

                System.out.println("Expected: " + e[i][0] + " - " + e[i][1]);
                System.out.println("Network eval: " + output[0] + " - " + output[1]);
            }
            System.out.println("Genome: " + genome.id + " Fitness: " + genome.fitness);
        }
        Collections.sort(genomes);
    }

    public void crossover(Genome genome1, Genome genome2) {
        //TODO: Combine genomes
    }

    public void holocaust() {
        //TODO: Get rid of low fitness genomes
    }

    public void chernovyl() {
        //TODO: Iterate over genes and apply mutations
    }

}
