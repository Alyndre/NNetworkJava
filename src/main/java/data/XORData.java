package data;

import java.io.IOException;

public class XORData implements Data {

    public int dataCount = 0;
    public int expectedCount = 0;

    double[][] data = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };

    double[][] expected = {{1,0}, {0,1}, {0,1}, {1,0}};

    public XORData() {
    }

    @Override
    public void normalizeData() {

    }

    @Override
    public double[] getNextData() throws IOException {
        double[] res = data[dataCount];
        dataCount++;
        return res;
    }

    @Override
    public double[] getNextExpected() throws IOException {
        double[] res = expected[expectedCount];
        expectedCount++;
        return res;
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

    @Override
    public void resetData() {
        dataCount = 0;
        expectedCount = 0;
    }
}
