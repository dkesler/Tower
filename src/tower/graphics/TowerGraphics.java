package tower.graphics;

import tower.buiildings.BuildingFactory;
import tower.controls.CameraControlIntent;
import tower.controls.ContextMenuIntent;
import tower.controls.CursorTrackingIntent;
import tower.controls.ViewBuildingDetailsIntent;
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

    public TowerGraphics(final LocalMap localMap, final BuildingFactory buildingFactory) {
        jFrame = new JFrame("Tower");
        jFrame.setBackground(Color.BLACK);
        jFrame.setMinimumSize(new Dimension(800, 600));
        JPanel jPanel = new JPanel(new BorderLayout());
        jFrame.add(jPanel);

        Camera camera = new Camera();
        Drawer drawer = new Drawer(localMap, camera, jPanel);

        new ContextMenuIntent(jPanel, localMap, camera, drawer, buildingFactory);
        new CursorTrackingIntent(drawer, jPanel);
        new CameraControlIntent(camera, jPanel);
        new ViewBuildingDetailsIntent(localMap, camera, jPanel, drawer);

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

    public void repaint() {
        jFrame.repaint();
    }
}
