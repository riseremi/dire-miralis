package the.storeroom.layers;

import java.awt.Rectangle;

/**
 * Class with additional layer methods like fillRect, drawRect etc.
 *
 * @author Remi
 */
public final class LayerUtils {

    /**
     * Fills a rectangle with specified tile at specified place then returns an
     * updated version of the layer.
     */
    public static int[][] fillRect(int[][] layer, int x, int y, int width, int height, int tileId)
            throws ArrayIndexOutOfBoundsException {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                layer[i][j] = tileId;
            }
        }
        return layer;
    }

    /**
     * Fills a rectangle with specified tile at (0; 0) then returns an updated
     * version of the layer.
     */
    public static int[][] fillRect(int[][] layer, int width, int height, int tileId)
            throws ArrayIndexOutOfBoundsException {
        return fillRect(layer, 0, 0, width, height, tileId);
    }
}
