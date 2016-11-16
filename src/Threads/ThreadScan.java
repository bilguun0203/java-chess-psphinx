package Threads;

import MainFrame.ChessFrame.MainFrame;
import MainFrame.ChessFrame.MainPanel;
import MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.File_MenuItems.newGame_Dialoge.NewGameDialoge;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author bilguun
 */
public class ThreadScan implements Runnable {

    private MainFrame myFrame;
    private MainPanel Mainpanel;
    private final NewGameDialoge Ndial;

    private Thread t;
    private String tName = "Scanner";
    private boolean running = true;

    public ThreadScan(MainFrame fr, MainPanel mp) {

        Mainpanel = mp;
        myFrame = fr;
        Ndial = new NewGameDialoge(fr);
    }

    public void run() {
        if(running) {
            String s = "";
            Process p;
            Scanner sc = new Scanner(System.in);
            String input = "voice";

            if (input.equals("text")) {
                while (!s.equals("-1")) {
                    s = sc.nextLine();
                    cmd(s);
                }
            } else if (input.equals("voice")) {
                try {
                    System.out.println("[EXECUTING] " + myFrame.getConfig("speechScan"));
                    p = Runtime.getRuntime().exec(myFrame.getConfig("speechScan"));
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));

                    while (!(s = br.readLine()).equals("-1")) {
                        System.out.println(s);
                        Thread.sleep(100);
                        cmd(s);
                    }
                    p.waitFor();
                    p.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error: " + e.getStackTrace());
                    System.out.println("Error: " + e.getMessage());
                    terminate(true);
                }
            } else {
                System.out.println("[ERROR] Оруулах төрлөө сонгоно уу");
                cmd("дуусга");
            }
        }
        else {
            System.out.println("Thread: Stopping ThreadScan...");
        }
    }

    public void start() {
        if(t==null){
            System.out.println("Thread: Starting ThreadScan...");
            t = new Thread(this, tName);
            t.start();
        }
    }

    public void terminate(boolean startAgain){
        if(startAgain){
            myFrame.startThreadScan();
        }
        running = false;
    }

    private void cmd(String s){

        s = s.toLowerCase();

        String[] shapes = {"хүү", "морь", "тэрэг", "тэмээ", "бэрс", "ноён"};

        if(s.contains("нүү") || s.contains("түлх")){
            Mainpanel.newPosition();
            Mainpanel.myTool.setCommand("");
        }
//            else if(s.contains("ухар")){
//                Mainpanel.toOldPosition(s);
//            }
        else if(s.contains("буц")){
            Mainpanel.undoMove();
        }
        else if(s.contains("эхэл")){
            if(!Mainpanel.isGameRunning()) {
                myFrame.start_Again();
            }
        }
        else if(s.contains("дуусга")){
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
        else {
            boolean found = false;
            for (String shape : shapes) {
                if (s.contains(shape)) {
                    Mainpanel.setCommand(s);
                    found = true;
                    break;
                }
            }
            if(!found){
                Mainpanel.myStatus.addStatus(" - Комманд олдсонгүй");
                System.out.println("Команд олдсонгүй");
            }
        }
    }

}
