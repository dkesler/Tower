package tower.graphics;

import com.google.common.collect.Lists;
import tower.controls.CameraControlIntent;
import tower.controls.ContextMenuIntent;
import tower.controls.CursorTrackingIntent;
import tower.controls.ViewBuildingDetailsIntent;
import tower.entity.buiildings.Building;
import tower.entity.buiildings.BuildingFactory;
import tower.grid.GridCoord;
import tower.entity.items.ItemFactory;
import tower.map.LocalMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TowerGraphics {

    final private JFrame jFrame;

    public TowerGraphics() {
        jFrame = new JFrame("Tower");
        jFrame.setMinimumSize(new Dimension(800, 600));
        jFrame.setBackground(Color.BLACK);

        LocalMap localMap = new LocalMap();
        BuildingFactory buildingFactory = new BuildingFactory();
        ItemFactory itemFactory = new ItemFactory();

        initialize(localMap, buildingFactory, itemFactory);

        Camera camera = new Camera();
        LocalMapPanel localMapPanel = new LocalMapPanel(localMap, camera);

        ContextMenuIntent contextMenuIntent = new ContextMenuIntent(localMapPanel.getjPanel(), localMap, camera, localMapPanel, buildingFactory);
        new CursorTrackingIntent(localMapPanel, localMapPanel.getjPanel());
        new CameraControlIntent(camera, localMapPanel.getjPanel());

        ViewBuildingDetailsIntent viewBuildingDetailsIntent = new ViewBuildingDetailsIntent(
                localMap,
                camera,
                localMapPanel.getjPanel()
        );
        viewBuildingDetailsIntent.registerIncompatibleIntent(contextMenuIntent.buildingPlacementIntent);

        localMapPanel.registerIntent(viewBuildingDetailsIntent);

        jFrame.getContentPane().add(localMapPanel.getjPanel());

        jFrame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        jFrame.setVisible(true);
    }

    private void initialize(LocalMap localMap, BuildingFactory buildingFactory, ItemFactory itemFactory) {
        Building blacksmith = buildingFactory.createByName("Blacksmith", GridCoord.fromUnits(3, 3));
        blacksmith.addItem(itemFactory.createByName("Iron Ore"));
        localMap.addBuilding(blacksmith);
        localMap.addBuilding(buildingFactory.createByName("Leatherworker", GridCoord.fromUnits(8, 8)));
    }

    public void repaint() {
        jFrame.repaint();
    }

    public void pack() {
        jFrame.pack();
    }
}
