package tower.graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TowerGraphics {

    public TowerGraphics() {
        JFrame jFrame = new JFrame("Tower");
        jFrame.setBackground(Color.BLACK);
        jFrame.setMinimumSize(new Dimension(800, 600));

        JPanel jPanel = new JPanel(new BorderLayout());

        jFrame.add(jPanel);

        jPanel.add(new Drawer());

        jFrame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        jFrame.setVisible(true);
        jPanel.setVisible(true);
    }
}
