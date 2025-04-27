package org.example;

import javax.swing.*;
import java.sql.*;

public class NewChatController {
    Connection connection;

    //Sohbet olusturma metodu
    public boolean createChat(String chatName, String chatPassword, int userId) {
        if (!isPasswordStrong(chatPassword)) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and include uppercase,lowercase,number and special characters!");
            return false;
        }

        String hashedChatPassword = PasswordHasher.hashPassword(chatPassword);

        String query = "insert into chat_groups (group_name,password,created_by) values (?,?,?)";

        try {
            connection = DBConnection.getConnection();
            //Benim tablomdaki sql in tablomda otomatik olusturdugu satirlara ozgunluk katan id degerini almak icin
            // Preparet Statment imi generated keys secenegiyle olusturdum
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, chatName);
            preparedStatement.setString(2, hashedChatPassword);
            preparedStatement.setInt(3, userId);

            int control = preparedStatement.executeUpdate();

            if (control > 0) {
                //Eger istedigim satir tabloya eklendiyse sütünlari aliyoruz
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int chatId = resultSet.getInt(1);
                    if (addUsersToGroup(chatId)) {
                        JOptionPane.showMessageDialog(null, "Chat Created");
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "A group with this name already exists!");
            } else {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && // Minimum 8 karakter
                password.matches(".*[A-Z].*") && // Büyük harf
                password.matches(".*[a-z].*") && // Küçük harf
                password.matches(".*\\d.*");  // Sayı
    }

    //Kullanicilarin hangi gruplara katildigini user_groups tablomda tutuyorum bu fonksiyonda ise
    // kullanicinin id si ile olusturdugu grubun id sini tabloma gonderiyorum.
    public boolean addUsersToGroup(int chatId) {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
