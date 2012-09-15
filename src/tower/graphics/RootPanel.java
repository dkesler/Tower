package tower.graphics;

import tower.controls.CameraControlIntent;
import tower.controls.ContextMenuIntent;
import tower.controls.CursorTrackingIntent;
import tower.controls.SelectBuildingIntent;
import tower.entity.buiildings.Building;
import tower.map.LocalMap;
import tower.saves.SaveParser;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
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
        buildingDetailsPanel = new BuildingDetailsPanel(jPanel);
        buildingDetailsPanel.setWidth(200);
        subPanels.add(localMapPanel);
        subPanels.add(buildingDetailsPanel);

        CameraControlIntent cameraControlIntent = new CameraControlIntent(localMapPanel.getCamera(), jPanel, localMapPanel);
        ContextMenuIntent contextMenuIntent = new ContextMenuIntent(jPanel, localMapPanel.getLocalMap(), localMapPanel.getCamera(), localMapPanel);
        SelectBuildingIntent selectBuildingIntent = new SelectBuildingIntent(localMapPanel.getLocalMap(), localMapPanel.getCamera(), jPanel, buildingDetailsPanel);
        selectBuildingIntent.registerIncompatibleIntent(contextMenuIntent.buildingPlacementIntent);

        initialize(localMapPanel.getLocalMap());

         new CursorTrackingIntent(this, jPanel);
    }

    private void initialize(LocalMap localMap) {
        SaveParser saveParser = new SaveParser("/saves/1.sav");
        for (Building building : saveParser.getBuildings()) {
            localMap.addBuilding(building);
        }
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
        buildingDetailsPanel.reflow(graphics2D);

        this.draw(graphics2D, mouseCoord);
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {
    }

    public void setMouseCoord(Point2D mouseCoord) {
        this.mouseCoord = mouseCoord;
    }
}
