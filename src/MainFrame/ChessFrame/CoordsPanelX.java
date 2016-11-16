package MainFrame.ChessFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bilguun on 10/23/16.
 */
public class CoordsPanelX extends JPanel {
    private final JLabel A = new JLabel("A");
    private final JLabel B = new JLabel("B");
    private final JLabel C = new JLabel("C");
    private final JLabel D = new JLabel("D");
    private final JLabel E = new JLabel("E");
    private final JLabel F = new JLabel("F");
    private final JLabel G = new JLabel("G");
    private final JLabel H = new JLabel("H");

    public CoordsPanelX(){
        setSize(600, 30);
        setLocation(3, 610);
        setLayout(null);
        setBackground(new Color(33,33,33));

        A.setLocation(27, 5);
        A.setSize(20, 20);
        A.setForeground(new Color(238,238,238));
        B.setLocation(102, 5);
        B.setSize(20, 20);
        B.setForeground(new Color(238,238,238));
        C.setLocation(175, 5);
        C.setSize(20, 20);
        C.setForeground(new Color(238,238,238));
        D.setLocation(250, 5);
        D.setSize(20, 20);
        D.setForeground(new Color(238,238,238));
        E.setLocation(325, 5);
        E.setSize(20, 20);
        E.setForeground(new Color(238,238,238));
        F.setLocation(400, 5);
        F.setSize(20, 20);
        F.setForeground(new Color(238,238,238));
        G.setLocation(475, 5);
        G.setSize(20, 20);
        G.setForeground(new Color(238,238,238));
        H.setLocation(550, 5);
        H.setSize(20, 20);
        H.setForeground(new Color(238,238,238));

        add(A);
        add(B);
        add(C);
        add(D);
        add(E);
        add(F);
        add(G);
        add(H);
    }

}
