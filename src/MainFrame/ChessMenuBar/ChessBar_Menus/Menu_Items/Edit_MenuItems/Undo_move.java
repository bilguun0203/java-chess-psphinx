/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Edit_MenuItems;

import MainFrame.ChessFrame.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bilguun
 */

public class Undo_move extends JMenuItem {

    /**
     * Creates a new instance of Undo_move
     */

    public Undo_move(MainPanel mainPanel) {

        MainPanel mp = mainPanel;
        setText("Үйлдэл буцаах");

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mp.undoMove();
//                if(mp.isGameRunning() && mp.getTurn() >= 1 && mp.getUndos() == 0)
//                    mp.undoMove();
            }
        });

    }

}