package data;

import java.io.IOException;

public interface Data {

    void normalizeData();
    double[] getNextData() throws IOException;
    double[] getNextExpected() throws IOException;
    double[][] getData();
    double[][] getExpected();
    int getTotalData();
    void resetData();
    public void shuffle();
}
