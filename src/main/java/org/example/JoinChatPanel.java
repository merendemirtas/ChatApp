package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinChatPanel extends BasePanel {
    private JPanel panel;
    private JButton b2;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1;
    private JPasswordField pf1;
    private JButton b1;
    private JoinChatController joinChatController = new JoinChatController();

    JoinChatPanel(JFrame frame) {
        super(frame);
        add(panel);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chatName = tf1.getText();
                String password = pf1.getText();

                if (joinChatController.checkChatPassword(chatName, password)) {
                    frame.setSize(1400, 900);
                    switchToPanel(new MainPanel(frame));
                } else {
                    JOptionPane.showMessageDialog(null, "The group does not exist");
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
