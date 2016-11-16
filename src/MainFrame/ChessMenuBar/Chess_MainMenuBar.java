
package MainFrame.ChessMenuBar;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import MainFrame.ChessFrame.MainPanel;
import MainFrame.ChessMenuBar.ChessBar_Menus.File_Menu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Edit_Menu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Tools_Menu;
import MainFrame.ChessMenuBar.ChessBar_Menus.Help_Menu;
import MainFrame.ChessFrame.MainFrame;

/**
 * @author bilguun
 */
public class Chess_MainMenuBar extends JMenuBar {

    private MainPanel mainPanel;

    /**
     * Creates a new instance of Chess_MainMenuBar
     */
    public Chess_MainMenuBar(MainFrame ff, MainPanel mp) {
        mainPanel = mp;
        Fmenu = new File_Menu(ff);
        add(Fmenu);
        add(Emenu);
        add(Tmenu);
        add(Hmenu);

    }

    public String getIpAddress() {
        return Fmenu.getIPaddress();
    }

    public String getPortnumber() {
        return Fmenu.getportNumber();
    }

    private final File_Menu Fmenu;
    private final Edit_Menu Emenu = new Edit_Menu(mainPanel);
    private final Tools_Menu Tmenu = new Tools_Menu();
    private final Help_Menu Hmenu = new Help_Menu();

}