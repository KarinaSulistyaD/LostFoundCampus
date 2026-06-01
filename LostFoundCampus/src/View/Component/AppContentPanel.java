package View.Component;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class AppContentPanel extends JPanel {

    public AppContentPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    public AppContentPanel() {
        setOpaque(false);
    }
}
