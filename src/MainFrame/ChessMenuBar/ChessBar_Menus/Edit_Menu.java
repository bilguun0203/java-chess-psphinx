/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
package MainFrame.ChessMenuBar.ChessBar_Menus;


import javax.swing.JMenu;

import MainFrame.ChessFrame.MainPanel;
import MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Edit_MenuItems.Redo_move;
import MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Edit_MenuItems.Undo_move;

/**
 * @author sami
 */
public class Edit_Menu extends JMenu {

    private MainPanel mainPanel;

    /**
     * Creates a new instance of Edit_Menu
     */
    public Edit_Menu(MainPanel mp) {
        mainPanel = mp;
        setText("Засах");
//        Uitem.setEnabled(false);//TODO : nemsen
        Ritem.setEnabled(false);//TODO : nemsen
        add(Uitem);
        add(Ritem);
    }

    private final Undo_move Uitem = new Undo_move(mainPanel);
    private final Redo_move Ritem = new Redo_move();
}