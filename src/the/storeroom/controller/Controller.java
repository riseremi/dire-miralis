package the.storeroom.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import the.storeroom.Game;
import the.storeroom.entity.Entity;
import the.storeroom.tiledlayer.TiledLayer;

/**
 *
 * @author Remi
 */
public class Controller implements KeyListener {

    private final Game instance = Game.getInstance();

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("mapx: " + instance.getMap().getTiles().getBlocksX() + ", mapy: " + instance.getMap().getTiles().getBlocksY());

        TiledLayer obstacles = instance.getMap().getObstacles();
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (instance.getPlayer().canMove(Entity.Direction.UP, obstacles)) {
                instance.getMap().moveDown();
                instance.repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (instance.getPlayer().canMove(Entity.Direction.DOWN, obstacles)) {
                instance.getMap().moveUp();
                instance.repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (instance.getPlayer().canMove(Entity.Direction.LEFT, obstacles)) {
                instance.getMap().moveRight();
                instance.repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (instance.getPlayer().canMove(Entity.Direction.RIGHT, obstacles)) {
                instance.getMap().moveLeft();
                instance.repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            instance.generateRooms();
            instance.repaint();
        }

        //open inventory
        if (e.getKeyCode() == KeyEvent.VK_I) {
            System.out.println("inv open");
            instance.setGameState(instance.getGameState() == Game.GameState.INVENTORY
                    ? Game.GameState.INGAME
                    : Game.GameState.INVENTORY);
            instance.repaint();
        }

        //add gem
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("add gem");
            instance.generateGem();
            instance.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
