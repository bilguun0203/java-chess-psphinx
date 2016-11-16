package MainFrame.ChessFrame.ThreadTimer;


import ChessGame.ConfigFile;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.*;

/**
 * @author sami
 */
public class ThreadTimer extends Thread {

    /**
     * Creates a new instance of ThreadTimer
     */


    public ThreadTimer(JLabel mynewScreen) {
        myScreen = mynewScreen;
        myScreen.setForeground(Color.BLACK);
        if(mytime < 30){
            if(mytime % 2 == 0){
                myScreen.setForeground(Color.RED);
            }
            else {
                myScreen.setForeground(Color.BLACK);
            }
        }
        myScreen.setText(calcTime());
    }

    public void run() {

        while (true) {
            mytime -= 0.1;
            try {


                this.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

//            myScreen.setText(Float.toString(mytime) + " секунд");
            myScreen.setForeground(Color.BLACK);
            if(mytime < 30){
                if(mytime % 2 == 0){
                    myScreen.setForeground(Color.RED);
                }
                else {
                    myScreen.setForeground(Color.BLACK);
                }
            }
            myScreen.setText(calcTime());
            if (mytime == 0) {

                JOptionPane.showConfirmDialog(null, " Тоглоом дууслаа!\n Цаг дууссан", "Цаг дууссан", JOptionPane.DEFAULT_OPTION);
                stop();
                System.out.println("Цаг дууссан");


                break;
            }
        }


    }

    private ConfigFile conf = new ConfigFile();
    private short mytime = Short.parseShort(conf.getConfig("time"));
    private JLabel myScreen;

    private String calcTime(){
        String timeD = "-";
        int seconds = mytime;
        int minutes;
        if(seconds > 60){
            minutes = seconds / 60;
            seconds = seconds - (minutes*60);
            timeD = minutes + " мин " + seconds + " сек";
        }
        else
            timeD = seconds + " секунд";
        return timeD;
    }

    public short getTime(){
        return mytime;
    }
}