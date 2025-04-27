package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogupPanel extends BasePanel {
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1;
    private JPasswordField pf1;
    private JButton b2;
    private JButton b1;
    private LogupController controller;

    LogupPanel(JFrame frame) {
        super(frame);
        add(panel);

        controller = new LogupController();

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tf1.getText();
                String password = String.valueOf(pf1.getPassword());
                if (controller.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "User registered successfully");
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
                frame.setSize(330, 440);
                switchToPanel(new LoginPanel(frame));
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
