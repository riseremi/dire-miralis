package the.storeroom.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.Setter;
import the.storeroom.Game;
import the.storeroom.layers.ObstaclesLayer;

/**
 *
 * @author Remi
 */
public abstract class Entity {

    @Getter @Setter private int x, y;
    private BufferedImage sprite;
    @Getter protected Inventory inventory;
    //rpg system
    @Getter @Setter private int hp, mp;
    @Getter @Setter private int speed; //TODO
    @Getter @Setter private int attack, defense;
    @Getter @Setter private int precision;

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

    public boolean canMove(Direction dir, ObstaclesLayer layer) {
        int layerX = -layer.getX();
        int layerY = -layer.getY();
        int realX = x / Game.TILE.width - layerX;
        int realY = y / Game.TILE.height - layerY;

        switch (dir) {
            case DOWN:
                return layer.isPassable(realX, realY + 1);
            case UP:
                return layer.isPassable(realX, realY - 1);
            case LEFT:
                return layer.isPassable(realX - 1, realY);
            case RIGHT:
                return layer.isPassable(realX + 1, realY);
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
