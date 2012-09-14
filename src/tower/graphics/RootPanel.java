package tower.graphics;

import tower.controls.CameraControlIntent;
import tower.controls.ContextMenuIntent;
import tower.controls.CursorTrackingIntent;
import tower.controls.ViewBuildingDetailsIntent;
import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.entity.items.ItemFactory;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class RootPanel extends Panel {

    private final JPanel jPanel;
    private final LocalMapPanel localMapPanel;
    private final BuildingDetailsPanel buildingDetailsPanel;

    private Point2D mouseCoord;

    public RootPanel() {
        this.visible = true;
        final RootPanel thisPanel = this;
        jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                thisPanel.draw((Graphics2D) g);
            }
        };
        jPanel.setFocusable(true);
        jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        localMapPanel = new LocalMapPanel();
        buildingDetailsPanel = new BuildingDetailsPanel();
        buildingDetailsPanel.setWidth(200);
        subPanels.add(localMapPanel);
        subPanels.add(buildingDetailsPanel);

        CameraControlIntent cameraControlIntent = new CameraControlIntent(localMapPanel.getCamera(), jPanel);
        BuildingFactory buildingFactory = new BuildingFactory();
        ContextMenuIntent contextMenuIntent = new ContextMenuIntent(jPanel, localMapPanel.getLocalMap(), localMapPanel.getCamera(), localMapPanel, new BuildingFactory());
        ViewBuildingDetailsIntent viewBuildingDetailsIntent = new ViewBuildingDetailsIntent(localMapPanel.getLocalMap(), localMapPanel.getCamera(), jPanel, buildingDetailsPanel);
        viewBuildingDetailsIntent.registerIncompatibleIntent(contextMenuIntent.buildingPlacementIntent);
        buildingDetailsPanel.registerIntent(viewBuildingDetailsIntent);

        initialize(localMapPanel.getLocalMap(), buildingFactory, new ItemFactory());

         new CursorTrackingIntent(this, jPanel);
    }

    private void initialize(LocalMap localMap, BuildingFactory buildingFactory, ItemFactory itemFactory) {
        Building blacksmith = buildingFactory.createByName("Blacksmith", GridCoord.fromUnits(3, 3));
        blacksmith.addItem(itemFactory.createByName("Iron Ore"));
        localMap.addBuilding(blacksmith);
        localMap.addBuilding(buildingFactory.createByName("Leatherworker", GridCoord.fromUnits(8, 8)));
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    private void draw(Graphics2D graphics2D) {
        this.width = jPanel.getWidth();
        this.height = jPanel.getHeight();

        localMapPanel.setHeight(jPanel.getHeight());
        localMapPanel.setWidth(buildingDetailsPanel.isVisible() ? jPanel.getWidth() - 200 : jPanel.getWidth());

        buildingDetailsPanel.setHeight(jPanel.getHeight());
        buildingDetailsPanel.setX(jPanel.getWidth() - 200);

        this.draw(graphics2D, mouseCoord);
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {
    }

    public void setMouseCoord(Point2D mouseCoord) {
        this.mouseCoord = mouseCoord;
    }
}
