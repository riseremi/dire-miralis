package the.storeroom.mapgen2014;

import java.awt.Rectangle;
import lombok.Getter;

/**
 *
 * @author Remi
 */
public class RectangleRoom extends Rectangle {

    @Getter private final int direction;

    public RectangleRoom(int x, int y, int width, int height, int direction) {
        super(x, y, width, height);
        this.direction = direction;
    }

    public RectangleRoom(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.direction = -1;
    }

}
