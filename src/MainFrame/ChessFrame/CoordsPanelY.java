package MainFrame.ChessFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bilguun on 10/23/16.
 */
public class CoordsPanelY extends JPanel {
    private final JLabel N1 = new JLabel("1");
    private final JLabel N2 = new JLabel("2");
    private final JLabel N3 = new JLabel("3");
    private final JLabel N4 = new JLabel("4");
    private final JLabel N5 = new JLabel("5");
    private final JLabel N6 = new JLabel("6");
    private final JLabel N7 = new JLabel("7");
    private final JLabel N8 = new JLabel("8");

    public CoordsPanelY(){
        setSize(30, 625);
        setLocation(603, 10);
        setLayout(null);
        setBackground(new Color(33,33,33));

        N8.setLocation(5, 20);
        N8.setSize(20, 20);
        N8.setForeground(new Color(238,238,238));
        N7.setLocation(5, 95);
        N7.setSize(20, 20);
        N7.setForeground(new Color(238,238,238));
        N6.setLocation(5, 170);
        N6.setSize(20, 20);
        N6.setForeground(new Color(238,238,238));
        N5.setLocation(5, 245);
        N5.setSize(20, 20);
        N5.setForeground(new Color(238,238,238));
        N4.setLocation(5, 320);
        N4.setSize(20, 20);
        N4.setForeground(new Color(238,238,238));
        N3.setLocation(5, 395);
        N3.setSize(20, 20);
        N3.setForeground(new Color(238,238,238));
        N2.setLocation(5, 470);
        N2.setSize(20, 20);
        N2.setForeground(new Color(238,238,238));
        N1.setLocation(5, 545);
        N1.setSize(20, 20);
        N1.setForeground(new Color(238,238,238));

        add(N8);
        add(N7);
        add(N6);
        add(N5);
        add(N4);
        add(N3);
        add(N2);
        add(N1);
    }

}
