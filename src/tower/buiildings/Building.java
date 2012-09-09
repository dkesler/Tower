package tower.buiildings;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class Building {

    public static final int UNIT_SIZE = 64;

    final BuildingPrototype prototype;
    final int x;
    final int y;

    public Building(BuildingPrototype prototype, int x, int y) {
        this.x = x;
        this.y = y;
        this.prototype = prototype;
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(
                prototype.image,
                new AffineTransformOp(
                        AffineTransform.getScaleInstance(.25, .25),
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR
                ),
                x*UNIT_SIZE,
                y*UNIT_SIZE
        );
    }

}
