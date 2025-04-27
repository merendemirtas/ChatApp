package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainPanel extends BasePanel {
    private JPanel panel;
    private JButton b11;
    private JButton b12;
    private JButton b13;
    private JButton b14;
    private JButton send;
    private JTextField textField;
    private JPanel messagePanel;
    private JPanel chatPanel;
    private JLabel groupName;
    private JPanel messagesPanel;
    private MainPanelController controller;
    private int groupId;
    String groupLabel;
    private JScrollPane scrollPane;
    private Timer messageUpdateTimer;
    private static int chatCounter = 0;

    MainPanel(JFrame frame) {
        super(frame);
        displayGroupsButton(sessionManager.getCurrentUserId());
        add(panel);

        b11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330, 440);
                switchToPanel(new NewChatPanel(frame));
            }
        });

        b12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330, 440);
                switchToPanel(new JoinChatPanel(frame));
            }
        });

        b13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330, 440);
                switchToPanel(new StarredMessagesPanel(frame));
            }
        });

        b14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = new JButton("Click Me");
                Color buttonColor = button.getBackground();
                System.out.println("Button Color: " + buttonColor);

                frame.setSize(330, 440);
                switchToPanel(new SettingsPanel(frame));
            }
        });

        //Mesajlari gonderme butonu
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId = 0;
                String message;
                controller = new MainPanelController();

                //Uygulamayi kim kullaniyorsa onun id sini aliyorum
                if (sessionManager.isLoggedIn()) {
                    userId = sessionManager.getCurrentUserId();
                }

                message = textField.getText();

                if (textField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Message can not be empty");
                } else {
                    //MainPanelController sinifimdaki mesaji database e gonderen fonksiyona gereken degerleri atiyorum
                    boolean success = controller.sendMessage(userId, groupId, message);

                    if (success) {
                        textField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Message not sent");
                    }
                }
            }
        });

        // Timer ı baslatiyorum
        startMessageUpdateTimer();
    }

    //Saniyede bir mesajlari temizleyip geri yukleyerek guncelleyen fonksiyon
    private void startMessageUpdateTimer() {
        //Saniyede bir calisacak timer
        messageUpdateTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (groupId != 0) {
                    StringBuilder formattedMessage = controller.loadMessages(groupId);
                    messagesPanel.removeAll();
                    displayMessages(formattedMessage);
                }
            }
        });
        messageUpdateTimer.start();
    }

    //Katilinan veya olusturulan gruplari main panelimin ortasinda gosterecek olan panelimi hazirlayan fonksiyon
    public void displayGroupsButton(int userId) {
        controller = new MainPanelController();
        ArrayList<String> groups = controller.getUserGroups(userId);
        chatPanel.setLayout(new GridLayout(groups.size(), 1));

        chatCounter = 0;

        for (String group : groups) {
            //Her grup butonu olustururken sohbet sayacimi arttırarak kullanicinin kac sohbete katildigini buluyorum
            // bunu da setting panelinde gostericem
            chatCounter++;
            JButton groupButton = new JButton(group);
            groupButton.setBounds(50, 50, 150, 80);
            chatPanel.add(groupButton);

            //Butun butonlarda olan tiklanince mesajlari yukleyen metod
            groupButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    groupId = controller.getGroupId(group);
                    groupLabel = group;
                    groupName.setText(groupLabel);
                    //tiklayarak id sini gonderdigim grubun icindeki mesajlari StringBuilder turunde aliyorum
                    StringBuilder formattedMessage = controller.loadMessages(groupId);

                    //Mesaj panelini temizleyip mesajları yukluyorum
                    messagesPanel.removeAll();
                    displayMessages(formattedMessage);
                }
            });
        }
    }

    /*Mesajlari ekranda gosteren metod.Once mesaj satirlarini messages dizisinde tutar ve bu satirlarin hepsine panel olusturur
    sonra bu satirlari ':' ile ayırarak mesaji ve gonderenin kullanici adini ve mesasj id sini bulur bu id i panelin içinde tutar .
    Her mesaj icin label olusturur ve Kullanici adi ve mesaji uygun sekilde birleştirerek label lari ekranda gosterir.
     */

    public void displayMessages(StringBuilder formattedMessages) {
        String[] messages = formattedMessages.toString().split("\n");

        for (String message : messages) {
            String[] parts = message.split(":");

            messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));

            if (parts.length < 3 || parts[0].isEmpty()) {
                parts = new String[]{"0", "", ""};
            }
            int messageId = Integer.parseInt(parts[0].trim());
            String username = parts[1].trim();
            String text = parts[2].trim();

            JPanel messageRowPanel = new JPanel();
            messageRowPanel.setLayout(new FlowLayout(username.equals(sessionManager.getCurrentUser()) ? FlowLayout.RIGHT : FlowLayout.LEFT));
            messageRowPanel.setPreferredSize(new Dimension(messagesPanel.getWidth(), 40));
            messageRowPanel.setMaximumSize(new Dimension(messagesPanel.getWidth(), 40));

            messageRowPanel.putClientProperty("messageId", messageId);

            String[] words = text.split(" ");
            JLabel wordLabel = null;
            for (String word : words) {
                if (!username.equals(sessionManager.getCurrentUser())) {
                    if (wordLabel == null && !word.isEmpty()) {
                        wordLabel = new JLabel(username + " : " + word);
                    } else {
                        wordLabel = new JLabel(word);
                    }
                } else {
                    wordLabel = new JLabel(word);
                }
                messageRowPanel.add(wordLabel);
            }

            //Hangi mesajin ustundeki panele sag tiklanirsa o mesaji yildizlar
            messageRowPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        Integer messageId = (Integer) messageRowPanel.getClientProperty("messageId");
                        if (messageId != null) {
                            //Mesaji yildizlayan metoda her mesaj icin farkli olusturulan ve icinde mesaj id sini tutan paneller sayesinde mesaj idsi gonderir
                            controller.toggleStarred(messageId);
                        }
                    }
                }
            });
            messagesPanel.add(messageRowPanel);
        }
        messagesPanel.revalidate();
        messagesPanel.repaint();
    }

    public static int getChatCounter() {
        return chatCounter;
    }
}
