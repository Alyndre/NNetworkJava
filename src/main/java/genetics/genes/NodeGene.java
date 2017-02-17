package genetics.genes;

public class NodeGene extends Gene {

    public int id;
    public int bias;
    public NodeType nodeType;

    public enum NodeType {
        INPUT, HIDDEN, OUTPUT
    }

    public NodeGene(int id, int bias, NodeType nodeType) {
        this.id = id;
        this.bias = bias;
        this.nodeType = nodeType;
    }
}
