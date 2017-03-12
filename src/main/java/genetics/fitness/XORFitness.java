package genetics.fitness;

public class XORFitness extends Fitness {

    public XORFitness() {
        super();
    }

    @Override
    public float fit(float[] output, float[] expected) {
        float res = 0;

        for (int i = 0; i<output.length; i++) {
            if (expected[i] == 0) {
                res += 1-output[i];
            } else {
                res += output[i];
            }
        }
        return res;
    }
}
