package the.storeroom.layers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Remi
 */
public abstract class DrawableLayer extends Layer {

    private final BufferedImage[] tiles;

    public DrawableLayer(BufferedImage image, int width, int height, int tileWidth, int tileHeight) {
        super(width, height, tileWidth, tileHeight);
        if (image.getWidth() / tileWidth * tileWidth != image.getWidth()
                || image.getHeight() / tileHeight * tileHeight != image.getHeight()) {
            throw new IllegalArgumentException();
        }
        tiles = chopImage(image, tileWidth, tileHeight);
    }

    public final BufferedImage[] chopImage(BufferedImage image, int tileWidth, int tileHeight) {
        int x = 0, y = 0;
        ArrayList<BufferedImage> list = new ArrayList<>();
        try {
            while (true) {
                while (true) {
                    BufferedImage subImage = image.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                    list.add(subImage);
                    x++;
                    if ((x + 1) * tileWidth > image.getWidth()) {
                        x = 0;
                        break;
                    }
                }
                y++;
                if ((y + 1) * tileHeight > image.getHeight()) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(x);
            System.out.println(y);
        }
        return (BufferedImage[]) list.toArray(new BufferedImage[list.size()]);
    }

    public abstract void paintLayer(Graphics g);

    protected void paintTile(Graphics g, int x, int y, int id) {
        g.drawImage(tiles[id], x, y, null);
    }

}
