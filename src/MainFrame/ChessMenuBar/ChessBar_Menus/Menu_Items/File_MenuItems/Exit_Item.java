/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.File_MenuItems;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author sami
 */
public class Exit_Item extends JMenuItem {

    /**
     * Creates a new instance of Exit_Item
     */
    public Exit_Item() {
        setText("Гарах");
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Тийм",
                        "Үгүй"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Тоглоомоос гарах уу?",
                        "Тоглоомоос гарах",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);
                if(choice == 0 ){
                    System.exit(0);
                }
            }
        });

    }

}/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
