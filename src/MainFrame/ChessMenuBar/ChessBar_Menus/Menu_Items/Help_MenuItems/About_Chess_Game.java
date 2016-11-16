/*
 *   *       Please Visit us at www.codemiles.com     *
 *  This Program was Developed by www.codemiles.com forums Team
 *  *           Please Don't Remove This Comment       *
 */

package MainFrame.ChessMenuBar.ChessBar_Menus.Menu_Items.Help_MenuItems;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author bilguun
 */
public class About_Chess_Game extends JMenuItem {

    /**
     * Creates a new instance of About_Chess_Game
     */
    public About_Chess_Game() {
        setText("Энэ тоглоомын тухай");

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String aboutGame = "" +
                        "Дуу хойлоогоор удирддаг шатар\n" +
                        "Командууд:\n" +
                        "\tэхэл/шатар эхэл: Тоглоомыг локал хэлбэрээр эхлүүлнэ\n" +
                        "\t<дүрс> <байрлал>: Нүүлгэх дүрсдээ байрлалыг нь өгөх\n" +
                        "\tнүү/түлх: Байрлалыг нь сонгосон дүрсийг хөдөлгөнө\n" +
                        "\tбуц: Сүүлд хийсэн үйлдлийг буцаана\n" +
                        "\tдуусга: Тоглоомоос гарна\n" +
                        "\tнэг/хоёр/цуцал: Олон дүрс буух боломжтой байрлалд аль дүрсийг нүүлгэхээ сонгох\n";
                JOptionPane.showMessageDialog(null, aboutGame);
            }
        });
    }

}
