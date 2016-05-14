package shauku.network;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Dani on 12/05/2016.
 */
public class Network {

    private ArrayList<Neuron> inList;
    private ArrayList<Neuron> outList;
    private ArrayList<ArrayList<Neuron>> hidLayers;

    public Network(Long inputs, Long outputs, Long[] hidden){
        System.out.println("Assembling network...");

        hidLayers = new ArrayList<>();
        inList = new ArrayList<>();
        outList = new ArrayList<>();

        System.out.println("Creating input layer...");
        for (Long i = 0L; i < inputs; i++){
            Neuron n = new Neuron(0f);
            inList.add(n);
        }
        System.out.println("Done!");

        System.out.println("Creating hidden layers...");
        System.out.println("Number of hidden layers: " + hidden.length);
        for (int x = 0; x<hidden.length; x++){
            Long l = hidden[x];
            ArrayList<Neuron> layer = new ArrayList<>();
            for (Long i = 0L; i<l; i++){
                Neuron n = new Neuron(0f);
                if (x==0){
                    n.connect(inList);
                } else {
                    n.connect(hidLayers.get(x-1));
                }
                layer.add(n);
            }
            hidLayers.add(layer);
        }
        System.out.println("Done!");

        System.out.println("Creating output layer...");
        for (Long i = 0L; i < outputs; i++){
            Neuron n = new Neuron(0f);
            n.connect(hidLayers.get(hidLayers.size()));
            outList.add(n);
        }
        System.out.println("Done!");

        System.out.println("Network online!");
    }

    public void train(){

    }

    public void start() {

    }
}
