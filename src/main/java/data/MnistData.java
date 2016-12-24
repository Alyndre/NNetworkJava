package data;

import java.io.FileInputStream;
import java.io.IOException;

public class MnistData implements Data {

    private double[][] data;
    private double[][] expected;
    private FileInputStream inImage = null;
    private FileInputStream inLabel = null;
    public int numberOfPixels;
    public int numberOfImages;
    private int dataCount = 0;
    private int expectedCount = 0;
    private int numOutput = 0;

    public MnistData (int numOutput) {
        String inputImagePath = "src/main/resources/train-images.idx3-ubyte";
        String inputLabelPath = "src/main/resources/train-labels.idx1-ubyte";
        try {
            inImage = new FileInputStream(inputImagePath);
            inLabel = new FileInputStream(inputLabelPath);

            int magicNumberImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            numberOfImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfRows = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfColumns = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());

            int magicNumberLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
            int numberOfLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());

            numberOfPixels = numberOfRows * numberOfColumns;
            data = new double[numberOfImages][numberOfPixels];
            expected = new double[numberOfLabels][numOutput];
            this.numOutput = numOutput;
        } catch(Exception e){
            System.out.println("MnistData error: " + e.getMessage());
        }
    }

    @Override
    public double[] getNextData() throws IOException{
        System.out.println("Data num: " + dataCount);
        double[] imgData = new double[numberOfPixels];
        try {
            for (int p = 0; p < numberOfPixels; p++) {
                int x = inImage.read();
                imgData[p] = x;
            }
            data[dataCount] = imgData;
            dataCount++;
            return imgData;
        } catch (IOException e) {
            System.out.println("getNextData error: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public double[] getNextExpected() throws IOException{
        try {
            double[] expected = new double[numOutput];
            int x = inLabel.read();
            System.out.println("Expected: " + x);
            expected[x] = 1;
            this.expected[expectedCount] = expected;
            expectedCount++;
            return expected;
        } catch (IOException e) {
            System.out.println("getNextExcepted error: " + e.getMessage());
            throw e;
        }
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

    @Override
    public int getTotalData() {
        return numberOfImages;
    }
}
