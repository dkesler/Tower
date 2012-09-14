package tower.graphics;

import tower.controls.CameraControlIntent;
import tower.controls.ContextMenuIntent;
import tower.controls.ViewBuildingDetailsIntent;
import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.entity.items.ItemFactory;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class LocalMapPanel extends Panel {

    final private Camera camera;
    final private LocalMap localMap;

    private GridCoord mouseCoord;

    public LocalMapPanel(JPanel jPanel) {
        this.camera = new Camera();
        this.localMap = new LocalMap();

        CameraControlIntent cameraControlIntent = new CameraControlIntent(camera, jPanel);
        BuildingFactory buildingFactory = new BuildingFactory();
        ContextMenuIntent contextMenuIntent = new ContextMenuIntent(jPanel, localMap, camera, this, buildingFactory);
        ViewBuildingDetailsIntent viewBuildingDetailsIntent = new ViewBuildingDetailsIntent(localMap, camera, jPanel);
        viewBuildingDetailsIntent.registerIncompatibleIntent(contextMenuIntent.buildingPlacementIntent);

        registerIntent(viewBuildingDetailsIntent);

        initialize(localMap, buildingFactory, new ItemFactory());
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {
        graphics2D.setTransform(camera.getCameraTransform());
        localMap.draw(graphics2D);
        graphics2D.setTransform(new AffineTransform());
    }

    private void initialize(LocalMap localMap, BuildingFactory buildingFactory, ItemFactory itemFactory) {
        Building blacksmith = buildingFactory.createByName("Blacksmith", GridCoord.fromUnits(3, 3));
        blacksmith.addItem(itemFactory.createByName("Iron Ore"));
        localMap.addBuilding(blacksmith);
        localMap.addBuilding(buildingFactory.createByName("Leatherworker", GridCoord.fromUnits(8, 8)));
    }
}
