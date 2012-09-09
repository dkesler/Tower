package tower.graphics;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Drawer extends JPanel {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawString("string", 100, 100);
    }
}
