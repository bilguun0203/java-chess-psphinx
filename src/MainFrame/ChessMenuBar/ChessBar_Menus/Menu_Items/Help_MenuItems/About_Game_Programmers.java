/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Help_MenuItems;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author sami
 */
public class About_Game_Programmers extends JMenuItem {

    /**
     * Creates a new instance of About_Game_Programmers
     */
    public About_Game_Programmers() {
        setText("Хөгжүүлэгчид");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String aboutDevelopers = "Хөгжүүлэгч:\n" +
                        "МУИС ХШУИС МКУТ Компьютерийн ухааны\n2-р түвшний оюутан О.Билгүүн\n" +
                        "";
                JOptionPane.showMessageDialog(null, aboutDevelopers);
            }
        });
    }

}/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
