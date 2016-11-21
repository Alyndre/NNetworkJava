package network.SOM;

import java.util.ArrayList;

public class Matrix {

    public int width;
    public int height;
    public Node[][] matrix;

    Matrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new Node[width][height];
    }

    public double euclideanDistance(double[] v1, double[] v2) throws IndexOutOfBoundsException {
        if (v1.length != v2.length) {
            throw new IndexOutOfBoundsException();
        }

        double summation = 0, temp;
        for (int x=0; x<v1.length; x++) {
            temp = v1[x] - v2[x];
            temp *= temp;
            summation += temp;
        }
        return summation;
    }
}
