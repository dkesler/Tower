package tower.graphics;

import tower.buiildings.Building;
import tower.buiildings.BuildingFactory;
import tower.grid.GridCoord;
import tower.items.ItemFactory;
import tower.map.LocalMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TowerGraphics {

    final private JFrame jFrame;

    public TowerGraphics() {
        jFrame = new JFrame("Tower");
        jFrame.setBackground(Color.BLACK);
        jFrame.setMinimumSize(new Dimension(800, 600));
        JPanel jPanel = new JPanel(new BorderLayout());
        jFrame.add(jPanel);

        LocalMap localMap = new LocalMap();
        BuildingFactory buildingFactory = new BuildingFactory();
        ItemFactory itemFactory = new ItemFactory();

        initialize(localMap, buildingFactory, itemFactory);

        LocalMapPanel localMapPanel = new LocalMapPanel(localMap, buildingFactory);

        jPanel.add(localMapPanel.getjPanel());

        jFrame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        jPanel.setFocusable(true);
        jPanel.requestFocusInWindow();

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
}
