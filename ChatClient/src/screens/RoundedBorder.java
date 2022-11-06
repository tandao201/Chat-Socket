package screens;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RoundedBorder extends LineBorder {

    private int radius;
    private Color color;

    RoundedBorder(int radius, Color color) {
        super(color);
        this.color = color;
        this.radius = radius;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius, this.radius, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
