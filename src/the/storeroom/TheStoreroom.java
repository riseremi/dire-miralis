package the.storeroom;

import javax.swing.JFrame;
import the.storeroom.controller.Controller;

/**
 *
 * @author Remi
 */
public class TheStoreroom extends JFrame {
    public static final int WINDOW_W = 480 - 32, WINDOW_H = 480;

    public TheStoreroom() {
        this.setBounds(0, 0, WINDOW_W, WINDOW_H);
        this.setTitle("The Storeroom");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TheStoreroom main = new TheStoreroom();
        
        Game game = Game.getInstance();
        main.add(game);
        
        main.addKeyListener(new Controller());
        
        main.setVisible(true);
    }
}
