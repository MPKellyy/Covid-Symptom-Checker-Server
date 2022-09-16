import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerUI {
    private JTextPane messagePane;
    private JButton offButton;
    public JPanel serverPane;

    public ServerUI() {
        offButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
