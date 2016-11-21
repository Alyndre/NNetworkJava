package network.SOM;

import java.util.ArrayList;

public class Node {

    public double[] weights;

    Node (int numWeights){
        weights = new double[numWeights];
        for (int x=0; x<numWeights; x++) {
            weights[x] = Math.random();
        }
    }
}
