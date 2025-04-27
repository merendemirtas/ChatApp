package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StarredMessagesPanel extends BasePanel {
    private JPanel panel;
    private JPanel starredMessagesPanel;
    private JButton b;
    private StarredMessagesController controller;

    StarredMessagesPanel(JFrame frame) {
        super(frame);

        controller = new StarredMessagesController();
        ArrayList<String> starredMessages = controller.getStarredMessages(sessionManager.getCurrentUserId());
        displayStarredMessages(starredMessages);

        add(panel);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1300, 900);
                switchToPanel(new MainPanel(frame));
            }
        });
    }

    //Yildizlanan mesajlari array list olarak alip onu mesajin id , gonderen kisi ve mesaj olarak parcalara ayirip
    // username:text olarak yeni bir label olusturarak panele ekleyen fonksiyon
    public void displayStarredMessages(ArrayList<String> starredMessages) {
        JPanel starredPanel = new JPanel();
        starredPanel.setLayout(new BoxLayout(starredPanel, BoxLayout.Y_AXIS));

        for (String message : starredMessages) {
            String[] parts = message.split(":");

            if (parts.length >= 3) {
                String username = parts[1].trim();
                String text = parts[2].trim();

                JPanel messageRowPanel = new JPanel();
                messageRowPanel.setLayout(new BoxLayout(messageRowPanel, BoxLayout.Y_AXIS));
                messageRowPanel.setPreferredSize(new Dimension(250, 50));
                messageRowPanel.setMaximumSize(new Dimension(250, 50));

                JLabel messageLabel = new JLabel(username + " : " + text);
                messageRowPanel.add(messageLabel);

                starredPanel.add(messageRowPanel);
            }
        }
        starredMessagesPanel.add(starredPanel);
        add(starredMessagesPanel);
    }

    public JPanel getPanel() {
        return panel;
    }
}