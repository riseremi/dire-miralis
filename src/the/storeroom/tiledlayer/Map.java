package the.storeroom.tiledlayer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import lombok.Getter;
import the.storeroom.Game;
import the.storeroom.TheStoreroom;
import the.storeroom.entity.Player;
import the.storeroom.items.Item;

/**
 *
 * @author Remi
 */
public class Map {

    private TiledLayer tiles, obstacles, fov;
    public static final int DEFAULT_TILE = 28; //28
    @Getter private ArrayList<Item> items;

    public Map(int width, int height, Dimension TILE) {
        items = new ArrayList<>();
        try {
            final BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/res/tileset.png"));
            final int paintWidth = TheStoreroom.WINDOW_W / TILE.width;
            final int paintHeight = TheStoreroom.WINDOW_H / TILE.height;
            tiles = new TiledLayer(image, TILE.width, TILE.height, width, height, paintWidth, paintHeight);
            obstacles = tiles.clone();
            fov = tiles.clone();
            fov.resetMap(28);
            //obstacles = new TiledLayer(image, TILE.width, TILE.height, width, height, paintWidth, paintHeight);
        } catch (IOException | CloneNotSupportedException ex) {
        }
        tiles.fillRectTile(0, 0, width, height, DEFAULT_TILE);
        obstacles.fillRectTile(0, 0, width, height, 1);
    }

    public void setObstacle(int x, int y) {
        obstacles.setTile(x, y, 1);
    }

    public void fillObstacle(int x, int y, int w, int h) {
        obstacles.fillRectTile(x, y, w, h, 1);
    }

    public TiledLayer getTiles() {
        return tiles;
    }

    public void paint(Graphics g) {
        fov.resetFOVMap(9, 19);

        Player player = Game.getInstance().getPlayer();
        int x = player.getX();
        int y = player.getY();

        int layerX = tiles.getBlocksX();
        int layerY = tiles.getBlocksY();

        int realX2 = x / Game.TILE.width + layerX;
        int realY2 = y / Game.TILE.height + layerY;

        int fovSide = 14;
        for (int i = -fovSide; i < fovSide + 1; i++) {
            for (int j = -fovSide; j < fovSide + 1; j++) {
                Bresenham.line(realX2, realY2, realX2 + i, realY2  + j, 9, fov.getMap(), obstacles.getMap());
            }
        }

//        obstacles.paintLayer(g);
        tiles.paintLayer(g);
//        fov.paintLayer(g);
    }

    public void reset() {
        tiles.resetMap(DEFAULT_TILE);
        obstacles.resetMap(1);
    }

    public void moveLeft() {
        tiles.moveLeft();
        obstacles.moveLeft();
        fov.moveLeft();
    }

    public void moveRight() {
        tiles.moveRight();
        obstacles.moveRight();
        fov.moveRight();
    }

    public TiledLayer getObstacles() {
        return obstacles;
    }

    public void moveUp() {
        tiles.moveUp();
        obstacles.moveUp();
        fov.moveUp();
    }

    public void moveDown() {
        tiles.moveDown();
        obstacles.moveDown();
        fov.moveDown();
    }

    public void setDebug(boolean debug) {
        tiles.setDebug(debug);
    }

    public void setPosition(int x, int y) {
        obstacles.setBlocksX(x);
        obstacles.setBlocksY(y);

        tiles.setBlocksX(x);
        tiles.setBlocksY(y);
        
        fov.setBlocksX(x);
        fov.setBlocksY(y);
    }
}
