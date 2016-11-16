
package MainFrame.ChessFrame;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * @author bilguun
 */
public class StatusPanel extends JPanel {

    /**
     * Creates a new instance of StatusPanel
     */
    public StatusPanel() {
        setSize(600, 25);
        setLocation(3, 635);
        setLayout(null);

        statusLabel.setSize(590, 20);
        statusLabel.setLocation(5, 5);
        statusLabel.setText(" Шинэ тоглоом эхлүүлнэ үү ");
        status = " Шинэ тоглоом эхлүүлнэ үү ";
        statusLabel.setBackground(Color.lightGray);
        statusLabel.setFont(new Font("Aril", Font.BOLD, 11));
        statusLabel.setForeground(Color.RED.brighter());
        statusLabel.setBorder(LabelBorder);
        add(statusLabel);


    }

    public void start_Again() {

        statusLabel.setText("  Тоглоом эхэллээ ");
        status = "  Тоглоом эхэллээ ";
    }

    public void changeStatus(Object str) {
        status = (String) str;
        statusLabel.setText((String) str);
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    public void addStatus(String str){
        statusLabel.setText(status + str);
    }

    private String status = "";
    private JLabel statusLabel = new JLabel();
    private LineBorder LabelBorder = new LineBorder(Color.BLACK.brighter(), 2);
}