package the.storeroom.items.gems;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import the.storeroom.items.Item;

/**
 *
 * @author Remi
 */
public class Gem extends Item {

    @Getter @Setter private GemType gemType;

    public enum GemType {

        BASE, STAT, ABILITY
    }

    public Gem(GemType gemType, BufferedImage image, String name, ArrayList<Property> properies, Type type) {
        super(name, image, properies, type);
        this.gemType = gemType;
    }

}
