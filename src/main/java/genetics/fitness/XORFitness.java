package genetics.fitness;

public class XORFitness extends Fitness {

    private int precision;

    public XORFitness(int precision) {
        super();
        this.precision = precision;
    }

    @Override
    public int fit(float[] output, float[] expected) {
        int res = 0;

        for (int i = 0; i<output.length; i++) {
            if (expected[i] == 0) {
                res += (int) (output[i]*-precision);
            } else {
                res += (int) (output[i]*precision);
            }
        }
        return res;
    }
}
