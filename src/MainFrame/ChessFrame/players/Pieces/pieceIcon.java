/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */
package MainFrame.ChessFrame.players.Pieces;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.applet.*;

import javax.imageio.ImageIO;

public class pieceIcon {
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private Image image;


    public pieceIcon(String NameIcon) //throws IOException
    {

        image = kit.getImage(NameIcon);
    }

    public Image returnPieceIcon() {
        return image;
    }
    
    
    /*
     *   *       Please Visit us at www.codemiles.com     *
     *  This Program was Developed by www.codemiles.com forums Team
     *  *           Please Don't Remove This Comment       *
     */
}
