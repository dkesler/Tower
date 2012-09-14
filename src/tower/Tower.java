package tower;

import tower.graphics.TowerGraphics;

import java.io.IOException;

public class Tower {
    public static void main(String[] args) throws IOException {
        TowerGraphics towerGraphics = new TowerGraphics();

        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException("Exception while sleeping", e);
            }

            towerGraphics.repaint();
        }
    }
}
