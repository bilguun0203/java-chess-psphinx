/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessFrame.players.Pieces;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;

public class Solider {

    /**
     * Creates a new instance of Solider
     */
    private int X, Y;
    private Point pixelPoint = new Point();
    private int pixelX, pixelY;
    private boolean havelife = true;
    private boolean movedbefore = false;
    private pieceIcon PieceIcon;
    private Point p = new Point();
    private Point old = new Point();
    private boolean myseen = false;

    public Solider(String NameIcon, int startX, int startY) {

        PieceIcon = new pieceIcon(NameIcon);


        X = startX;
        Y = startY;
        p.x = X;
        p.y = Y;
    }

    public Image returnPieceImage() {
        return PieceIcon.returnPieceIcon();
    }

    public boolean returnLife() {
        return havelife;
    }

    public int returnX() {
        return X;
    }

    public void setPoint(Point newPoint) {
        old.x = p.x;
        old.y = p.y;
        X = p.x = newPoint.x;
        Y = p.y = newPoint.y;
        p.x = X;
        p.y = Y;
        movedbefore = true;
        myseen = false;
    }

    public void undoMove(Point newPoint, String color) {
        X = p.x = newPoint.x;
        Y = p.y = newPoint.y;
        p.x = X;
        p.y = Y;
        if(color.equals("white")){
            if(Y == 7){
                movedbefore = false;
            }
        }
        else if(color.equals("black")){
            if(Y == 2){
                movedbefore = false;
            }
        }
    }
    public void undoMove(String color){
        X = p.x = old.x;
        Y = p.y = old.y;
        p.x = X;
        p.y = Y;
        if(color.equals("white")){
            if(Y == 7){
                movedbefore = false;
            }
        }
        else if(color.equals("black")){
            if(Y == 2){
                movedbefore = false;
            }
        }
    }

    public Point returnOld() {
        return old;
    }

    public void setX(int newX) {
        X = newX;
        p.x = X;
    }

    public void setY(int newY) {
        Y = newY;
        p.y = Y;
    }

    public void setPixels(int newpixelX, int newpixelY) {
        pixelPoint.x = newpixelX;
        pixelPoint.y = newpixelY;
    }

    public int getPixelX() {
        return pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public Point getpixelPoint() {
        return pixelPoint;
    }

    public int returnY() {
        return Y;
    }

    public Point returnPostion() {
        return (Point) p.clone();
    }

    public boolean Inthispostion(int x, int y) {
        if (p.x == x && p.y == y)
            return true;
        return false;
    }

    public boolean Canmove(int x, int y, String typeColor) {

        if ((typeColor.equals("black"))) {
            if ((((y - 1 == Y) && (x == (X)))) /*&&!Check_Solider_Sees(x,y)*/) {

                return true;

            } else if ((((y - 2 == Y) && (x == (X)))) && !movedbefore) {

                return true;
            } else if ((y - 1 == Y && x + 1 == (X) || (y - 1 == Y && x - 1 == (X))) && myseen) {
                return true;
            } else return false;
        } else if (typeColor == "white") {
            if (((y + 1 == Y) && (x == (X))) /*&&!Check_Solider_Sees(x,y)*/) {
                return true;
            } else if ((((y + 2 == Y) && (x == (X)))) && !movedbefore) {
                return true;
            } else if ((y + 1 == Y && x + 1 == (X) || (y + 1 == Y && x - 1 == (X))) && myseen) {
                return true;
            } else
                return false;
        }
        return false;


    }

    public boolean PieceInMYway(int x, int y, Point othersPostion, String typeColor) {
        if (Y - y == 2 || Y - y == -2) {
            if ((typeColor.equals("black"))) {

                if ((((y - 1 == othersPostion.y) && (x == (othersPostion.x)))) && !movedbefore) {
                    return true;
                } else return false;
            } else if (typeColor.equals("white")) {

                if (((y + 1 == othersPostion.y) && (x == (othersPostion.x)) && !movedbefore)) {

                    return true;

                } else
                    return false;
            }
        }

        return false;
    }

    public void toOld(Point Old) {

        p.x = Old.x;
        p.y = Old.y;

    }

    public void setMYseen(boolean newBoolean) {
        myseen = newBoolean;
    }

    public boolean returnMyseen() {
        return myseen;
    }

    public boolean setSeenbychecking(Point newP, String Color) {
        myseen = false;
        if ((Color.equals("black"))) {
            if ((newP.y - 1 == Y && newP.x + 1 == (X) || (newP.y - 1 == Y && newP.x - 1 == (X)))) {

                myseen = true;
                return true;
            } else return false;
        } else if (Color.equals("white")) {
            if ((newP.y + 1 == Y && newP.x + 1 == (X) || (newP.y + 1 == Y && newP.x - 1 == (X)))) {
                myseen = true;

                return true;
            } else return false;
        }
        return false;
    }

    public Point GeneratePossible_Moves() {
        return new Point();
    }

    public String Tell_me() {
        String t = returnPointReverse(p);
        return "Хүү (" + t + ")";
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

}/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
