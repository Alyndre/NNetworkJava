package network.SOM;

public class Node {

    public double[] weights;
    private int x;
    private int y;

    Node (int numWeights){
        weights = new double[numWeights];
        for (int x=0; x<numWeights; x++) {
            weights[x] = Math.random();
        }
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
