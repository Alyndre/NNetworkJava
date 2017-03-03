package genetics.fitness;

public abstract class Fitness {
    public Fitness(){}
    public abstract int fit(float[] output, float[] expected);
}
