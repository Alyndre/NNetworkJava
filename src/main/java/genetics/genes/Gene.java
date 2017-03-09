package genetics.genes;

public abstract class Gene {

    public float mutationRate;
    public int innovation;

    public Gene() {
        innovation = 1;
        mutationRate = (float) Math.random()/50;
    }

    public abstract void mutate();
}
