package genetics.genes;

public class ConnectionGene extends Gene {

    public int inputId;
    public int outputId;
    public float weight;
    public int innovation;
    public boolean enabled;

    public ConnectionGene (int inputId, int outputId, float weight, int innovation, boolean enabled) {
        this.inputId = inputId;
        this.outputId = outputId;
        this.weight = weight;
        this.innovation = innovation;
        this.enabled = enabled;
    }

}
