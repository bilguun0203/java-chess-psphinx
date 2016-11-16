
package MainFrame.ChessFrame;

import MainFrame.ChessFrame.Dialog.DuplicateDialog;
import MainFrame.ChessFrame.players.player1;
import MainFrame.ChessFrame.players.player2;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.event.*;
import java.lang.String;

/**
 * @author bilguun
 */

public class MainPanel extends JPanel {

    private player1 P1 = new player1();
    private player2 P2 = new player2();
    private final int Divide = 600 / 8;
    private int move = 0;
    private Rectangle2D rec;
    private short players_turn = 1;
    public final ToolPanel myTool;
    public final StatusPanel myStatus;
    private boolean GameOver = false;
    private boolean Iam_Server = false;
    private boolean Iam_Client = false;
    private ServerSocket ServerSock;
    private Socket Sock;
    private BufferedReader in;
    private PrintWriter out;
    private String Box;
    private boolean local = true;
    private JButton startServer;
    private JButton startClient;
    private String MyIp_Address;
    private String MyPort_number;
    private boolean Game_started = true;
    private Recv_Thread Recv_from;
    private ChatPanel Refe_Chat;
    // TODO: Nemsen
    private boolean isGameRunning = false;
    private int turn = 0;
    private int lastInhand = -1;
    private Point targetP = new Point(-1,-1);
    private int inHandG1 = -1;
    private int inHandG2 = -1;
    private int undos = 0;
    private int lastKilled = -1;
    private Point lastKilledP = new Point(-1,-1);
    private MainFrame myFrame;
    public DuplicateDialog dDialog;
    private volatile int choice;

    public void start_As_Server(String Ip, String Port, ChatPanel newChat) {

        Recv_from = new Recv_Thread();
        Refe_Chat = newChat;
        Game_started = false;

        MyIp_Address = Ip;
        MyPort_number = Port;


        start_Again();
        startServer = new JButton(" Сервер эхлүүлэх");
        startServer.setSize(180, 25);
        startServer.setLocation(200, 300);
        startServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {


                    ServerSock = new ServerSocket(Integer.parseInt(MyPort_number));

                    Thread Server = new Thread(new Runnable() {
                        public synchronized void run() {

                            try {

                                Sock = ServerSock.accept();

                                Refe_Chat.listen_chat();
                                in = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
                                out = new PrintWriter(Sock.getOutputStream());
                                startServer.setVisible(false);
                                startServer = null;
                                Recv_from.start();

                                Game_started = true;

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });


                    Server.start();
             /*in=new BufferedReader(new InputStreamReader(Sock.getInputStream()));
             out=new PrintWriter(Sock.getOutputStream());*/
                    // Sock.setSoTimeout(999999);
                    //  Refe_Chat.listen_chat();


                } catch (IOException ex) {
                    ex.printStackTrace();

                    JOptionPane.showConfirmDialog(null, "Серверийн алдаа", "Алдаа", JOptionPane.ERROR_MESSAGE);
                }
                startServer.setText("Хүлээж байна...");


            }

        });
        local = false;
        add(startServer);


        Iam_Server = true;

