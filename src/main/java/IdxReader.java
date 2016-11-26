import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IdxReader {

    public static void main(String[] args) {
        FileInputStream inImage = null;
        FileInputStream inLabel = null;

        String inputImagePath = "src/main/resources/train-images.idx3-ubyte";
        String inputLabelPath = "src/main/resources/train-labels.idx1-ubyte";

        String outputPath = "src/main/resources/";

        int[] hashMap = new int[10];

        try {
            inImage = new FileInputStream(inputImagePath);
            inLabel = new FileInputStream(inputLabelPath);

            int magicNumberImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfRows  = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfColumns = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());

            int magicNumberLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
            int numberOfLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());

            BufferedImage image = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_INT_ARGB);
            int numberOfPixels = numberOfRows * numberOfColumns;
            int[] imgPixels = new int[numberOfPixels];

            for(int i = 0; i < numberOfImages; i++) {

                if(i % 100 == 0) {System.out.println("Number of images extracted: " + i);}

                for(int p = 0; p < numberOfPixels; p++) {
                    System.out.println(inImage.read());
                    int gray = 255 - inImage.read();
                    imgPixels[p] = 0xFF000000 | (gray<<16) | (gray<<8) | gray;
                    System.out.println(imgPixels[p]);
                }

                image.setRGB(0, 0, numberOfColumns, numberOfRows, imgPixels, 0, numberOfColumns);

                int label = inLabel.read();

                hashMap[label]++;
                /*
                File outputfile = new File(outputPath + label + "_0" + hashMap[label] + ".png");

                ImageIO.write(image, "png", outputfile);*/
                break;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inImage != null) {
                try {
                    inImage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inLabel != null) {
                try {
                    inLabel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
