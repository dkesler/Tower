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
        height = options.size() * 16 + 8;
    }

    @Override
    protected void drawImplSpecific(Graphics2D graphics2D, Point2D mouseCoord) {

        int x = this.x + 5;
        int y = this.y + 16;

        String selectedOption = getSelectedOption(mouseCoord);
        for (String option : options) {
            if (option.equals(selectedOption)) {
                graphics2D.setColor(Color.LIGHT_GRAY);
                graphics2D.fillRect(x, y + 4, width - 10, -16);
            }
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(option, x, y);
            y += 16;
        }
    }

    public String getSelectedOption(Point2D mouseCoord) {

        if (mouseCoord.getX() < x || mouseCoord.getX() > x + width) {
            return null;
        }

        int idx = (int) ((mouseCoord.getY() - y)/16);
        if (idx < 0 || idx >= options.size()) {
            return null;
        } else {
            return options.get(idx);
        }
    }
}
