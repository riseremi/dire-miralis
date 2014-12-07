package the.storeroom.layers;

import java.awt.Graphics;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Remi
 */
public abstract class Layer {

    @Getter @Setter private int x, y;
    @Getter private final int width, height;
    @Getter @Setter private int[][] layer;
    @Getter @Setter private int tileWidth, tileHeight;

    public enum Direction {

        UP, DOWN, LEFT, RIGHT
    }

    public Layer(int width, int height, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        layer = new int[width][height];

    }

    public void setTile(int x, int y, int tileId) throws ArrayIndexOutOfBoundsException {
        layer[x][y] = tileId;
    }

    public int getTile(int x, int y) throws ArrayIndexOutOfBoundsException {
        return layer[x][y];
    }

    public void reset(int tileId) {
        fillRect(width, height, tileId);
    }

    /**
     * Moves layer in a specified direction.
     */
    public void move(Direction dir) {
        switch (dir) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
    }

    /**
     * Fills a rectangle with specified tile at specified place then returns an
     * updated version of the layer.
     */
    public void fillRect(int x, int y, int width, int height, int tileId)
            throws ArrayIndexOutOfBoundsException {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                layer[i][j] = tileId;
            }
        }
    }

    /**
     * Fills a rectangle with specified tile at (0; 0) then returns an updated
     * version of the layer.
     */
    public void fillRect(int width, int height, int tileId)
            throws ArrayIndexOutOfBoundsException {
        fillRect(0, 0, width, height, tileId);
    }
}
