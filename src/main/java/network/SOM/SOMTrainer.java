package network.SOM;

import data.Data;
import network.Trainer;

public class SOMTrainer extends Trainer {

    private double LATTICE_RADIUS;
    private double TIME_CONSTANT;

    private int iterations;
    private float learningRate;
    private SelfOrganizingMap selfOrganizingMap;
    private Data data;

    public SOMTrainer(SelfOrganizingMap selfOrganizingMap, float learningRate, Data data, int iterations){
        super();
        this.selfOrganizingMap = selfOrganizingMap;
        this.learningRate = learningRate;
        this.data = data;
        this.iterations = iterations;
        LATTICE_RADIUS = Math.max(selfOrganizingMap.matrix.width, selfOrganizingMap.matrix.height)/2;
        TIME_CONSTANT = iterations / Math.log(LATTICE_RADIUS);
    }

    @Override
    public void run() {
        train(data, iterations);
        running = false;
    }

    @Override
    public void train(Data data, int iterations) {
        //TODO: TRAIN
    }

    public void adjustWeights(Node node, double[] inputs, double learningRate, double distanceFalloff) {
        double weight;
        double input;
        for (int i=0; i<node.weights.length; i++) {
            weight = node.weights[i];
            input = inputs[i];
            weight += distanceFalloff * learningRate * (input - weight);
            node.weights[i] = weight;
        }
    }

    private double getNeighborhoodRadius(double iteration) {
        return LATTICE_RADIUS * Math.exp(-iteration/TIME_CONSTANT);
    }

    private double getDistanceFalloff(double distSq, double radius) {
        double radiusSq = radius * radius;
        return Math.exp(-(distSq)/(2 * radiusSq));
    }
}
