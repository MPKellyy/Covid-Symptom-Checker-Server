import javax.swing.*;
import java.awt.*;


public class MessagePanel extends JPanel {

    public MessagePanel(String message) {
        this.setLayout(new BorderLayout());
        JTextArea messageView = new JTextArea(message);
        messageView.setEditable(false);
        this.add(messageView, BorderLayout.NORTH);
        this.setVisible(true);
    }

}