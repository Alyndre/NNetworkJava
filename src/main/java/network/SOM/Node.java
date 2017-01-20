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

    public double distanceTo(Node n2) {
        int deltaX;
        int deltaY;
        deltaX = getX() - n2.getX();
        deltaX *= deltaX;
        deltaY = getY() - n2.getY();
        deltaY *= deltaY;
        return deltaX + deltaY;
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
