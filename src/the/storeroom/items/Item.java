package the.storeroom.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import the.storeroom.Game;
import the.storeroom.entity.Inventory;

/**
 *
 * @author Remi
 */
public class Item extends AbstractItem {

    @Getter @Setter private String name;
    @Getter private ArrayList<Property> properies;
    @Getter @Setter private Type type;

    public Item(String name, BufferedImage image, ArrayList<Property> properies, Type type) {
        this.image = image;
        this.name = name;
        this.properies = properies;
        this.type = type;
    }

    @Override
    public void paint(Graphics g) {
        final int xx = (x - Game.getInstance().getMap().getTiles().getBlocksX()) * Game.TILE.width;
        final int yy = (y - Game.getInstance().getMap().getTiles().getBlocksY()) * Game.TILE.height;
        g.drawImage(image, xx, yy, null);
    }

    public enum Property {

        EQUIPABLE, INSERTABLE, HAS_SOCKETS, DROPPABLE, THROWABLE, CONSUMABLE
    }

    public enum Type {

        WEAPON, ARMOR, WAND, POTION, SCROLL, GEM, OTHER
    }

    public void use(Inventory inventory) {
        //TODO
    }

    public void drop(Inventory inventory) {
        //TODO
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + type.name();
    }

}
