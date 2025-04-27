package org.example;

import javax.swing.*;
import java.sql.*;

public class JoinChatController {
    public static Connection connection;

    //Katilinmak istenen sohbetin sifresini kontrol etme metodu
    public boolean checkChatPassword(String chatName, String chatPassword) {
        String hashedPassword = PasswordHasher.hashPassword(chatPassword);

        try {
            connection = DBConnection.getConnection();

            String query = "select * from chat_groups where group_name = ? and password = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, chatName);
            statement.setString(2, hashedPassword);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int result = resultSet.getInt("id");
                if (addUsersToGroup(result)) {
                    JOptionPane.showMessageDialog(null, "Joined " + chatName + " successfully");
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //New Chat sinifinda da olan bir grubu olusturan veya gruba giren kişileri o grubun üyesi olarak tuttugum user_groups tablosuna ekliyorum
    public boolean addUsersToGroup(int chatId) {
        int userId = sessionManager.getCurrentUserId();

        String checkQuery = "select count(*) from user_groups where user_id = ? and group_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, chatId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "You are already in this group");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        String query = "insert into user_groups ( group_id, user_id) values (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, chatId);
            statement.setInt(2, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
