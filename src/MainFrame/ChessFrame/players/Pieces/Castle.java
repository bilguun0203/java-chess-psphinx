

package MainFrame.ChessFrame.players.Pieces;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;

public class Castle {

    private int X, Y;
    private int pixelX, pixelY;
    private Point pixelPoint = new Point();
    private boolean havelife = true;
    private pieceIcon PieceIcon;
    private Point p = new Point();
    private Point old = new Point();

    public Castle(String NameIcon, int startX, int startY) {

        PieceIcon = new pieceIcon(NameIcon);

        X = startX;
        Y = startY;
        p.x = X;
        p.y = Y;
    }

    public Image returnPieceImage() {
        return PieceIcon.returnPieceIcon();
    }

    public int returnX() {
        X = p.x;
        return X;
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
        Y = p.y;
        return Y;
    }

    public void toOld(Point Old) {

        p.x = Old.x;
        p.y = Old.y;

    }

    public void setPoint(Point newPoint) {
        old.x = p.x;
        old.y = p.y;
        X = p.x = newPoint.x;
        Y = p.y = newPoint.y;
    }

    public void undoMove(Point newPoint) {
        X = newPoint.x;
        Y = newPoint.y;
        p.x = X;
        p.y = Y;
    }
    public void undoMove(){
        X = old.x;
        Y = old.y;
        p.x = X;
        p.y = Y;
    }

    public void setX(int newX) {
        X = newX;
        p.x = newX;
    }

    public void setY(int newY) {
        Y = newY;
        p.y = newY;
    }

    public Point returnOld() {
        return old;
    }

    public Point returnPostion() {


        return (Point) p.clone();
    }

    public boolean returnLife() {
        return havelife;
    }

    public boolean Inthispostion(int x, int y) {
        if (p.x == x && p.y == y)
            return true;
        return false;
    }

    public boolean Canmove(int x, int y) {
        if (((y == Y) && (x > (X) || (x < (X))))) {
            return true;
        } else if ((((y > Y) || (y < Y)) && (x == (X)))) {
            return true;
        } else {

            return false;
        }


    }

    public boolean PieceInMYway(int x, int y, Point othersPostion) {
        int j = y;
        int i = x;
        if (((y == Y) && (x > (X) || (x < (X))))) {

            if ((X < i))

                while ((i != X + 1)) {
                    i--;
                    if (((othersPostion.y) == j) && ((othersPostion.x == i)))//there Same Color piece
                    {
                        return true;
                    }
                }

            else if ((X > i)) {
                while ((i != X - 1)) {
                    i++;
                    if (((othersPostion.y) == j) && ((othersPostion.x == i))) {
                        return true;
                    }
                }
            }
        } else if ((((y > Y) || (y < Y)) && (x == (X)))) {
            if ((Y < j)) {
                while ((j != Y + 1)) {
                    j--;
                    if (((othersPostion.y) == j) && ((othersPostion.x == i))) {
                        return true;
                    }
                }
            } else if ((Y > j)) {
                while ((j != Y - 1)) {
                    j++;

                    if (((othersPostion.y) == j) && ((othersPostion.x == i))) {
                        return true;
                    }
                }

            }
        }
        return false;


    }

    public boolean checkKing(int x, int y, Point othersPostion) {
        int j = y;
        int i = x;
        if (((y == Y) && (x > (X) || (x < (X))))) {

            if ((X < i))

                while ((i != X)) {
                    i--;
                    if (((othersPostion.y) == j) && ((othersPostion.x == i)))//there Same Color piece
                    {
                        return true;
                    }
                }

            else if ((X > i)) {
                while ((i != X)) {
                    i++;
                    if (((othersPostion.y) == j) && ((othersPostion.x == i))) {
                        return true;
                    }
                }
            }
        } else if ((((y > Y) || (y < Y)) && (x == (X)))) {
            if ((Y < j)) {
                while ((j != Y)) {
                    j--;
                    if (((othersPostion.y) == j) && ((othersPostion.x == i))) {
                        return true;
                    }
                }
            } else if ((Y > j)) {
                while ((j != Y)) {
                    j++;

                    if (((othersPostion.y) == j) && ((othersPostion.x == i))) {
                        return true;
                    }
                }

            }
        }
        return false;


    }


    public Point GeneratePossible_Moves() {
        return new Point();
    }

    public String Tell_me() {
        String t = returnPointReverse(p);
        return "Тэрэг (" + t + ")";
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

}