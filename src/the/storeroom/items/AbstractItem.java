package the.storeroom.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Remi
 */
public abstract class AbstractItem {

    @Getter @Setter protected int x, y;
    @Getter @Setter(AccessLevel.PACKAGE) protected BufferedImage image;

    public abstract void paint(Graphics g);
}
