package the.storeroom.tiledlayer;

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
public final class TiledLayer extends Layer {

    @Setter
    private boolean debug;
    @Getter @Setter
    private int[][] map;//[x][y]
    private final BufferedImage[] tiles;
    private final int tileWidth, tileHeight;
    @Getter
    private final int horizontalTilesNumber, verticalTilesNumber;
    //test
    @Getter
    private final BufferedImage minimap;
    private final BufferedImage image;

    public TiledLayer(BufferedImage image, int tileWidth, int tileHeight, int width, int height, int paintWidth, int paintHeight) {
        super(width * tileWidth, height * tileHeight, paintWidth, paintHeight);
        this.image = image;
        if (image.getWidth() / tileWidth * tileWidth != image.getWidth()
                || image.getHeight() / tileHeight * tileHeight != image.getHeight()) {
            throw new IllegalArgumentException();
        }
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.horizontalTilesNumber = width;
        this.verticalTilesNumber = height;
        tiles = chopImage(image);
        map = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = 0;
            }
        }
        minimap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage[] chopImage(BufferedImage image) {
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

    public void setTile(int x, int y, int tileId) {
        try {
            map[x][y] = tileId;
        } catch (Exception e) {
            System.out.println("error at: " + x + ":" + y);
        }
    }

    public int getTile(int x, int y) {
        int tileId = 0;
        try {
            tileId = map[x][y];
        } catch (Exception ex) {
            System.out.println("Out of bounds (getTile): x:" + x + ", y:" + y);
        }
        return tileId;
    }

    public void fillRectTile(int x, int y, int w, int h, int tileId) {
        for (int i = y; i < y + h; i++) {
            for (int j = x; j < w + x; j++) {
                setTile(j, i, tileId);
            }
        }
    }

    //отрисовка слоя, при этом рисуются только помещающиеся на экран тайлы
    @Override
    public void paintLayer(Graphics g) {
        Graphics2D g2d = minimap.createGraphics();
        g2d.setColor(new Color(142, 68, 173));
        g2d.fillRect(0, 0, minimap.getWidth(), minimap.getHeight());

        g2d.setColor(new Color(50, 50, 50));
        for (int i = 0; i < horizontalTilesNumber; i++) {
            for (int j = 0; j < verticalTilesNumber; j++) {
                if (map[i][j] != Map.DEFAULT_TILE) {
                    g2d.drawLine(i, j, i, j);
                }
            }
        }

        //новая отрисовка
        for (int i = 0; i < getPaintWidth(); i++) {
            for (int j = 0; j < getPaintHeight(); j++) {
                try {
                    paintTile(g, i * tileWidth, j * tileHeight, map[i + getBlocksX()][j + getBlocksY()]);
                    if (debug) {
                        g.drawString("" + map[i + getBlocksX()][j + getBlocksY()], i * tileWidth, j * tileHeight);
                    }
                } catch (Exception ex) {
                }

                g2d.setColor(new Color(231, 76, 60, 100));
                g2d.drawLine(i + getBlocksX(), j + getBlocksY(), i + getBlocksX(), j + getBlocksY());
            }
        }

    }

    protected void paintTile(Graphics g, int x, int y, int id) {
        g.drawImage(tiles[id], x, y, null);
    }

    public void moveDown() {
        super.setBlocksY(super.getBlocksY() - 1);
    }

    public void moveUp() {
        super.setBlocksY(super.getBlocksY() + 1);
    }

    public void moveLeft() {
        super.setBlocksX(super.getBlocksX() + 1);
    }

    public void moveRight() {
        super.setBlocksX(super.getBlocksX() - 1);
    }

    public void resetFOVMap(int tileId, int tile) {
        for (int i = 0; i < verticalTilesNumber; i++) {
            for (int j = 0; j < horizontalTilesNumber; j++) {
                if (map[i][j] == tileId) {
                    map[i][j] = tile;
                }
            }
        }
    }

    public void resetMap(int tileId) {
        for (int i = 0; i < verticalTilesNumber; i++) {
            for (int j = 0; j < horizontalTilesNumber; j++) {
                map[i][j] = tileId;
            }
        }
    }

    @Override
    protected TiledLayer clone() throws CloneNotSupportedException {
        return new TiledLayer(image, tileWidth, tileHeight, horizontalTilesNumber,
                verticalTilesNumber, getPaintWidth(), getPaintHeight());
    }

}
