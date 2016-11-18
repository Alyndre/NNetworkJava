package network.SOM;

import java.util.Vector;

public class EuclideanVector extends Vector {

    EuclideanVector(){}

    public double euclideanDistance(EuclideanVector v2) throws IndexOutOfBoundsException {
        if (v2.size() != this.size()) {
            throw new IndexOutOfBoundsException();
        }

        double summation = 0, temp;
        for (int x=0; x<this.size(); x++) {
            temp = ((Double)elementAt(x)) - ((Double)v2.elementAt(x));
            temp *= temp;
            summation += temp;
        }
        return summation;
    }
}
