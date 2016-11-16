
package MainFrame.ChessFrame;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import ChessGame.ConfigFile;
import MainFrame.ChessMenuBar.Chess_MainMenuBar;
import MainFrame.ChessFrame.ChatPanel;
import MainFrame.ChessFrame.StatusPanel;
import Threads.ThreadScan;

/**
 * @author bilguun
 */
public class MainFrame extends JFrame {


    public MainFrame() {
        setTitle("Шатар");
        setSize(820, 720);
        setResizable(false);

        contentPane.setLayout(null);
        contentPane.add(Chatpanel);
        contentPane.add(Satuspanel);
        contentPane.add(Toolpanel);

        MyChessBar = new Chess_MainMenuBar(this, Mainpanel);

        setJMenuBar(MyChessBar);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        startThreadScan();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
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

    public void start_Again() {
//        ThreadScan t = new ThreadScan(Mainpanel);
//        t.start();
        Mainpanel.start_Again();
        contentPane.add(Mainpanel);
        contentPane.add(CoordsX);
        contentPane.add(CoordsY);
        repaint();

    }

    public void start_As_Server() {
//        ThreadScan t = new ThreadScan(Mainpanel);
//        t.start();
        Mainpanel.start_As_Server(MyChessBar.getIpAddress(), MyChessBar.getPortnumber(), Chatpanel);

        contentPane.add(Mainpanel);
        contentPane.add(CoordsX);
        contentPane.add(CoordsY);
        repaint();

        setTitle("Сервер тоглогч");

    }

    public void start_As_Client() {
//        ThreadScan t = new ThreadScan(Mainpanel);
//        t.start();

        Mainpanel.start_As_Client(MyChessBar.getIpAddress(), MyChessBar.getPortnumber(), Chatpanel);

        contentPane.add(Mainpanel);
        contentPane.add(CoordsX);
        contentPane.add(CoordsY);
        repaint();
        setTitle("Клиент тоглогч");
    }

    public void startThreadScan(){
        t = new ThreadScan(this, Mainpanel);
        t.start();
    }

    public String getConfig(String var){
        return conf.getConfig(var);
    }

    public void setConfig(String var, String val){
        conf.setConfig(var, val);
    }

    private final Chess_MainMenuBar MyChessBar;
    private final ToolPanel Toolpanel = new ToolPanel();
    private final StatusPanel Satuspanel = new StatusPanel();

    public final MainPanel Mainpanel = new MainPanel(Toolpanel, Satuspanel, this);
    private final ConfigFile conf = new ConfigFile();
    private final ChatPanel Chatpanel = new ChatPanel();
    private final CoordsPanelX CoordsX = new CoordsPanelX();
    private final CoordsPanelY CoordsY = new CoordsPanelY();
    private Container contentPane = getContentPane();

    private ThreadScan t;

}


