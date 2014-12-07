package the.storeroom.layers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LPzhelud use of this class approved by the author 09.11.2012 - 9:15
 * AM
 */
public class TiledLayer extends DrawableLayer {

    @Setter private boolean debug;
    private final int tileWidth, tileHeight;
    @Getter private final int paintWidth, paintHeight;
    //test
    @Getter private final BufferedImage minimap;
    private final BufferedImage image;

    public TiledLayer(BufferedImage image, int tileWidth, int tileHeight, int width, int height, int paintWidth, int paintHeight) {
        super(image, width, height, tileWidth, tileHeight);
        this.image = image;

        this.paintWidth = paintWidth;
        this.paintHeight = paintHeight;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;

        minimap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    //отрисовка слоя, при этом рисуются только помещающиеся на экран тайлы
    @Override
    public void paintLayer(Graphics g) {
        int[][] map = super.getLayer();

        Graphics2D g2d = minimap.createGraphics();
        g2d.setColor(new Color(142, 68, 173));
        g2d.fillRect(0, 0, minimap.getWidth(), minimap.getHeight());

        g2d.setColor(new Color(50, 50, 50));
        for (int i = 0; i < super.getWidth(); i++) {
            for (int j = 0; j < super.getHeight(); j++) {
                if (map[i][j] != Map.DEFAULT_TILE) {
                    g2d.drawLine(i, j, i, j);
                }
            }
        }

        //новая отрисовка
        for (int i = 0; i < getPaintWidth(); i++) {
            for (int j = 0; j < getPaintHeight(); j++) {
                try {
                    paintTile(g, i * tileWidth, j * tileHeight, map[i + getX()][j + getY()]);
                    if (debug) {
                        g.drawString("" + map[i + getX()][j + getY()], i * tileWidth, j * tileHeight);
                    }

                    g2d.setColor(new Color(231, 76, 60, 100));
                    if (map[i + getX()][j + getY()] != 12) {
                        g2d.drawLine(i + getX(), j + getY(), i + getX(), j + getY());
                    }
                } catch (Exception ex) {
                }
            }
        }

    }

    @Override
    protected TiledLayer clone() throws CloneNotSupportedException {
        super.clone();
        return new TiledLayer(image, tileWidth, tileHeight, super.getWidth(),
                super.getHeight(), getPaintWidth(), getPaintHeight());
    }

}
