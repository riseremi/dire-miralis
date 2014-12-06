package the.storeroom.items.gems;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author Remi
 */
public class AbilityGem extends Gem {

    @Getter private Ability ability;

    public enum Ability {
        //TODO
    }

    public AbilityGem(GemType gemType, BufferedImage image, String name, ArrayList<Property> properies, Type type) {
        super(gemType, image, name, properies, type);
    }

}
