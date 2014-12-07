package the.storeroom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import the.storeroom.entity.Player;
import the.storeroom.items.Item;
import the.storeroom.mapgen2014.MapGen2014;
import the.storeroom.layers.Map;
import the.storeroom.layers.RoomUtils;
import the.storeroom.layers.TiledLayer;

/**
 *
 * @author Remi
 */
public final class Game extends JPanel {

    public static final Dimension TILE = new Dimension(16, 16);
    public static final Dimension MAP = new Dimension(32, 32);
    @Getter private TiledLayer layer;
    private static final Random rnd = new Random();
    private static Game instance;
    @Getter private final Player player;
    private final Map map;
    @Setter @Getter private GameState gameState = GameState.INGAME;
    private int MAX_ROOMS = 24 * MAP.width / 32;

    private Game() {
        map = new Map(MAP.width, MAP.height, TILE);
        map.setDebug(false);

        player = new Player("/res/player.png");
        player.setX(TheStoreroom.WINDOW_W / 2);
        player.setY(TheStoreroom.WINDOW_H / 2);
        
        player.setHp(100);
        player.setHp(30);

        generateRooms();

        System.out.println("player X and Y: " + player.getBlocksX() + "x" + player.getBlocksY());
        
        setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    public enum GameState {

        PAUSE, MAIN_MENU_OPEN, INGAME, INVENTORY
    }

    public void generateGem() {
        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/testItem" + (rnd.nextInt(3)) + ".png"));
            final Item item = new Item("Test Item On The Ground", image, null, Item.Type.OTHER);

            while (true) {
                final int x = rnd.nextInt(26 + 1);
                final int y = rnd.nextInt(26 + 1);

                if (map.getObstacles().isPassable(x, y)) {
                    item.setX(x);
                    item.setY(y);
                    break;
                }
            }

            map.getItems().add(item);
        } catch (IOException ex) {
        }
    }

    public void generateRooms() {
        map.reset();

        try {
//            MapGen2014 mapgen = new MapGen2014(24, MAP.width, MAP.height);
            MapGen2014 mapgen = new MapGen2014(MAX_ROOMS, MAP.width, MAP.height);
            for (int i = 0; i < mapgen.getRooms().size(); i++) {
                final Rectangle room = mapgen.getRooms().get(i);
                RoomUtils.buildRoom(map, room.x, room.y, room.width, room.height,
                        RoomUtils.STANDARD_ROOM);
            }
            RoomUtils.fillWalls(map);
        } catch (StackOverflowError e) {
            generateRooms();
        }

        RoomUtils.placePlayer(player, map);
    }

    public Map getMap() {
        return map;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    @Override
    public void paintComponent(Graphics g) {
//        g.setColor(new Color(255, 255, 255));
        g.setColor(new Color(19, 19, 19));
        g.fillRect(0, 0, TheStoreroom.WINDOW_W, TheStoreroom.WINDOW_H);

        //g.setColor(new Color(50, 50, 50));
        map.paint(g);

        map.getItems().stream().forEach((i) -> {
            i.paint(g);
        });

        player.paint(g);
        g.drawImage(map.getTiles().getMinimap(), 0, 0, null);

        if (gameState == GameState.INVENTORY) {
            player.getInventory().paint(g);
        }

        if (gameState == GameState.INGAME) {
            g.setColor(new Color(250, 250, 250));

            g.setColor(new Color(231, 76, 60));
            g.drawString("HP: " + player.getHp(), 4, 64 - 16);
            
            g.setColor(new Color(52, 152, 219));
            g.drawString("MP: " + player.getMp(), 4, 64);
//            g.drawString("ВЫНОСЛЕВАСТЬ: 100", 16, 64 + 24 * 2);
        }

    }
}
