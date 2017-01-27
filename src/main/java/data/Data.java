package data;

import java.io.IOException;

public interface Data {

    void normalizeData();
    float[] getNextData() throws IOException;
    float[] getNextExpected() throws IOException;
    float[][] getData();
    float[][] getExpected();
    int getTotalData();
    void resetData();
    public void shuffle();
}
