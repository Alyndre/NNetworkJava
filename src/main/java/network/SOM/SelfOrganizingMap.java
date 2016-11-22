package network.SOM;

import data.Data;
import network.Network;

public class SelfOrganizingMap extends Network {

    public SelfOrganizingMap(int width, int height) {
        super(Type.SOM);
    }

    @Override
    public double[] evaluate(double[] data) {
        return new double[0];
    }
}
