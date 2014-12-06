package the.storeroom.items.gems;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author Remi
 */
public class StatGem extends Gem {

    @Getter private StatAffected statAffected;

    public enum StatAffected {

        //beta
        STRENGTH, AGILITY, HEALTH, STAMINA, INTELLIGENCE
    }

    public StatGem(GemType gemType, BufferedImage image, String name, ArrayList<Property> properies, Type type) {
        super(gemType, image, name, properies, type);
    }

   

}
