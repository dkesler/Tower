package tower.entity.constructions;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WallPrototype {
    public WallPrototype() {
        try {
            URL resource = ClassLoader.class.getResource("/images/constructions/wall.png");
            this.image = ImageIO.read(FileUtils.toFile(resource));
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred loading image for wall", e);
        }
    }

    public final BufferedImage image;
}
