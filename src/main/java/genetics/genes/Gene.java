package genetics.genes;

public abstract class Gene {

    public float mutationRate;

    public Gene() {
        mutationRate = (float) Math.random()/20; // 5% MAX
    }

    public abstract void mutate();
}
