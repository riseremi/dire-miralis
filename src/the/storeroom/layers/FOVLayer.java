package the.storeroom.layers;

import java.awt.image.BufferedImage;

/**
 * This layer represents Field Of View.
 *
 * @author Remi
 */
public class FOVLayer extends TiledLayer {

    public static final int VISIBLE = 1;
    public static final int INVISIBLE = 0;
    public static final int SEEN = 2;

    /**
     * Creates a new FOVLayer.
     *
     * @param tile fill layer with the {@link #VISIBLE} or {@link #INVISIBLE} or
     * {@link #SEEN} tile.
     */
    public FOVLayer(BufferedImage image, int tile, int tileWidth, int tileHeight, int width, int height, int paintWidth, int paintHeight) {
        super(image, tileWidth, tileHeight, width, height, paintWidth, paintHeight);
        super.fillRect(width, height, tile);
    }

    /**
     * Checks if this tile is visible.
     */
    public boolean isVisible(int x, int y) throws ArrayIndexOutOfBoundsException {
        return super.getTile(x, y) == VISIBLE;
    }

    public void reset(int tileId, int tile) {
        for (int i = 0; i < super.getHeight(); i++) {
            for (int j = 0; j < super.getWidth(); j++) {
                if (super.getTile(i, j) == tileId) {
                    super.setTile(i, j, tile);
                }
            }
        }
    }

}
