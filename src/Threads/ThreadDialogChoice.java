package Threads;

import MainFrame.ChessFrame.MainFrame;
import MainFrame.ChessFrame.MainPanel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author bilguun
 */
public class ThreadDialogChoice implements Runnable {

    private MainFrame myFrame;
    private MainPanel Mainpanel;

    private Thread t;
    private String tName = "Scanner";
    private boolean running = true;

    public ThreadDialogChoice(MainFrame fr, MainPanel mp) {

        Mainpanel = mp;
        myFrame = fr;
    }

    public void run() {
        if(running) {
            String s = "";
            Process p;
            Scanner sc = new Scanner(System.in);
            String input = "voice";

            if (input.equals("text")) {
                while (running) {
                    s = sc.nextLine();
                    s = s.toLowerCase();
                    cmd(s);
                }
            } else if (input.equals("voice")) {
                try {
                    System.out.println("[EXECUTING] " + myFrame.getConfig("speechDialog"));
                    p = Runtime.getRuntime().exec(myFrame.getConfig("speechDialog"));
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));

                    while (running) {
                        System.out.println(s);
                        Thread.sleep(100);
                        s = s.toLowerCase();
                        cmd(s);
                    }
                    p.waitFor();
                    p.destroy();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("[ERROR] Оруулах төрлөө сонгоно уу");
            }
        }
        else {
            System.out.println("Thread: Stopping ThreadDialogChoice...");
        }
    }

    private void cmd(String s){
        boolean check = false;
        if(s.contains("нэг")){
            Mainpanel.dDialog.setChoice(1);
            check = true;
        }
        else if(s.contains("хоёр")){
            Mainpanel.dDialog.setChoice(2);
            check = true;
        }
        else if(s.contains("цуцал")){
            Mainpanel.dDialog.onCancel();
        }
        else {
            System.out.println("Команд олдсонгүй");
        }
        if(check) {
            running = false;
            Mainpanel.dDialog.onCancel();
        }
    }

    public void start() {
        if(t==null){
            System.out.println("Thread: Starting ThreadDialogChoice...");
            t = new Thread(this, tName);
            t.start();
        }
    }

    public void terminate(){
        running = false;
    }

}
