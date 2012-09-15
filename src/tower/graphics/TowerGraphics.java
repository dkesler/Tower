package tower.graphics;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
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
