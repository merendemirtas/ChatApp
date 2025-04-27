package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewChatPanel extends BasePanel {
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1;
    private JPasswordField pf1;
    private JButton b2;
    private JButton b1;
    private NewChatController controller = new NewChatController();

    NewChatPanel(JFrame frame) {
        super(frame);
        add(panel);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chatName = tf1.getText();
                String password = String.valueOf(pf1.getPassword());

                if (controller.createChat(chatName, password, sessionManager.getCurrentUserId())) {
                    frame.setSize(1300, 900);
                    switchToPanel(new MainPanel(frame));
                }
                tf1.setText("");
                pf1.setText("");
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1300, 900);
                switchToPanel(new MainPanel(frame));
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
