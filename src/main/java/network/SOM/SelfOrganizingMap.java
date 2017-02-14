package network.SOM;

import network.Trainer;

public class SelfOrganizingMap {

    private Trainer trainer;
    public Matrix matrix;

    public SelfOrganizingMap(int width, int height, int inputDimensions, Trainer trainer) {
        System.out.println("Assembling network...");
        System.out.println("Type of network: SOM");

        System.out.println("Creating matrix...");

        matrix = new Matrix(width, height, inputDimensions);

        System.out.println("Matrix created!");

        System.out.println("Network online!");
    }

    public float[] evaluate(float[] data) {
        return new float[0];
    }
}
