package the.storeroom.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.Setter;
import the.storeroom.Game;
import the.storeroom.tiledlayer.TiledLayer;

/**
 *
 * @author Remi
 */
public class Entity {

    @Getter @Setter private int x, y;
    private BufferedImage sprite;
    @Getter protected Inventory inventory;
    //rpg system
    @Getter @Setter private int hp, mp;
    @Getter @Setter private int speed; //TODO
    @Getter @Setter private int attack, defense;

    public Entity(String pathToSprite) {
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(pathToSprite));
        } catch (IOException ex) {
        }
    }

    public enum Direction {

        UP, DOWN, LEFT, RIGHT
    }

    public void paint(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }

    public boolean canMove(Direction dir, TiledLayer layer) {
        int layerX = -layer.getBlocksX();
        int layerY = -layer.getBlocksY();
        int realX = x / Game.TILE.width - layerX;
        int realY = y / Game.TILE.height - layerY;

        switch (dir) {
            case DOWN:
                return layer.getTile(realX, realY + 1) == 0;
            case UP:
                return layer.getTile(realX, realY - 1) == 0;
            case LEFT:
                return layer.getTile(realX - 1, realY) == 0;
            case RIGHT:
                return layer.getTile(realX + 1, realY) == 0;
        }

        return true;
    }

    public int getBlocksX() {
        return x / Game.TILE.width;
    }

    public int getBlocksY() {
        return y / Game.TILE.height;
    }
}
