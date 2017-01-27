package data;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class XORData implements Data {

    public int dataCount = 0;
    public int expectedCount = 0;

    float[][] data = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };

    float[][] expected = {{1,0}, {0,1}, {0,1}, {1,0}};

    public XORData() {
    }

    @Override
    public void normalizeData() {

    }

    @Override
    public float[] getNextData() throws IOException {
        float[] res = data[dataCount];
        dataCount++;
        return res;
    }

    @Override
    public float[] getNextExpected() throws IOException {
        float[] res = expected[expectedCount];
        expectedCount++;
        return res;
    }

    @Override
    public float[][] getData() {
        return data;
    }

    @Override
    public float[][] getExpected() {
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

    @Override
    public void shuffle()
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = data.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            float[] t = data[index];
            data[index] = data[i];
            data[i] = t;

            float[] e = expected[index];
            expected[index] = expected[i];
            expected[i] = e;
        }
    }
}
