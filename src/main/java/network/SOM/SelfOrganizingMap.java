package network.SOM;

import network.Network;
import network.Trainer;

public class SelfOrganizingMap extends Network {

    private Trainer trainer;

    public SelfOrganizingMap(int width, int height, int inputDimensions, Trainer trainer) {
        super(Type.SOM);
        System.out.println("Assembling network...");
        System.out.println("Type of network: " + this.getType());

        System.out.println("Creating matrix...");

        Matrix matrix = new Matrix(width, height, inputDimensions);

        System.out.println("Matrix created!");

        System.out.println("Network online!");
    }

    @Override
    public double[] evaluate(double[] data) {
        return new double[0];
    }
}
