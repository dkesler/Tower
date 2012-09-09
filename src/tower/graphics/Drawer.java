package tower.graphics;

import tower.map.LocalMap;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Drawer extends JPanel {

    final LocalMap localMap;

    public Drawer(LocalMap localMap) {
        this.localMap = localMap;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        localMap.draw(g2);
    }
}