        myTool.setturn(players_turn);
        repaint();
    }

    public void start_As_Client(String Ip, String Port, ChatPanel newChat) {


        Recv_from = new Recv_Thread();

        Refe_Chat = newChat;

        Game_started = false;


        start_Again();
        MyIp_Address = Ip;
        MyPort_number = Port;
        local = false;
        startClient = new JButton("Клиент эхлүүлэх");
        startClient.setSize(180, 25);
        startClient.setLocation(200, 300);

        startClient.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {

                    Sock = new Socket(MyIp_Address, Integer.parseInt(MyPort_number));
                    in = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
                    out = new PrintWriter(Sock.getOutputStream());


                    Recv_from.start();
                    Game_started = true;
                    Refe_Chat.start_chat();


                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showConfirmDialog(null, "Клиентийн алдаа", "Алдаа", JOptionPane.ERROR_MESSAGE);
                }

                startClient.setVisible(false);
                startClient = null;
            }
        });


        Iam_Client = true;
        add(startClient);

        myTool.setturn(players_turn);

    }


    public void start_Again() {
        P1 = new player1();
        P2 = new player2();
        move = 0;
        players_turn = 1;
        GameOver = false;
        local = true;
        myTool.start_Again();
        myStatus.start_Again();
        Iam_Server = false;
        Iam_Client = false;
        myTool.setturn(players_turn);
        repaint();
        isGameRunning = true; //TODO: Nemsen
    }

    public MainPanel(ToolPanel myToolPanel, StatusPanel myStatusPanel, MainFrame frame) {
        setBackground(Color.WHITE);

        setSize(600, 600);
        setLocation(3, 10);

        MousewhenMove mouseDragAndDrop = new MousewhenMove();
        Mousehere mouseHereEvent = new Mousehere();
        addMouseMotionListener(mouseDragAndDrop);
        addMouseListener(mouseHereEvent);

        myTool = myToolPanel;
        myStatus = myStatusPanel;

        setLayout(null);

        myFrame = frame;

        //TODO: Nemsen
//        ActionListener CommandAction = new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                String text = myTool.inputCommand.getText();
//                if(text.equals("")){
//                    myStatus.addStatus(" - хоосон байна");
//                    System.out.println("Хоосон байна");
//                }
//                else if(text.equalsIgnoreCase("start") && !isGameRunning){
//                    myTool.inputCommand.setText("");
//                }
//                else if(isGameRunning) {
//
//                    myTool.inputCommand.setText("");
//                    String[] words = text.split(" ");
//
//                    Point cP = returnPoint(words[0]);
//                    Point nP = returnPoint(words[1]);
//                    if (cP.x == -1 || cP.y == -1 || nP.x == -1 || nP.y == -1) {
//                        myStatus.addStatus(" - байрлал буруу");
//                        System.out.println("[ АЛДАА ] ");
//                    } else {
//                        int inhand = -1;
//
//                        if (!GameOver) {
//
//                            for (int i = 1; i < 33; i++) {
//                                Point pieceP = P2.returnPostion(i);
//                                if (pieceP.x == cP.x && pieceP.y == cP.y) {
//                                    inhand = i;
//                                    break;
//                                }
//                                pieceP = P1.returnPostion(i);
//                                if (pieceP.x == cP.x && pieceP.y == cP.y) {
//                                    inhand = i;
//                                    break;
//                                }
//                            }
//
//                            movePieces(nP, inhand);
//
//                        }
//                    }
//                }
//                else {
//                    myStatus.addStatus(" - тоглоом эхлээгүй байна");
//                    System.out.println("[ АЛДАА ] ");
//                    myTool.inputCommand.setText("");
//                }
//            }
//        };
//        myTool.cmdButton.addActionListener(CommandAction);
//        myTool.inputCommand.addActionListener(CommandAction);

//        myTool.cmdButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                String text = myTool.inputCommand.getText();
//                if(text.equals("")){
//                    System.out.println("Hooson baina");
//                }
//                else if(text.equalsIgnoreCase("start") && !isGameRunning){
//                    myTool.inputCommand.setText("");
//                    mynewFrame.start_Again();
////                    start_Again();
//                }
//                else if(isGameRunning) {
//                    String[] words = text.split(" ");
//                    System.out.println(words.length);
//                    myTool.inputCommand.setText("");
//                }
//                else {
//                    System.out.println("[ АЛДАА ] ");
//                    myTool.inputCommand.setText("");
//                }
//            }
//        });
//        myTool.inputCommand.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                String text = myTool.inputCommand.getText();
//                if(text.equals("")){
//                    System.out.println("Hooson baina");
//                }
//                else if(text.equalsIgnoreCase("start") && !isGameRunning){
//                    myTool.inputCommand.setText("");
//                    mynewFrame.start_Again();
////                    start_Again();
//                }
//                else if(isGameRunning) {
//                    String[] words = text.split(" ");
//                    System.out.println(words.length);
//                    myTool.inputCommand.setText("");
//                }
//                else {
//                    System.out.println("[ АЛДАА ] ");
//                    myTool.inputCommand.setText("");
//                }
//            }
//        });


    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int iWidth = 600;

        // Drawing the board
        for (int i = 0; i < 8; i = i + 2) {
            for (int j = 0; j < 8; j = j + 2) {

                g2.setColor(new Color(44,44,44));
                rec = new Rectangle2D.Double(j * iWidth / 8, (1 + i) * iWidth / 8, Divide, Divide);
                g2.fill(rec);
                rec = new Rectangle2D.Double((1 + j) * iWidth / 8, i * iWidth / 8, Divide, Divide);
                g2.fill(rec);

            }
        }
        for (int i = 0; i < 8; i = i + 2) {
            for (int j = 0; j < 8; j = j + 2) {

                g2.setColor(new Color(238, 238, 238));
                rec = new Rectangle2D.Double(j * iWidth / 8, i * iWidth / 8, Divide, Divide);
                g2.fill(rec);
                rec = new Rectangle2D.Double((1 + j) * iWidth / 8, (1 + i) * iWidth / 8, Divide, Divide);
                g2.fill(rec);

            }
        }

        // Target Position
        int thickness = 5;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(new Color(242, 38, 19));
        if(targetP.x != -1 && targetP.y != -1){
            rec = new Rectangle2D.Double((targetP.x-1) * iWidth / 8, (targetP.y-1) * iWidth / 8, Divide - 2, Divide - 2);
            g2.draw(rec);
        }

        // Piece position
        Point p1 = new Point(-1,-1);
        Point p2 = new Point(-1,-1);
        g2.setColor(new Color(219, 10, 91));
        if(players_turn == 1){
            if(inHandG1 != -1){
                p1 = P1.returnPostion(inHandG1);
            }
            if(inHandG2 != -1){
                p2 = P1.returnPostion(inHandG2);
            }
        }
        else if(players_turn == 2){
            if(inHandG1 != -1){
                p1 = P2.returnPostion(inHandG1);
            }
            if(inHandG2 != -1){
                p2 = P2.returnPostion(inHandG2);
            }
        }
        if(p1.x != -1 && p1.y != -1) {
            rec = new Rectangle2D.Double((p1.x - 1) * iWidth / 8, (p1.y - 1) * iWidth / 8, Divide -2, Divide -2);
            g2.draw(rec);
        }
        if(p2.x != -1 && p2.y != -1) {
            rec = new Rectangle2D.Double((p2.x - 1) * iWidth / 8, (p2.y - 1) * iWidth / 8, Divide -2, Divide-2);
            g2.draw(rec);
        }
        g2.setStroke(oldStroke);

        /// Puting the pieces
        Point postionPoint;
        int postX;
        int postY;
        Image img;
        for (int i = 1; i <= 32; i++) {
            if (i < 17) {
                if (i == P2.GetInhand()) {
                    postionPoint = P2.getPixelPoint(i);

                } else {
                    postionPoint = P2.returnPostion(i);
                }
                img = P2.returnIconImage(i);

            } else {


                if (i == P1.GetInhand()) {

                    postionPoint = P1.getPixelPoint(i);

                } else {
                    postionPoint = P1.returnPostion(i);
                }
                img = P1.returnIconImage(i);
            }


            if (i == P1.GetInhand())
                g2.drawImage(img, postionPoint.x - 25, postionPoint.y - 25, Divide - 40, Divide - 12, this);
            else if (i == P2.GetInhand())
                g2.drawImage(img, postionPoint.x - 25, postionPoint.y - 25, Divide - 40, Divide - 12, this);
            else {
                postX = rowToX(postionPoint.x);
                postY = colToY(postionPoint.y);
                g2.drawImage(img, postX + 20, postY + 4, Divide - 40, Divide - 12, this);
            }


        }


    }

    /// You can inherit from Adapter and avoid meaningless
    private class Mousehere implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            checkTime();

        }

        public void mousePressed(MouseEvent e) {
            checkTime();

        }

        public void mouseReleased(MouseEvent e) {
            checkTime();
            if (!GameOver) {

                if(P1.GetInhand() != -1){
                    Point newP;
                    newP = P1.getPixelPoint(P1.GetInhand());
                    newP.x /= Divide;
                    newP.y /= Divide;
                    newP.x++;
                    newP.y++;

                    movePieces(newP, P1.GetInhand());
                }
                if(P2.GetInhand() != -1){
                    Point newP;
                    newP = P2.getPixelPoint(P2.GetInhand());
                    newP.x /= Divide;
                    newP.y /= Divide;
                    newP.x++;
                    newP.y++;

                    movePieces(newP, P2.GetInhand());
                }

            }

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }

    // TODO: Nemsen
    public void setCommand(String s){
//        lastCommand = s;
        checkTime();
        myTool.setCommand(s);
        inHandG1 = -1;
        inHandG2 = -1;
        targetP.x = -1;
        targetP.y = -1;
        String[] words = s.split(" ");
//        String shape = "";
        int inhand = -1;
        int inhand2 = -1;
        int doub = 0;
        // Soldier huu W:25-32 B:9-16
        // Castle tereg W:17,18 B:1,2
        // Horse mori W:19,20 B:3,4
        // Elephent temee W:21,22 B:5,6
        // Queen bers W:23 B:7
        // King noyon W:24 B:8

        Point target = returnPointCryllic(words[1], words[2]);
//        for(int i=0; i<words.length; i++){
//            if(shape.equals("")){
//                if(words[i].equalsIgnoreCase("хүү")){
//                    shape = "хүү";
//                }
//                else if(words[i].equalsIgnoreCase("тэрэг")){
//                    shape = "тэрэг";
//                }
//                else if(words[i].equalsIgnoreCase("тэмээ")){
//                    shape = "тэмээ";
//                }
//                else if(words[i].equalsIgnoreCase("морь")){
//                    shape = "морь";
//                }
//                else if(words[i].equalsIgnoreCase("бэрс")){
//                    shape = "бэрс";
//                }
//                else if(words[i].equalsIgnoreCase("ноён")){
//                    shape = "ноён";
//                }
//            }
//            else {
//                if (words[i].equalsIgnoreCase("эй")) {
//                    target = returnPointCryllic(1, words[i + 1]);
//                    break;
//                } else if (words[i].equalsIgnoreCase("бий")) {
//                    target = returnPointCryllic(2, words[i + 1]);
//                    break;
//                } else if (words[i].equalsIgnoreCase("сий")) {
//                    target = returnPointCryllic(3, words[i + 1]);
//                    break;
//                } else if (words[i].equalsIgnoreCase("дий")) {
//                    target = returnPointCryllic(4, words[i + 1]);
//                    break;
//                } else if (words[i].equalsIgnoreCase("ий")) {
//                    target = returnPointCryllic(5, words[i + 1]);
//                    break;
//                } else if (words[i].equalsIgnoreCase("ээф")) {
//                    target = returnPointCryllic(6, words[i + 1]);
//                    break;
//                } else if (words[i].equalsIgnoreCase("жий")) {
//                    target = returnPointCryllic(7, words[i + 1]);
//                    break;
//                } else if (words[i].equalsIgnoreCase("эйч")) {
//                    target = returnPointCryllic(8, words[i + 1]);
//                    break;
//                }
//            }
//        }
        if(target.x == -1 || target.y == -1){
            myStatus.addStatus(" - байрлал буруу");
            System.out.println("[ АЛДАА ] Буруу байрлал");
        }
        else{
            if (!GameOver && isGameRunning) {
                boolean flag = false;
                if(players_turn == 1) {
                    flag = P1.Pice_already_there(target);
                }
                else if(players_turn == 2){
                    flag = P2.Pice_already_there(target);
                }
                if(flag) {
                    switch (words[0]) {
                        case "хүү":
                            if (players_turn == 1) {
                                for (int i = 25; i < 33; i++) {
                                    Point samePostion;
                                    for (int j = 1; j < 17; j++) {
                                        samePostion = P2.returnPostion(j);
                                        if (samePostion.x == target.x && samePostion.y == target.y) {
                                            if (P1.setSeentoSiliders(i, samePostion))
                                                System.out.println("setSeentoSilidersPre");
                                            break;
                                        }
                                    }
                                    if (P1.checkthemove(target, i) && !checkWay(i, target)) {
                                        doub++;
                                        if (doub != 2) {
                                            inhand = i;
                                        } else if (doub == 2) {
                                            inhand2 = i;
                                        }
                                    }
                                }
                            } else if (players_turn == 2) {
                                for (int i = 9; i < 17; i++) {
                                    Point samePostion;
                                    for (int j = 17; j < 33; j++) {
                                        samePostion = P1.returnPostion(j);
                                        if (samePostion.x == target.x && samePostion.y == target.y) {
                                            if (P2.setSeentoSiliders(i, samePostion))
                                                System.out.println("setSeentoSilidersPre");
                                            break;
                                        }
                                    }
                                    if (P2.checkthemove(target, i) && !checkWay(i, target)) {
                                        doub++;
                                        if (doub != 2) {
                                            inhand = i;
                                        } else if (doub == 2) {
                                            inhand2 = i;
                                        }
                                    }
                                }
                            } else {
                                System.out.println("[ АЛДАА ] players_turn");
                            }
                            break;
                        case "морь":
                            if (players_turn == 1) {
                                if (P1.checkthemove(target, 19) && !checkWay(19, target)) {
                                    doub++;
                                    inhand = 19;
                                }
                                if (P1.checkthemove(target, 20) && !checkWay(20, target)) {
                                    doub++;
                                    if (inhand == 19) {
                                        inhand2 = 20;
                                    } else {
                                        inhand = 20;
                                    }
                                }
                            } else if (players_turn == 2) {
                                if (P2.checkthemove(target, 3) && !checkWay(3, target)) {
                                    inhand = 3;
                                    doub++;
                                }
                                if (P2.checkthemove(target, 4) && !checkWay(4, target)) {
                                    doub++;
                                    if (inhand == 3) {
                                        inhand2 = 4;
                                    } else {
                                        inhand = 4;
                                    }
                                }
                            } else {
                                System.out.println("[ АЛДАА ] players_turn");
                            }
                            break;
                        case "тэрэг":
                            if (players_turn == 1) {
                                if (P1.checkthemove(target, 17) && !checkWay(17, target)) {
                                    inhand = 17;
                                    doub++;
                                }
                                if (P1.checkthemove(target, 18) && !checkWay(18, target)) {
                                    doub++;
                                    if (inhand == 17) {
                                        inhand2 = 18;
                                    } else {
                                        inhand = 18;
                                    }
                                }
                            } else if (players_turn == 2) {
                                if (P2.checkthemove(target, 1) && !checkWay(1, target)) {
                                    inhand = 1;
                                    doub++;
                                }
                                if (P2.checkthemove(target, 2) && !checkWay(2, target)) {
                                    doub++;
                                    if (inhand == 1) {
                                        inhand2 = 2;
                                    } else {
                                        inhand = 2;
                                    }
                                }
                            } else {
                                System.out.println("[ АЛДАА ] players_turn");
                            }
                            break;
                        case "тэмээ":
                            if (players_turn == 1) {
                                if (P1.checkthemove(target, 21) && !checkWay(21, target)) {
                                    inhand = 21;
                                    doub++;
                                }
                                if (P1.checkthemove(target, 22) && !checkWay(22, target)) {
                                    inhand = 22;
                                    doub++;
                                }
                            } else if (players_turn == 2) {
                                if (P2.checkthemove(target, 5) && !checkWay(5, target)) {
                                    inhand = 5;
                                    doub++;
                                }
                                if (P2.checkthemove(target, 6) && !checkWay(6, target)) {
                                    inhand = 6;
                                    doub++;
                                }
                            } else {
                                System.out.println("[ АЛДАА ] players_turn");
                            }
                            break;
                        case "бэрс":
                            if (players_turn == 1) {
                                if (P1.checkthemove(target, 23) && !checkWay(23, target)) {
                                    inhand = 23;
                                    doub++;
                                }
                            } else if (players_turn == 2) {
                                if (P2.checkthemove(target, 7) && !checkWay(7, target)) {
                                    inhand = 7;
                                    doub++;
                                }
                            } else {
                                System.out.println("[ АЛДАА ] players_turn");
                            }
                            break;
                        case "ноён":
                            if (players_turn == 1) {
                                if (P1.checkthemove(target, 24) && !checkWay(24, target)) {
                                    inhand = 24;
                                    doub++;
                                }
                            } else if (players_turn == 2) {
                                if (P2.checkthemove(target, 8) && !checkWay(8, target)) {
                                    inhand = 8;
                                    doub++;
                                }
                            } else {
                                System.out.println("[ АЛДАА ] players_turn");
                            }
                            break;
                    }
                    if (doub == 0) {
                        myStatus.addStatus(" - нүүх дүрс олдсонгүй");
                        System.out.println("Нүүх дүрс олдсонгүй");
                    } else if (doub == 1) {
                        inHandG1 = inhand;
                        inHandG2 = -1;
                        targetP = target;
                    } else {
                        inHandG1 = inhand;
                        inHandG2 = inhand2;
                        targetP = target;
                        myStatus.addStatus(" - 2 дүрс буух боломж");
//                        if(players_turn==1){
//                            Object[] options = {"1", "2", "Цуцлах"};
//                            int choice = JOptionPane.showOptionDialog(null,
//                                    "Алийг нь нүүлгэх вэ?\n1. "+returnPointReverse(P1.returnPostion(inhand))+"\n2. "+returnPointReverse(P1.returnPostion(inhand2)),
//                                    "Давхцсан буудал байна",
//                                    JOptionPane.YES_NO_OPTION,
//                                    JOptionPane.INFORMATION_MESSAGE,
//                                    null,
//                                    options,
//                                    options[2]);
//                            if(choice == 0 ){
//                                movePieces(target, inhand);
//                            }
//                            else if(choice == 1) {
//                                movePieces(target, inhand2);
//                            }
//                        }
//                        else if(players_turn==2){
//                            Object[] options = {"1", "2", "Цуцлах"};
//                            int choice = JOptionPane.showOptionDialog(null,
//                                    "Алийг нь нүүлгэх вэ?\n1. "+returnPointReverse(P2.returnPostion(inhand))+"\n2. "+returnPointReverse(P2.returnPostion(inhand2)),
//                                    "Давхцсан буудал байна",
//                                    JOptionPane.YES_NO_OPTION,
//                                    JOptionPane.INFORMATION_MESSAGE,
//                                    null,
//                                    options,
//                                    options[2]);
//                            if(choice == 0 ){
//                                movePieces(target, inhand);
//                            }
//                            else if(choice == 1) {
//                                movePieces(target, inhand2);
//                            }
//                        }
//                        System.out.println("Сонгосон нүдэнд 2 дүрс буух боломжтой");
                    }
                    repaint();
                }
                else {
                    myStatus.addStatus(" - нүүх дүрс олдсонгүй");
                    System.out.println("Нүүх дүрс олдсонгүй");
                }
            }
        }
    }
    public void newPosition(){
        checkTime();
        if(isGameRunning) {

            if(inHandG2 == -1){
                movePieces(targetP, inHandG1);
            }
            else if(inHandG2 != 0){
                if (players_turn == 1) {
                    String s1 = "" + returnPointReverse(P1.returnPostion(inHandG1));
                    String s2 = "" + returnPointReverse(P1.returnPostion(inHandG2));
                    dDialog = new DuplicateDialog(myFrame, s1, s2);
                    dDialog.setVisible(true);
                    dDialog.t.terminate();
                    int choice = dDialog.getChoice();
                    if (choice == 1) {
                        movePieces(targetP, inHandG1);
                    } else if (choice == 2) {
                        movePieces(targetP, inHandG2);
                    }
                } else if (players_turn == 2) {
                    String s1 = "" + returnPointReverse(P2.returnPostion(inHandG1));
                    String s2 = "" + returnPointReverse(P2.returnPostion(inHandG2));
                    dDialog = new DuplicateDialog(myFrame, s1, s2);
                    dDialog.setVisible(true);
                    dDialog.t.terminate();
                    int choice = dDialog.getChoice();
                    if (choice == 1) {
                        movePieces(targetP, inHandG1);
                    } else if (choice == 2) {
                        movePieces(targetP, inHandG2);
                    }
                }
            }

            inHandG1 = -1;
            inHandG2 = -1;
            targetP.setLocation(-1, -1);
//            Point cP = returnPoint(words[0]);
//            Point nP = returnPoint(words[1]);
//            if (cP.x == -1 || cP.y == -1 || nP.x == -1 || nP.y == -1) {
//                System.out.println("[ АЛДАА ] ");
//            } else {
//                int inhand = -1;
//
//                if (!GameOver) {
//
//                    for (int i = 1; i < 33; i++) {
//                        Point pieceP = P2.returnPostion(i);
//                        if (pieceP.x == cP.x && pieceP.y == cP.y) {
//                            inhand = i;
//                            break;
//                        }
//                        pieceP = P1.returnPostion(i);
//                        if (pieceP.x == cP.x && pieceP.y == cP.y) {
//                            inhand = i;
//                            break;
//                        }
//                    }
//
//                    movePieces(nP, inhand);
//
//                }
//            }
        }
    }

    private boolean checkWay(int InHand, Point newP){
        boolean flag = false;
        for (int i = 1; i <= 32; i++) {
            if (InHand != i)
            {
                if(i<17)
                    flag = !P2.Pice_already_there(newP);
                else
                    flag = !P1.Pice_already_there(newP);
                if(players_turn == 1) {
                    if (i < 17) {
                        flag = P1.checktheWay(newP, P2.returnPostion(i), InHand);
                        if(InHand > 24 && InHand < 33) {
                            Point temp = P2.returnPostion(i);
                            if (temp.x == newP.x && temp.y == newP.y) {
                                Point temp2 = P1.returnPostion(InHand);
                                if ((newP.x == temp2.x - 1 && newP.y == temp2.y - 1)) {
                                    flag = false;
                                } else flag = !(newP.x == temp2.x + 1 && newP.y == temp2.y - 1);
                            }
                        }
                    }
                    else {
                        flag = P1.checktheWay(newP, P1.returnPostion(i), InHand);
                        if(InHand > 24 && InHand < 33) {
                            Point temp = P1.returnPostion(i);
                            if (temp.x == newP.x && temp.y == newP.y) {
                                flag = false;
                            }
                        }
                    }
                    if (flag) break;
                }
                if(players_turn == 2) {
                    if (i < 17) {
                        flag = P2.checktheWay(newP, P2.returnPostion(i), InHand);
                        if(InHand > 8 && InHand < 17) {
                            Point temp = P2.returnPostion(i);
                            if (temp.x == newP.x && temp.y == newP.y) {
                                flag = false;
                            }
                        }
                    }
                    else {
                        flag = P2.checktheWay(newP, P1.returnPostion(i), InHand);
                        if(InHand > 8 && InHand < 17) {
                            Point temp = P1.returnPostion(i);
                            if (temp.x == newP.x && temp.y == newP.y) {
                                Point temp2 = P2.returnPostion(InHand);
                                if ((newP.x == temp2.x - 1 && newP.y == temp2.y + 1)) {
                                    flag = false;
                                } else flag = !(newP.x == temp2.x + 1 && newP.y == temp2.y + 1);
                            }
                        }
                    }
                    if (flag) break;
                }
                if (flag) break;
            }
        }
        return flag; // flag == false bol hudulj bolno
    }

    public void movePieces(Point newP, int InHand){
        checkTime();
        boolean can_Send = false;
        Point samePostion;
        ////////////////////////////////////
        ///////// White ////////////////////
        ////////////////////////////////////

        if (players_turn == 1) {

            P1.SetInhand(InHand);

            Point old = P1.returnOldPostion(InHand);
            int x = old.x;
            int y = old.y;
            Point present = P1.returnPostion(InHand);


            ///////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////////


            if (Iam_Server || local) {


                // set the seen of the solider -white
                if (InHand < 33 && InHand > 24) {
                    for (int i = 1; i < 17; i++) {
                        samePostion = P2.returnPostion(i);
                        if (samePostion.x == newP.x && samePostion.y == newP.y) {
                            if (P1.setSeentoSiliders(InHand, samePostion))
                                System.out.println("setSeentoSiliders");
                            break;
                        }
                    }
                }
///////////////////////////////////////////////////////////////////////////////////
                if (!(newP.x == present.x && newP.y == present.y)/*&&!P1.returncheckKing()*/)
                    if (P1.checkthemove(newP, InHand)) // if the move is illegal
                    {


                        boolean flag = checkWay(InHand,newP);
//                        for (int i = 1; i <= 32; i++) {
//                            if (InHand != i)// check if there is peices in the WAY
//                            {
//                                if (i < 17)
//                                    flag = P1.checktheWay(newP, P2.returnPostion(i), InHand);//Means there is somting in the Way so can't move
//                                else {
//                                    flag = P1.checktheWay(newP, P1.returnPostion(i), InHand);
//                                }
//
//                                if (flag == true) break;//Means  there is a Pice in the Way
//                            }
//
//
//                            //
//
//
//                        }

                        if (!flag && P1.Pice_already_there(newP))
                        //(if flag =false this means "The pice able to MOVE as logic""
                        {
                            // So We Check If the New Place Make  a Check To Black King !!!
                            Point myold = new Point();
                            Point o = P1.returnPostion(InHand);
                            myold.x = o.x;
                            myold.y = o.y;
                            Point other = new Point();
                            Point f = new Point();
                            boolean kill = false;
                            int killed = -1;
                            boolean end_move = true;


                            ////***  Start Here to Check the King

                            for (int k = 1; k < 17; k++) {
                                // I have to Check the Place

                                other = P2.returnPostion(k);


                                if (newP.x == other.x && newP.y == other.y) {

                                    if (InHand > 24 && P1.returnsoliderSeen(InHand)) {
                                        kill = true;

                                        f.x = other.x;
                                        f.y = other.y;

                                        P2.Killedpiec(k);

                                        lastKilledP = f;//TODO: +
                                    } else if (InHand <= 24) {
                                        kill = true;

                                        f.x = other.x;
                                        f.y = other.y;

                                        P2.Killedpiec(k);

                                        lastKilledP = f;//TODO: +
                                    } else {
                                        P1.changePostion(myold, InHand);
                                        end_move = false;

                                        break;
                                    }

                                    killed = k;//!!!

                                    break;

                                }

                            }

                            lastKilled = killed;//TODO: +

                            if (end_move)
                                P1.changePostion(newP, InHand);// Here is the mOve ended

                            P1.checkKing(false);
                            if (P1.see_king_Check(P2))
                            // if my king will be in check if i move
                            //so i can't move and i will return back to old postion'
                            {
                                P1.changePostion(myold, InHand);
                                P1.checkKing(true);
                                end_move = false;
                            }
                            if (kill && P1.returncheckKing()) {
                                P2.changePostion(f, killed); // TODO: идсэн боловч ноёныг шагласан бол буцаана
                                lastKilled = -1;
                                lastKilledP.setLocation(-1,-1);
                            }


                            if (!P1.returncheckKing()) {

                                if (P2.see_king_Check(P1))
                                // if my king will be in check if i move
                                //so i can't move and i will return back to old postion'
                                {

                                    P2.checkKing(true);
                                    end_move = false;
                                    if (P2.Check_Mate_GameOver(P1)) {
                                        GameOver();
                                        Box = Integer.toString(InHand) + Integer.toString(newP.x) + Integer.toString(newP.y);
                                        can_Send = true;
                                    } else {
                                        Box = Integer.toString(InHand) + Integer.toString(newP.x) + Integer.toString(newP.y);

                                        CheckStatus();
                                        can_Send = true;

                                    }


                                }


                                if (end_move) {
                                    Box = Integer.toString(InHand) + Integer.toString(newP.x) + Integer.toString(newP.y);


                                    ChangeTurn();
                                    can_Send = true;
                                }


                            }

                        }

                    }

                P1.SetInhand(-1);

                repaint();

                if (can_Send && ((Iam_Server || Iam_Client))) {

                    Send_move();
                    //Send_to.resume();

                    //          Recv_from.resume();

                }

                if (GameOver)
                    JOptionPane.showConfirmDialog(null, "Мад!\n Цагаан яллаа", "Тоглоом дууслаа", JOptionPane.PLAIN_MESSAGE);

            }
        }
        ///////////////////////////////Black/////////////////////////////////////////
        //////////////////////////////Black///////////////////////////////////////////
        //////////////////////////////Black//////////////////////////////////////////////
        //////////////////////////////Black//////////////////////////////////////////////
        else if (InHand != -1)//white
        {

            P2.SetInhand(InHand);
            if (Iam_Client || local) {
                Point old = P2.returnOldPostion(InHand);
                Point present = P2.returnPostion(InHand);


                // set the seen of the solider -black
                // set the seen of the solider -black
                // set the seen of the solider -black
                if (InHand < 17 && InHand > 8) {
                    for (int i = 17; i < 33; i++) {
                        samePostion = P1.returnPostion(i);

                        if (samePostion.x == newP.x && samePostion.y == newP.y) {
                            if (P2.setSeentoSiliders(InHand, samePostion)) {

                                break;
                            }
                        }
                    }
                }


                if (!(newP.x == present.x && newP.y == present.y)/*&&!P2.returncheckKing()*/)
                    if (P2.checkthemove(newP, InHand)) {
                        boolean flag = checkWay(InHand, newP);
//                        for (int i = 1; i <= 32; i++) {
//                            if (InHand != i) {
//                                if (i < 17)
//                                    flag = P2.checktheWay(newP, P2.returnPostion(i), InHand);
//                                else
//                                    flag = P2.checktheWay(newP, P1.returnPostion(i), InHand);
//
//                                if (flag) break;
//                            }
//                        }


                        for (int i = 1; i <= 16 && !flag; i++) {
                            if (InHand != i) {
                                if (!flag) {
                                    samePostion = P2.returnPostion(i);
                                    if (newP.x == samePostion.x && newP.y == samePostion.y) {
                                        flag = true;
                                        break;

                                    }
                                }

                            }

                            if (flag) break;
                        }

                        if (!flag) {
                            Point kingPostion2 = P2.returnPostion(8);
                            Point myold = new Point();
                            Point o = P2.returnPostion(InHand);
                            myold.x = o.x;
                            myold.y = o.y;
                            Point other = new Point();
                            Point f = new Point();
                            boolean kill = false;
                            boolean end_move = true;
                            int killed = -1;


                            for (int k = 17; k < 33; k++) {
                                other = P1.returnPostion(k);
                                if (newP.x == other.x && newP.y == other.y) {

                                    if (InHand > 8 && P2.returnsoliderSeen(InHand)) {
                                        kill = true;

                                        other = P1.returnPostion(k);

                                        f.x = other.x;
                                        f.y = other.y;

                                        P1.Killedpiec(k);

                                        lastKilledP = f;//TODO: +
                                    } else if (InHand <= 8) {
                                        kill = true;

                                        other = P1.returnPostion(k);

                                        f.x = other.x;
                                        f.y = other.y;
                                        P1.Killedpiec(k);

                                        lastKilledP = f;//TODO: +
                                    } else {
                                        end_move = false;
                                        P2.changePostion(myold, InHand);
                                    }


                                    killed = k;
                                    break;

                                }

                            }
                            //boolean kin2=true;
                            lastKilled = killed;//TODO: +
                            if (end_move)
                                P2.changePostion(newP, InHand);

                            P2.checkKing(false);
                            if (P2.see_king_Check(P1))
                            // if my king will be in check if i move
                            //so i can't move and i will return back to old postion'
                            {
                                P2.changePostion(myold, InHand);
                                P2.checkKing(true);

                                end_move = false;


                            }
                            if (kill && P2.returncheckKing()) {
                                P1.changePostion(f, killed); // TODO: идсэн боловч ноёныг шагласан бол буцаана
                                lastKilled = -1;
                                lastKilledP.setLocation(-1,-1);
                            }

                            if (P2.returncheckKing()) {
                                P2.changePostion(myold, InHand);
                            }


                            if (!P2.returncheckKing()) {
                                if (P1.see_king_Check(P2))
                                // if my king will be in check if i move
                                //so i can't move and i will return back to old postion'
                                {

                                    P1.checkKing(true);
                                    end_move = false;


                                    if (P1.Check_Mate_GameOver(P2)) {
                                        Box = Integer.toString(InHand) + Integer.toString(newP.x) + Integer.toString(newP.y);
                                        GameOver();

                                        can_Send = true;
                                    } else {
                                        Box = Integer.toString(InHand) + Integer.toString(newP.x) + Integer.toString(newP.y);
                                        CheckStatus();
                                        can_Send = true;
                                    }
                                }


                                if (end_move) {
                                    Box = Integer.toString(InHand) + Integer.toString(newP.x) + Integer.toString(newP.y);
                                    ChangeTurn();
                                    can_Send = true;
                                }

                            }

                        }
                    }
                P2.SetInhand(-1);

                repaint();

                if (can_Send && ((Iam_Server || Iam_Client))) {

                    //Send_to.resume();
                    Send_move();
                    ///     Recv_from.resume();


                }
                if (GameOver)
                    JOptionPane.showConfirmDialog(null, "Мад!\n Хар яллаа", "Тоглоом дууслаа", JOptionPane.DEFAULT_OPTION);

            }
        }
    }

    private boolean BoardgetPostion(int x, int y)


    {
        if (!GameOver && Game_started) {
            if ((Iam_Server && players_turn == 1) || (local) || (Iam_Client && players_turn == 2)) {

                int newX = x / Divide;
                int newY = y / Divide;
                newX++;
                newY++;

                if (newX > 8 || newY > 8 || newX < 1 || newY < 1) {
                    repaint();
                    return false;

                }

                if (players_turn == 1 && P1.GetInhand() == -1)//Player 1
                {
                    for (int i = 17; i <= 32; i++) {
                        Point p = P1.returnPostion(i);
                        if (p.x == newX && p.y == newY) {
                            P1.SetInhand(i);
                            whenHandleAndPice(x, y);
                            return true;
                        }
                    }
                } else if (players_turn == 2 && P2.GetInhand() == -1)//Player 2
                {
                    for (int i = 1; i <= 16; i++) {
                        Point p = P2.returnPostion(i);
                        if (p.x == newX && p.y == newY) {
                            P2.SetInhand(i);
                            whenHandleAndPice(x, y);
                            return true;
                        }
                    }
                } else if (players_turn == 1 && P1.GetInhand() != -1)//Player 1
                {
                    whenHandleAndPice(x, y);
                    return true;
                } else if (players_turn == 2 && P2.GetInhand() != -1)//Player 2
                {
                    whenHandleAndPice(x, y);
                    return true;
                }
                P1.SetInhand(-1);
                move = 0;

                return false;

            }
        }
        return false;
    }

    private boolean whenHandleAndPice(int x, int y) {

        if (players_turn == 1 && P1.GetInhand() != -1) {
            P1.changePixel(x, y, P1.GetInhand());
            return true;
        } else if (players_turn == 2 && P2.GetInhand() != -1) {
            P2.changePixel(x, y, P2.GetInhand());
            return true;
        }
        return false;
    }

    private int rowToX(int r) {
        int myx;
        int iHeight = this.getHeight();
        myx = (r * iHeight / 8) - Divide;
        return myx;
    }

    private int colToY(int c) {
        int myy;
        int iWidth = getWidth();
        myy = (c * iWidth / 8) - Divide;
        return myy;
    }


    private class MousewhenMove implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();
            if (controll_game_type(x, y)) {

                repaint();
            }

        }

        public void mouseMoved(MouseEvent e) {

        }


    }

    private boolean controll_game_type(int x, int y) {

        if (Iam_Server || Iam_Client && Game_started) {
            if (Iam_Server && players_turn == 1) {
                return BoardgetPostion(x, y);
            } else if (Iam_Client && players_turn == 2) {
                return BoardgetPostion(x, y);
            } else
                return false;
        } else {
            return BoardgetPostion(x, y);
        }


        // return false;
    }


    private void ChangeTurn() {
        turn++;
        if (players_turn == 1) {
            players_turn = 2;
            myTool.setturn(2);
            lastInhand = P1.GetInhand();
            myTool.add_to_History("Цагаан : " + P1.Tell_me_About_last_move());
            myStatus.changeStatus(" Хар нүүх ээлж");
            myStatus.addStatus(" - Нүүдэл " + turn);
            myTool.change_to_Timer2();
        } else if (players_turn == 2) {
            players_turn = 1;
            myTool.setturn(1);
            lastInhand = P2.GetInhand();
            myTool.add_to_History("Хар : " + P2.Tell_me_About_last_move());
            myTool.change_to_Timer1();
            myStatus.changeStatus(" Цагаан нүүх ээлж");
            myStatus.addStatus(" - Нүүдэл " + turn);
        }
        undos = 0;
    }

    private void NetChangeTurn() {
        if (players_turn == 2) {

            lastInhand = P1.GetInhand();
            myTool.add_to_History("Цагаан : " + P1.Tell_me_About_last_move());
            myStatus.changeStatus(" Хар нүүх ээлж");
            myStatus.addStatus(" - Нүүдэл " + turn);
            myTool.change_to_Timer2();
        } else if (players_turn == 1) {

            lastInhand = P2.GetInhand();
            myTool.add_to_History("Хар : " + P2.Tell_me_About_last_move());
            myTool.change_to_Timer1();
            myStatus.changeStatus(" Цагаан нүүх ээлж");
            myStatus.addStatus(" - Нүүдэл " + turn);
        }
        undos = 0;
    }

    private void NeTGameCheckStatus() {
        if (players_turn == 1) {

            myTool.add_to_History("Цагаан : " + P1.Tell_me_About_last_move());
            myTool.change_to_Timer2();
        } else if (players_turn == 2) {

            myTool.add_to_History("Хар : " + P2.Tell_me_About_last_move());
            myTool.change_to_Timer1();
        }
        myStatus.changeStatus(" Шаг! ");
        undos = 0;
    }


    private void CheckStatus() {
        if (players_turn == 1) {

            players_turn = 2;
            lastInhand = P1.GetInhand();
            myTool.add_to_History("Цагаан : " + P1.Tell_me_About_last_move());
            myTool.change_to_Timer2();
        } else if (players_turn == 2) {

            players_turn = 1;
            lastInhand = P2.GetInhand();
            myTool.add_to_History("Хар : " + P2.Tell_me_About_last_move());
            myTool.change_to_Timer1();
        }
        myStatus.changeStatus(" Шаг! ");
        undos = 0;
    }


    private void GameOver() {

        myStatus.changeStatus(" Check Mate! ");


        GameOver = true;
        isGameRunning = false; //TODO: Nemsen
    }


    private void Send_move() {
        out.print(Box);
        out.print("\r\n");
        out.flush();

    }

    private Point returnPoint(String str){
        Point pnt = new Point();
        str = str.toLowerCase();
        char c = str.charAt(0);
        int a = -1;
        int b = -1;
        switch (c){
            case 'a': a = 1;break;
            case 'b': a = 2;break;
            case 'c': a = 3;break;
            case 'd': a = 4;break;
            case 'e': a = 5;break;
            case 'f': a = 6;break;
            case 'g': a = 7;break;
            case 'h': a = 8;break;
        }
        c = str.charAt(1);
        switch (c){
            case '1': b = 8;break;
            case '2': b = 7;break;
            case '3': b = 6;break;
            case '4': b = 5;break;
            case '5': b = 4;break;
            case '6': b = 3;break;
            case '7': b = 2;break;
            case '8': b = 1;break;
        }
        pnt.x = a;
        pnt.y = b;
        return pnt;
    }
    private String returnPointReverse(Point pnt){
        String point = "";
        switch (pnt.x){
            case 1: point = "A";break;
            case 2: point = "B";break;
            case 3: point = "C";break;
            case 4: point = "D";break;
            case 5: point = "E";break;
            case 6: point = "F";break;
            case 7: point = "G";break;
            case 8: point = "H";break;
        }
        switch (pnt.y){
            case 1: point += "8";break;
            case 2: point += "7";break;
            case 3: point += "6";break;
            case 4: point += "5";break;
            case 5: point += "4";break;
            case 6: point += "3";break;
            case 7: point += "2";break;
            case 8: point += "1";break;
        }
        return point;
    }
    private Point returnPointCryllic(String c, String n){
        Point pnt = new Point();
        c = c.toLowerCase();
        n = n.toLowerCase();
        int a = -1;
        int b = -1;
        switch (c){
            case "эй": a = 1;break;
            case "бий": a = 2;break;
            case "сий": a = 3;break;
            case "дий": a = 4;break;
            case "ий": a = 5;break;
            case "ээф": a = 6;break;
            case "жий": a = 7;break;
            case "эйч": a = 8;break;
        }
        switch (n){
            case "нэг": b = 8;break;
            case "хоёр": b = 7;break;
            case "гурав": b = 6;break;
            case "дөрөв": b = 5;break;
            case "тав": b = 4;break;
            case "зургаа": b = 3;break;
            case "долоо": b = 2;break;
            case "найм": b = 1;break;
        }
        pnt.x = a;
        pnt.y = b;
        return pnt;
    }

