package shauku.network;

import java.util.ArrayList;

public class Neuron {

    private ArrayList<Neuron> inputs;
    private float weight;
    private float threshold;
    private boolean fired;

    public Neuron (float threshold) {
        this.threshold = threshold;
        fired = false;
        inputs = new ArrayList<>();
        float tmpWeight = (float) Math.random();
        tmpWeight *= Math.floor(Math.random()*2) == 1 ? 1 : -1;
        setWeight( tmpWeight );
    }

    public void connect (Neuron ... ns) {
        for (Neuron n : ns) inputs.add(n);
    }

    public void setWeight (float newWeight) {
        weight = newWeight;
    }

    public void setWeight (boolean newWeight) {
        weight = newWeight ? 1.0f : 0.0f;
    }

    public float getWeight () {
        return weight;
    }

    public float fire () {
        if (inputs.size() > 0) {
            float totalWeight = 0.0f;
            for (Neuron n : inputs) {
                n.fire();
                totalWeight += (n.isFired()) ? n.getWeight() : 0.0f;
            }
            fired = totalWeight > threshold;
            return totalWeight;
        }
        else if (weight != 0.0f) {
            fired = weight > threshold;
            return weight;
        }
        else {
            return 0.0f;
        }
    }

    public boolean isFired () {
        return fired;
    }
}
