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

        RootPanel rootPanel = new RootPanel();
        jFrame.getContentPane().add(rootPanel.getjPanel());

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

    public void repaint() {
        jFrame.repaint();
    }
}
