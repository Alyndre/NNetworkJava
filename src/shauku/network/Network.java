package shauku.network;

import java.util.ArrayList;

/**
 * Created by Dani on 12/05/2016.
 */
public class Network {

    ArrayList<Neuron> inList;
    ArrayList<Neuron> outList;
    ArrayList<ArrayList> hidLayers;

    public Network(Long inputs, Long outputs, Long[] hidden){
        System.out.println("Assembling network...");

        hidLayers = new ArrayList<>();
        inList = new ArrayList<>();
        outList = new ArrayList<>();

        System.out.println("Creating hidden layers...");
        for (Long l : hidden) {
            ArrayList<Neuron> layer = new ArrayList<>();
            for (Long i = 0L; i < l; i++){
                Neuron n = new Neuron(0f);
                layer.add(n);
            }
            hidLayers.add(layer);
        }
        System.out.println("Done!");

        System.out.println("Creating output layer...");
        for (Long i = 0L; i < outputs; i++){
            Neuron n = new Neuron(0f);
            outList.add(n);
        }
        System.out.println("Done!");

        System.out.println("Creating input layer...");
        for (Long i = 0L; i < inputs; i++){
            Neuron n = new Neuron(0f);
            inList.add(n);
        }
        System.out.println("Done!");

        System.out.println("Network online!");
    }

    public void train(){

    }
}
