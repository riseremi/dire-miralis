package the.storeroom.layers;

import lombok.Getter;

/**
 *
 * @author Remi
 */
public class ObstaclesLayer extends Layer {

    /**
     * The passable tile constant. This value means that player CAN move through
     * this tile.
     */
    public static final int PASSABLE = 1;

    /**
     * The impassable tile constant. This value means that player CANNOT move
     * through this tile.
     */
    public static final int IMPASSABLE = 2;

    /**
     * Creates a new ObstaclesLayer.
     *
     * @param tile fill layer with the {@link #PASSABLE} or {@link #IMPASSABLE}
     * tile.
     */
    public ObstaclesLayer(int tile, int width, int height, int tileWidth, int tileHeight) {
        super(width, height, tileWidth, tileHeight);
        super.fillRect(width, height, tile);

    }

    /**
     * Checks if there is an obstacle on specified tile.
     */
    public boolean isPassable(int x, int y) throws ArrayIndexOutOfBoundsException {
        return super.getTile(x, y) == PASSABLE;
    }
}
