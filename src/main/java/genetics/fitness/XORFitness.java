package genetics.fitness;

public class XORFitness extends Fitness {

    private int precision;

    public XORFitness(int precision) {
        super();
        this.precision = precision;
    }

    @Override
    public float fit(float[] output, float[] expected) {
        float res = 0;

        for (int i = 0; i<output.length; i++) {
            if (expected[i] == 0) {
                res += (output[i]*-1);
            } else {
                res += (output[i]);
            }
        }
        return res;
    }
}
