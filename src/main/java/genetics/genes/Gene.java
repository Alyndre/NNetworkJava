package genetics.genes;

public abstract class Gene {

    public float mutationRate;

    public Gene() {
        mutationRate = (float) Math.random()/40;
    }

    public abstract void mutate();
}
