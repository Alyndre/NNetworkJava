package data;

public class MnistData implements Data {

    private double[][] data;
    private double[][] excepted;

    public MnistData () {

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
        return excepted;
    }
}
