package genetics.genes;

public abstract class Gene {

    public float mutationRate;

    public Gene() {
        mutationRate = 0.01f;
    }

    public abstract void mutate();
}