//    private Point returnPointCryllic(int a, String str){
//        Point pnt = new Point();
//        str = str.toLowerCase();
//        int b = -1;
//        switch (str){
//            case "нэг": b = 8;break;
//            case "хоёр": b = 7;break;
//            case "гурав": b = 6;break;
//            case "дөрөв": b = 5;break;
//            case "тав": b = 4;break;
//            case "зургаа": b = 3;break;
//            case "долоо": b = 2;break;
//            case "найм": b = 1;break;
//        }
//        pnt.x = a;
//        pnt.y = b;
//        return pnt;
//    }
    public void undoMove(){
        if(isGameRunning && turn >= 1 && undos == 0) {
            if (players_turn == 1) {
                if(P2.returnPostion(lastInhand).x ==  lastKilledP.x && P2.returnPostion(lastInhand).y ==  lastKilledP.y){
                    if(lastKilled > 16 && lastKilled < 33){
                        P1.setLocation(lastKilledP,lastKilled);
                        lastKilled = -1;
                    }
                }
                P2.undoMove(lastInhand);
                repaint();
                myTool.add_to_History(" ^ Буцсан ^ ");
                players_turn = 2;
                turn--;
                myStatus.changeStatus(" Хар нүүх ээлж");
                myStatus.addStatus(" - Нүүдэл " + turn);
                repaint();
                undos++;
            } else if (players_turn == 2) {
                if(P1.returnPostion(lastInhand).x ==  lastKilledP.x && P1.returnPostion(lastInhand).y ==  lastKilledP.y){
                    if(lastKilled > 0 && lastKilled < 17){
                        P2.setLocation(lastKilledP,lastKilled);
                        lastKilled = -1;
                    }
                }
                P1.undoMove(lastInhand);
                repaint();
                myTool.add_to_History(" ^ Буцсан ^ ");
                players_turn = 1;
                turn--;
                myStatus.changeStatus(" Цагаан нүүх ээлж");
                myStatus.addStatus(" - Нүүдэл " + turn);
                repaint();
                undos++;
            }
            System.out.println("Буцсан");
        }
        else {
            System.out.println("Буцаах боломжгүй");
        }
    }
    public void checkDuplicate(String s){
//        String[] words = s.split(" ");
//        boolean check = false;
//        if(words[0].equals("нэг")){
//            movePieces(pointDup, inHandG1);
//            check = true;
//        }
//        else if(words[0].equals("хоёр")){
//            movePieces(pointDup, inHandG2);
//            check = true;
//        }
//        if(check) {
//            Window[] windows = Window.getWindows();
//            for (Window window : windows) {
//                if (window instanceof JDialog) {
//                    JDialog dialog = (JDialog) window;
//                    if (dialog.getContentPane().getComponentCount() == 1
//                            && dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
//                        dialog.dispose();
//                    }
//                }
//            }
//        }
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public int getUndos() {
        return undos;
    }

    public int getTurn() {
        return turn;
    }

    public void checkTime(){
        if(myTool.getTime1() == 0 || myTool.getTime2() == 0){
            isGameRunning = false;
            GameOver = true;
            if(myTool.getTime1() == 0){
                JOptionPane.showConfirmDialog(null, "Цаг дууссан!\n Хар яллаа", "Тоглоом дууслаа", JOptionPane.DEFAULT_OPTION);
            }
            else if(myTool.getTime2() == 0){
                JOptionPane.showConfirmDialog(null, "Цаг дууссан!\n Цагаан яллаа", "Тоглоом дууслаа", JOptionPane.DEFAULT_OPTION);
            }
        }
    }

    class Recv_Thread extends Thread {
        public synchronized void run() {


            while (true) {

                try {

                    Box = in.readLine();

                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("ERROR");
                }


                if (Box != null) {


                    int newInHand = Integer.parseInt(Box);
                    int newX = Integer.parseInt(Box);
                    int newY = Integer.parseInt(Box);


                    /***
                     * Operation to Get
                     *1- The # of Pice
                     *2- The Location X
                     *3- The Location Y
                     *
                     **/

                    newInHand /= 100;
                    newX -= (newInHand * 100);
                    newX /= 10;
                    newY -= (newInHand * 100) + (newX * 10);


                    if (players_turn == 1) {


                        P1.SetInhand(newInHand);
                        players_turn = 2;

                        P1.changePostion(new Point(newX, newY), newInHand);

                        P2.Killedpiec(P1.Get_Pice_already_there_from_enemy(new Point(newX, newY), P2));
                        P2.checkKing(false);

                        if (P2.see_king_Check(P1))
                        // if my king will be in check if i move
                        //so i can't move and i will return back to old postion'
                        {

                            P2.checkKing(true);


                            if (P2.Check_Mate_GameOver(P1)) {
                                System.out.println("mate");
                                GameOver();

                            } else {

                                NeTGameCheckStatus();

                            }
                        } else NetChangeTurn();


                        P1.SetInhand(-1);


                    } else {
                        P2.SetInhand(newInHand);
                        P2.changePostion(new Point(newX, newY), newInHand);

                        P1.Killedpiec(P2.Get_Pice_already_there_from_enemy(new Point(newX, newY), P1));
                        players_turn = 1;

                        P1.checkKing(false);
                        if (P1.see_king_Check(P2))
                        // if my king will be in check if i move
                        //so i can't move and i will return back to old postion'
                        {

                            P1.checkKing(true);


                            if (P1.Check_Mate_GameOver(P2)) {

                                System.out.println("mate");
                                GameOver();

                            } else {

                                NeTGameCheckStatus();

                            }
                        } else NetChangeTurn();

                        P2.SetInhand(-1);
                    }
                    //   CheckStatus();

                    repaint();
                }


            }
        }
    }

}
