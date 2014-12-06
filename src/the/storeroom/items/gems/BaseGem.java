package the.storeroom.items.gems;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author Remi
 */
public class BaseGem extends Gem {
    
    @Getter private BaseGemType baseGemType;
    
    public enum BaseGemType {
        ARMOR, WEAPON, POTION, WAND, SCROLL
    }

    public BaseGem(GemType gemType, BufferedImage image, String name, ArrayList<Property> properies, Type type) {
        super(gemType, image, name, properies, type);
    }

  
    

}
