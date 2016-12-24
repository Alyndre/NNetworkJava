package data;

import java.io.IOException;

public class XORData implements Data {

    double[][] data = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };

    double[][] expected = {{1}, {0}, {0}, {1}};

    public XORData() {
    }

    @Override
    public void normalizeData() {

    }

    @Override
    public double[] getNextData() throws IOException {
        return new double[0];
    }

    @Override
    public double[] getNextExpected() throws IOException {
        return new double[0];
    }

    @Override
    public double[][] getData() {
        return data;
    }

    @Override
    public double[][] getExpected() {
        return expected;
    }

    @Override
    public int getTotalData() {
        return data.length;
    }
}
