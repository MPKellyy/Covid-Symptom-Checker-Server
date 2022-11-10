package app;
import javax.swing.*;
import java.awt.*;

/**
 * Basic panel for server-side that displays binary string of client responses.
 */
public class MessagePanel extends JPanel {

    public MessagePanel(String message) {
        this.setLayout(new BorderLayout());
        JTextArea messageView = new JTextArea(message);
        messageView.setEditable(false);
        this.add(messageView, BorderLayout.NORTH);
        this.setVisible(true);
    }

}