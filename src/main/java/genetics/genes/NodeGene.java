package genetics.genes;

public class NodeGene extends Gene {

    public int id;
    public float bias;
    public NodeType nodeType;

    public enum NodeType {
        INPUT, HIDDEN, OUTPUT
    }

    public NodeGene(int id, float bias, NodeType nodeType) {
        this.id = id;
        this.bias = bias;
        this.nodeType = nodeType;
    }
}
