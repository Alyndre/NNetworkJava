package genetics.fitness;

public abstract class Fitness {
    public Fitness(){}
    public abstract float fit(float[] output, float[] expected);
}
