package shauku.network;

import shauku.data.Data;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Network {

    public enum Type {
        MLP,
        SOM
    }

    private Type type;

    private long numNeurons = 0;

    public Network(Type type){
        this.setType(type);
    }

    public abstract void train(Data dataset, int iterations);

    public abstract void feed(double[] data);

    public abstract void start();

    public void drawNetwork(){

    }

    public void saveNet() {
        System.out.println("Saving...");
        //TODO: Save all the network params in a BBDD or something
    }

    public void loadNet(long netId) {
        System.out.println("Loading network " + netId);
        //TODO: Load a previously saved network
    }

    public void clean() {
        //TODO: Clean all data of the network
    }

    public long getNumNeurons() {
        return numNeurons;
    }

    public void setNumNeurons(long numNeurons) {
        this.numNeurons = numNeurons;
    }

    public void addNeurons(long newNeurons){
        numNeurons += newNeurons;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
