package the.storeroom.layers;

import java.awt.Dimension;
import java.awt.Graphics;
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

    @Getter private TiledLayer tiles;
//    private TiledLayer obstacles2;
    private FOVLayer fov;
    @Getter private ObstaclesLayer obstacles;
    public static final int DEFAULT_TILE = 28; //28
    @Getter private ArrayList<Item> items;
    private ArrayList<Layer> layers;

    public Map(int width, int height, Dimension TILE) {
        layers = new ArrayList<>();
        items = new ArrayList<>();

        try {
            final BufferedImage tilesImage = ImageIO.read(getClass().getResourceAsStream("/res/tileset.png"));
            final BufferedImage fovImage = ImageIO.read(getClass().getResourceAsStream("/res/fov.png"));
            final int paintWidth = TheStoreroom.WINDOW_W / TILE.width;
            final int paintHeight = TheStoreroom.WINDOW_H / TILE.height;

            tiles = new TiledLayer(tilesImage, TILE.width, TILE.height, width, height, paintWidth, paintHeight);
            tiles.fillRect(width, height, DEFAULT_TILE);

            fov = new FOVLayer(fovImage, FOVLayer.INVISIBLE, TILE.width, TILE.height, width, height, paintWidth, paintHeight);

            obstacles = new ObstaclesLayer(ObstaclesLayer.IMPASSABLE, width, height, TILE.width, TILE.height);
        } catch (IOException ex) {
            System.out.println("Cannot create map.");
            System.exit(0);
        }

        layers.add(tiles);
        layers.add(fov);
        layers.add(obstacles);
    }

    public void paint(Graphics g) {
        fov.reset(FOVLayer.VISIBLE, FOVLayer.SEEN);

        Player player = Game.getInstance().getPlayer();
        int x = player.getX();
        int y = player.getY();

        int layerX = tiles.getX();
        int layerY = tiles.getY();

        int realX2 = x / Game.TILE.width + layerX;
        int realY2 = y / Game.TILE.height + layerY;
        final int WINDOW_W = TheStoreroom.WINDOW_W;
        final int WINDOW_H = TheStoreroom.WINDOW_H;

        int fovSide = WINDOW_W > WINDOW_H ? WINDOW_W / Game.TILE.width : WINDOW_H / Game.TILE.height;
        for (int i = -fovSide; i < fovSide + 1; i++) {
            for (int j = -fovSide; j < fovSide + 1; j++) {
                Bresenham.line(realX2, realY2, realX2 + i, realY2 + j, 9, fov.getLayer(), obstacles);
            }
        }

//        obstacles.paintLayer(g);
        tiles.paintLayer(g);
        fov.paintLayer(g);
    }

    public void reset() {
        tiles.reset(DEFAULT_TILE);
        obstacles.reset(ObstaclesLayer.IMPASSABLE);
        fov.reset(FOVLayer.SEEN, FOVLayer.INVISIBLE);
    }

    public void move(Layer.Direction dir) {
        layers.stream().forEach((l) -> l.move(dir));
    }

//    public void moveLeft() {
//        tiles.moveLeft();
//        obstacles.moveLeft();
//        fov.moveLeft();
//    }
//
//    public void moveRight() {
//        tiles.moveRight();
//        obstacles.moveRight();
//        fov.moveRight();
//    }
//    public void moveUp() {
//        tiles.moveUp();
//        obstacles.moveUp();
//        fov.moveUp();
//    }
//
//    public void moveDown() {
//        tiles.moveDown();
//        obstacles.moveDown();
//        fov.moveDown();
//    }
    public void setDebug(boolean debug) {
        tiles.setDebug(debug);
    }

    public void setPosition(int x, int y) {
        obstacles.setX(x);
        obstacles.setY(y);

        tiles.setX(x);
        tiles.setY(y);

        fov.setX(x);
        fov.setY(y);
    }
}
