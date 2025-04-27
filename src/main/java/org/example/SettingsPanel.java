package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends BasePanel {
    private JButton b1;
    private JPasswordField pf1;
    private JTextField tf1;
    private JButton b3;
    private JButton b4;
    private JButton b2;
    private JPanel panel;
    private JLabel l4;
    private JLabel l5;
    private JLabel l1;
    private JLabel l2;
    private JPanel l22;
    private JLabel username;
    private JLabel l3;
    private JLabel l31;

    SettingsPanel(JFrame frame) {
        super(frame);

        if (sessionManager.isLoggedIn()) {
            String username1 = sessionManager.getCurrentUser();
            username.setText(username1);
            int chatCount = MainPanel.getChatCounter();
            l31.setText(String.valueOf(chatCount));
        }

        add(panel);

        l4.setVisible(false);
        l5.setVisible(false);
        tf1.setVisible(false);
        pf1.setVisible(false);
        b2.setVisible(false);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l4.setVisible(true);
                l5.setVisible(true);
                tf1.setVisible(true);
                pf1.setVisible(true);
                b2.setVisible(true);
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsController settingsController = new SettingsController();

                if (settingsController.changePassword(tf1.getText(), pf1.getText())) {
                    JOptionPane.showMessageDialog(null, "Changed Password");
                    tf1.setText("");
                    pf1.setText("");
                }
            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1300, 900);
                frame.setContentPane(new MainPanel(frame));
            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have successfully logged out!");
                sessionManager.endSession();
                frame.setSize(330, 440);
                switchToPanel(new LoginPanel(frame));
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}