package tower.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends Panel {

    private final List<String> options;

    public MenuPanel() {
        options = new ArrayList<>();
        bgColor = Color.GRAY;
        borderColor = Color.GRAY;
        width = 200;
    }

    public void addOption(String option) {
        options.add(option);
        height = options.size() * 16 + 5;
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {

        int x = this.x + 5;
        int y = this.y + 16;

        graphics2D.setColor(Color.WHITE);
        for (String option : options) {
            graphics2D.drawString(option, x, y);
            y += 16;
        }
    }

    public String getSelectedOption(Point2D mouseCoord) {
        int idx = (int) ((mouseCoord.getY() - this.getY())/16);
        if (idx < 0 || idx >= options.size()) {
            return null;
        } else {
            return options.get(idx);
        }
    }
}
