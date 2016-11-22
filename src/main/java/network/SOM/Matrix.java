package network.SOM;

public class Matrix {

    public int width;
    public int height;
    public Node[][] matrix;

    Matrix(int width, int height, int inputDimensions) {
        this.width = width;
        this.height = height;
        this.matrix = new Node[width][height];

        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                matrix[x][y] = new Node(inputDimensions);
                matrix[x][y].setX(x);
                matrix[x][y].setY(y);
            }
        }
    }

    public Node getBMU(double[] inputVector) {
        // Start out assuming that 0,0 is our best matching unit
        Node bmu = matrix[0][0];
        double bestDist = euclideanDistance(inputVector, bmu.weights);
        double curDist;

        // Now step through the entire matrix and check the euclidean distance
        // between the input vector and the given node
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                curDist = euclideanDistance(inputVector, matrix[x][y].weights);
                if (curDist < bestDist) {
                    // If the distance from the current node to the input vector
                    // is less than the distance to our current BMU, we have a
                    // new BMU
                    bmu = matrix[x][y];
                    bestDist = curDist;
                }
            }
        }

        return bmu;
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
