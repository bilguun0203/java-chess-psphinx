package MainFrame.ChessFrame;

import MainFrame.ChessFrame.ThreadTimer.ThreadTimer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;

public class ToolPanel extends JPanel {
    /**
     * Creates a new instance of ToolPanel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //        Graphics2D g2 = (Graphics2D)g;

        // draw a rectangle
        
       /* Ellipse2D start= new Ellipse2D.Double(100, 34, 30, 30);
        Ellipse2D stop= new Ellipse2D.Double(100, 234, 30, 30);
        g2.setColor(Color.RED.darker());
        g2.fill(start);
        g2.setColor(Color.GREEN.darker());
        g2.fill(stop);
        
        Ellipse2D surr1= new Ellipse2D.Double(100, 34, 30, 30);
        Ellipse2D surr2= new Ellipse2D.Double(100, 234, 30, 30);
        
        g2.setColor(Color.BLACK.brighter());
        
        
        g2.draw(surr1);
        g2.draw(surr2);*/


    }


    public ToolPanel() {
        setSize(200, 350);
        setLocation(620, 0);
        setLayout(null);


        JLturn1.setSize(160, 25);
        JLturn1.setLocation(20, 70);
        Screen1.setSize(100, 25);
        Screen1.setLocation(90, 94);

        JLblack.setSize(70, 25);
        JLblack.setLocation(20, 94);

        JLturn1.setEnabled(false);
        JLturn1.setBackground(Color.GRAY);
        JLturn1.setDisabledTextColor(Color.BLACK);
        JLturn1.setFont(new Font("Arial", Font.BOLD, 12));

        JLblack.setEnabled(false);
        JLblack.setBackground(new Color(33, 33, 33));
        JLblack.setForeground(new Color(220, 220, 220));
        JLblack.setFont(new Font("Arial", Font.BOLD, 12));
        JLblack.setDisabledTextColor(new Color(220, 220, 220));

        JLturn2.setSize(160, 25);
        JLturn2.setLocation(20, 10);
        Screen2.setSize(100, 25);
        Screen2.setLocation(90, 34);
        JLwhite.setSize(70, 25);
        JLwhite.setLocation(20, 34);

        JLturn2.setEnabled(false);
        JLturn2.setBackground(Color.GRAY);
        JLturn2.setDisabledTextColor(Color.BLACK);
        JLturn2.setFont(new Font("Arial", Font.BOLD, 12));

        JLwhite.setEnabled(false);
        JLwhite.setBackground(new Color(220, 220, 220));
        JLblack.setForeground(new Color(33, 33, 33));
        JLwhite.setFont(new Font("Arial", Font.BOLD, 12));
        JLwhite.setDisabledTextColor(new Color(33, 33, 33));

        //TODO: Nemsen
        command.setSize(170,40);
        command.setLocation(20,295);
//        inputCommand.setColumns(20);
//        inputCommand.undoMove(20,295);
//        inputCommand.setSize(90,20);
//        cmdButton.setSize(90, 20);
//        cmdButton.undoMove(120,295);

        add(JLturn1);
        add(JLblack);
        add(JLturn2);
        add(JLwhite);
        add(Screen1);
        add(Screen2);
//        add(inputCommand);//TODO: Nemsen
//        add(cmdButton);//TODO: Nemsen
        add(command);

        HistoryScroll.setSize(170, 150);
        HistoryScroll.setLocation(20, 130);
        add(HistoryScroll);


    }

    public void setturn(int player_turn) {
        if (player_turn == 1){
            JLturn2.setBackground(Color.CYAN);
            JLturn1.setBackground(Color.GRAY);
        }
        else if (player_turn == 2){
            JLturn1.setBackground(Color.CYAN);
            JLturn2.setBackground(Color.GRAY);
        }
    }

    public void add_to_History(Object newItem) {
        HistoryList.addElemen_tolist(newItem);
    }
    public void remove_from_History() {
        HistoryList.remove(HistoryList.getLastVisibleIndex());
    }

    public void change_to_Timer1() {
        Timer1.resume();
        Timer2.suspend();
    }

    public void change_to_Timer2() {
        Timer2.resume();
        Timer1.suspend();
    }

    public void stop_timers() {
        Timer1.stop();
        Timer2.stop();
    }

    public short getTime1(){
        return Timer1.getTime();
    }
    public short getTime2(){
        return Timer2.getTime();
    }

    private final JTextField JLturn1 = new JTextField(" Тоглогч 2-ын ээлж ");
    private final JTextField JLturn2 = new JTextField(" Тоглогч 1-ийн ээлж ");
    private final JTextField JLwhite = new JTextField("  Цагаан ");
    private final JTextField JLblack = new JTextField("  Хар ");
    private final myHistoryList HistoryList = new myHistoryList();
    private final short number_of_turn = 1;
    private final JScrollPane HistoryScroll = new JScrollPane(HistoryList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private final JLabel Screen1 = new JLabel();
    private final JLabel Screen2 = new JLabel();
    private final JLabel TimDesc1 = new JLabel(" Цаг 1");
    private final JLabel TimDesc2 = new JLabel(" Цаг 2");
//    public final JTextField inputCommand = new JTextField(); //TODO: Nemsen
//    public final JButton cmdButton = new JButton("Илгээх"); //TODO: Nemsen
    private final  JLabel command = new JLabel();
    private ThreadTimer Timer1;
    private ThreadTimer Timer2;

    public void start_Again() {
        if (Timer1 != null) {
            Timer1.stop();
            Timer2.stop();
        }

        Timer1 = new ThreadTimer(Screen2);
        Timer2 = new ThreadTimer(Screen1);

        Timer1.start();
        Timer2.start();
        Timer2.suspend();

        HistoryList.clean_list();
        HistoryList.addElemen_tolist("Тоглогч: Нүүдэл");

    }

    public void setCommand(String s){
        command.setText(s);
    }


}

class myHistoryList extends JList {
    myHistoryList() {

        this.setBackground(new Color(255, 193, 7));
        setModel(listModel);
        listModel.addElement("Тоглогч: Нүүдэл");

    }

    public void clean_list() {
        listModel.clear();
    }

    public void addElemen_tolist(Object newItem) {
        listModel.addElement(newItem);
    }

    private DefaultListModel listModel = new DefaultListModel();
}

class myStatusFileds extends JTextField {
    myStatusFileds() {
        this.setEnabled(false);
    }
}