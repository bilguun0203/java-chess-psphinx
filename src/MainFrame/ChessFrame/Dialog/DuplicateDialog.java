package MainFrame.ChessFrame.Dialog;

import javax.swing.*;
import MainFrame.ChessFrame.*;
import Threads.ThreadDialogChoice;
import Threads.ThreadScan;

import java.awt.event.*;

/**
 * Created by bilguun on 2016-10-30.
 */
public class DuplicateDialog extends JDialog {

    public DuplicateDialog(MainFrame frame, String s1, String s2) {
        super(frame,"Давхцсан нүүдэл",true);

        myFrame = frame;
        setModal(true);

        t = new ThreadDialogChoice(myFrame, myFrame.Mainpanel);
        t.start();

        setSize(220,135);
        setLocation(340,300);
        setContentPane(panel);

        panel.setLayout(null);
        panel.setSize(220,135);

        question.setSize(190,18);
        question.setLocation(5,5);
        question.setText("Аль дүрсийг нүүлгэх вэ?");
        no1.setSize(190,18);
        no1.setLocation(5,28);
        no1.setText("1. "+s1);
        no2.setSize(190,18);
        no2.setLocation(5,51);
        no2.setText("2. "+s2);

        btn1.setSize(50,24);
        btn2.setSize(50,24);
        btn_cancel.setSize(90,24);
        btn1.setLocation(10,74);
        btn2.setLocation(65,74);
        btn_cancel.setLocation(120,74);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choice = 1;
                onCancel();
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choice = 2;
                onCancel();
            }
        });

        btn_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onCancel();
            }
        });

        btn_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onCancel();
            }
        });

        panel.add(question);
        panel.add(no1);
        panel.add(no2);
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn_cancel);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        panel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void onCancel(){
        t.terminate();
        dispose();
    }

    public void setChoice(int n){
        choice = n;
    }

    public int getChoice(){
        return choice;
    }

    private final JPanel panel = new JPanel();
    private final JButton btn1 = new JButton("1");
    private final JButton btn2 = new JButton("2");
    private final JButton btn_cancel = new JButton("Цуцлах");
    private final JLabel question = new JLabel("",SwingConstants.CENTER);
    private final JLabel no1 = new JLabel("",SwingConstants.CENTER);
    private final JLabel no2 = new JLabel("",SwingConstants.CENTER);
    private int choice = 0;

    private final MainFrame myFrame;

    public ThreadDialogChoice t;

}
