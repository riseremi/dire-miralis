package the.storeroom.entity;

/**
 *
 * @author Remi
 */
public class Player extends Entity {
    

    public Player(String pathToSprite) {
        super(pathToSprite);
        inventory = new Inventory();
    }
    
    

}
