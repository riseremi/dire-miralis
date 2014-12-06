package the.storeroom.tiledlayer;

import java.awt.Graphics;
import the.storeroom.Game;
import the.storeroom.TheStoreroom;

/**
 *
 * @author Remi
 */
public abstract class Layer {

    private int blocksX, blocksY, x, y;
    private final int width, height;
    private final int paintWidth, paintHeight;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Layer(int width, int height, int paintWidth, int paintHeight) {
        this.width = width;
        this.height = height;
        this.paintWidth = paintWidth;
        this.paintHeight = paintHeight;
        
        System.out.println("paint width x height: " + paintWidth + "x" + paintHeight);
    }

    public final void paintComponent(Graphics g) {
        paintLayer(g);
    }

    abstract void paintLayer(Graphics g);

    public int getW() {
        return width;
    }

    public int getH() { //10/10
        return height;
    }

    public int getPaintWidth() {
        return paintWidth;
    }

    public int getPaintHeight() {
        return paintHeight;
    }

    public int getBlocksX() {
        return blocksX;
    }

    public int getBlocksY() {
        return blocksY;
    }

    public void setBlocksX(int x) {
        this.blocksX = x;
    }

    public void setBlocksY(int y) {
        this.blocksY = y;
    }
}
