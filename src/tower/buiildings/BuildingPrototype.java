package tower.buiildings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BuildingPrototype {
    public final int width;
    public final int height;
    public final BufferedImage image;
    public final String name;

    public BuildingPrototype(String name, File imageFile, int height, int width) {
        this.name = name;
        try {
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred loading image for building [" + name + "]", e);
        }
        this.height = height;
        this.width = width;
    }
}
