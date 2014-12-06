package the.storeroom;

import java.awt.Graphics;

/**
 *
 * @author Remi
 */
public class Camera {

    private int x, y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void translate(Graphics g, int tileWidth, int tileHeight) {
        g.translate(x * tileWidth, y * tileHeight);
    }

    public void untranslate(Graphics g, int tileWidth, int tileHeight) {
        g.translate(-x * tileWidth, y * -tileHeight);
    }

    public void addY(int y) {
        this.y += y;
    }

    public void addX(int x) {
        this.x += x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
