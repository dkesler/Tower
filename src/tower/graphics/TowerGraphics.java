package tower.graphics;

import tower.buiildings.BuildingFactory;
import tower.controls.BuildingPlacementIntent;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TowerGraphics {

    final private JFrame jFrame;
    final private JPanel jPanel;
    final private PopupMenu genericPopup;
    final private BuildingPlacementIntent buildingPlacementIntent = new BuildingPlacementIntent();
    final private Drawer drawer;

    public TowerGraphics(final LocalMap localMap, final BuildingFactory buildingFactory) {
        jFrame = new JFrame("Tower");
        jFrame.setBackground(Color.BLACK);
        jFrame.setMinimumSize(new Dimension(800, 600));

       jPanel = new JPanel(new BorderLayout());

        genericPopup = new PopupMenu();
        for (final String building : buildingFactory.getBuildingNames()) {
            MenuItem menuItem = new MenuItem(building);
            genericPopup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buildingPlacementIntent.name = building;
                    buildingPlacementIntent.prototype = buildingFactory.getPrototype(building);
                    buildingPlacementIntent.isActive = true;
                    drawer.addActiveIntent(buildingPlacementIntent);
                }
            });
        }

        jFrame.add(jPanel);

        drawer = new Drawer(localMap);
        jPanel.add(drawer);
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && !buildingPlacementIntent.isActive) {
                    jPanel.add(genericPopup);
                    genericPopup.show(jPanel, e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON1 && buildingPlacementIntent.isActive && buildingPlacementIntent.isValidPlacement) {
                    buildingPlacementIntent.isActive = false;
                    drawer.removeActiveIntent(buildingPlacementIntent);
                    localMap.addBuilding(buildingFactory.createByName(buildingPlacementIntent.name, GridCoord.fromPixels(e.getX(), e.getY())));
                }
            }
        };

        jPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (buildingPlacementIntent.isActive && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    buildingPlacementIntent.isActive = false;
                    drawer.removeActiveIntent(buildingPlacementIntent);
                }
            }
        });


        MouseMotionAdapter motionListener = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                drawer.mouseX = e.getX();
                drawer.mouseY = e.getY();
                if (buildingPlacementIntent.isActive) {
                    buildingPlacementIntent.updateValidity(GridCoord.fromPixels(e.getX(), e.getY()), localMap.getBuildings());
                }
            }
        };

        jPanel.addMouseListener(
                listener
        );

        jPanel.addMouseMotionListener(motionListener);

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
        jPanel.setVisible(true);
    }

    public void repaint() {
        jFrame.repaint();
    }
}
