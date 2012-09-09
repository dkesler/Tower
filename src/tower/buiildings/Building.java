package tower.buiildings;

import tower.grid.GridCoord;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class Building {

    final private BuildingPrototype prototype;
    final private GridCoord gridCoord;

    public Building(BuildingPrototype prototype, GridCoord gridCoord) {
        this.gridCoord = gridCoord;
        this.prototype = prototype;
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(
                prototype.image,
                new AffineTransformOp(
                        AffineTransform.getScaleInstance(.25, .25),
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR
                ),
                gridCoord.xPixels,
                gridCoord.yPixels
        );
    }

}
