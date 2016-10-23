package data;

public class XORData implements Data {

    double[][] data = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };

    double[][] expected = {{0}, {1}, {1}, {0}};

    public XORData() {
    }

    @Override
    public void normalizeData() {

    }

    @Override
    public double[][] getData() {
        return data;
    }

    @Override
    public double[][] getExpected() {
        return expected;
    }
}
