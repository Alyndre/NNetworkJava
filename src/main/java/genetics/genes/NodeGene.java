package genetics.genes;

public class NodeGene extends Gene {

    public int id;
    public float bias;
    public NodeType nodeType;

    public enum NodeType {
        INPUT, HIDDEN, OUTPUT
    }

    public NodeGene(int id, float bias, NodeType nodeType) {
        super();
        this.id = id;
        this.bias = bias;
        this.nodeType = nodeType;
    }

    @Override
    public void mutate() {
        this.bias += (Math.random()) * Math.floor(Math.random()*2) == 1 ? 1 : -1;
    }
}
