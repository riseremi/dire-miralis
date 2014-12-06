package the.storeroom.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import lombok.Getter;
import the.storeroom.Game;
import the.storeroom.TheStoreroom;
import the.storeroom.items.Item;

/**
 *
 * @author Remi
 */
public class Inventory {

    @Getter private ArrayList<Item> items;
    @Getter private final Dimension size = new Dimension(4, 4); //TODO

    public Inventory() {
        items = new ArrayList<>();
        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/testItem1.png"));
            items.add(new Item("Test Item #01", image, null, Item.Type.OTHER));
        } catch (IOException ex) {
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void useItem(Item item) {
        item.use(this);
    }

    public void paint(Graphics g) {
        g.setColor(new Color(100, 100, 100));
        g.fillRect(0, 0, TheStoreroom.WINDOW_W, TheStoreroom.WINDOW_H);

        g.setColor(new Color(255, 255, 255));
        g.drawString("Inventory is here", 64, 64);

        int startY = 96;
        for (Item item : items) {
            g.drawString(item.toString(), 64, startY);
            startY += 24;
        }
        Game.getInstance().repaint();
    }
}
