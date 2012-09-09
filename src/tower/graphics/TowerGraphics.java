package tower.graphics;

import tower.buiildings.Building;
import tower.buiildings.BuildingFactory;
import tower.controls.BuildingPlacementIntent;
import tower.controls.DestroyBuildingIntent;
import tower.grid.GridCoord;
import tower.map.LocalMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TowerGraphics {

    final private JFrame jFrame;
    final private JPanel jPanel;
    final private JMenu createBuildingMenu;
    final private JMenu destroyBuildingMenu;
    final private BuildingPlacementIntent buildingPlacementIntent = new BuildingPlacementIntent();
    final private DestroyBuildingIntent destroyBuildingIntent = new DestroyBuildingIntent();
    final private Drawer drawer;
    final private Camera camera;

    public TowerGraphics(final LocalMap localMap, final BuildingFactory buildingFactory) {
        jFrame = new JFrame("Tower");
        jFrame.setBackground(Color.BLACK);
        jFrame.setMinimumSize(new Dimension(800, 600));

       jPanel = new JPanel(new BorderLayout());

        createBuildingMenu = new JMenu();
        for (final String building : buildingFactory.getBuildingNames()) {
            JMenuItem menuItem = new JMenuItem(building);
            createBuildingMenu.add(menuItem);
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

        destroyBuildingMenu = new JMenu();

        jFrame.add(jPanel);
        jPanel.add(createBuildingMenu);
        jPanel.add(destroyBuildingMenu);

        final ActionListener destroyBuildingActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localMap.removeBuilding(destroyBuildingIntent.building);
                destroyBuildingIntent.isActive = false;
                drawer.removeActiveIntent(destroyBuildingIntent);
            }
        };

        camera = new Camera();

        drawer = new Drawer(localMap, camera);

        jPanel.add(drawer);
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    final Building selected = localMap.getBuildingAt(camera.convertEventToGrid(e));
                    if (selected != null) {
                        destroyBuildingIntent.isActive = true;
                        destroyBuildingIntent.building = selected;
                        destroyBuildingMenu.removeAll();
                        JMenuItem jMenuItem = new JMenuItem("Destroy " + selected.getName());
                        jMenuItem.addActionListener(destroyBuildingActionListener);
                        destroyBuildingMenu.add(jMenuItem);

                        drawer.addActiveIntent(destroyBuildingIntent);

                        JPopupMenu destroyPopup = destroyBuildingMenu.getPopupMenu();
                        destroyPopup.addPopupMenuListener(new PopupMenuListener() {
                            @Override
                            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                            }

                            @Override
                            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                            }

                            @Override
                            public void popupMenuCanceled(PopupMenuEvent e) {
                                drawer.removeActiveIntent(destroyBuildingIntent);
                                destroyBuildingIntent.isActive = false;
                            }
                        });

                        destroyPopup.show(jPanel, e.getX(), e.getY());


                    } else if (!buildingPlacementIntent.isActive) {
                        createBuildingMenu.getPopupMenu().show(jPanel, e.getX(), e.getY());
                    }
                } else if (e.getButton() == MouseEvent.BUTTON1 && buildingPlacementIntent.isActive && buildingPlacementIntent.isValidPlacement) {
                    buildingPlacementIntent.isActive = false;
                    drawer.removeActiveIntent(buildingPlacementIntent);
                    localMap.addBuilding(buildingFactory.createByName(buildingPlacementIntent.name, camera.convertEventToGrid(e)));
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
                GridCoord mouseCoord = camera.convertEventToGrid(e);
                drawer.mouseCoord = mouseCoord;
                if (buildingPlacementIntent.isActive) {
                    buildingPlacementIntent.updateValidity(mouseCoord, localMap.getBuildings());
                }
            }
        };

        jPanel.addMouseListener(listener);
        jPanel.addMouseMotionListener(motionListener);
        jPanel.addMouseWheelListener(
                new MouseAdapter() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent e) {
                        camera.zoom(-e.getWheelRotation());
                    }
                }
        );

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
