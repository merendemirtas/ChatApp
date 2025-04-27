package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends BasePanel {
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1;
    private JButton b1;
    private JButton b2;
    private JPasswordField pf1;

    private LoginController controller;

    LoginPanel(JFrame frame) {
        super(frame);
        add(panel);

        //Login Controller sinifini baslatiyoruz
        controller = new LoginController();

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tf1.getText();
                //Sifre rakam icerse de string turune cevrilir valueOf komutuyla
                String password = String.valueOf(pf1.getPassword());

                if (controller.LoginUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    frame.setSize(1300, 900);
                    switchToPanel(new MainPanel(frame));
                } else {
                    JOptionPane.showMessageDialog(frame, "You entered invalid username or password");
                }
                tf1.setText("");
                pf1.setText("");
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel(new LogupPanel(frame));
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
