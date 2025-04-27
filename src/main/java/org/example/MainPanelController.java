package org.example;

import java.sql.*;
import java.util.ArrayList;

public class MainPanelController {
    Connection connection;

    //Kullanicinin katildigi veya olusturdugu gruplarin isimlerini bir arraylist olarak döndürür.
    public ArrayList<String> getUserGroups(int userId) {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String> groups = new ArrayList<>();
        String query = "select group_name from chat_groups " +
                "join user_groups on chat_groups.id = user_groups.group_id where user_groups.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                groups.add(resultSet.getString("group_name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return groups;
    }

    //Mesaj gonderen metod
    public boolean sendMessage(int userId, int groupId, String message) {
        String query = "insert into messages (user_id,group_id,message_text,is_starred,is_read,send_date) values (?,?,?,?,?,?)";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            stmt.setString(3, message);
            stmt.setBoolean(4, false);
            stmt.setBoolean(5, false);
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

            int rowAffected = stmt.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //Ismi bilinen grubun id sini döndüren metod
    public int getGroupId(String groupName) {
        String query = "select id from chat_groups where group_name = ?";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, groupName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                return id;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    //Secilen grubun id sini gondererek icindeki mesajları String Builder olarak döndüren meetod
    public StringBuilder loadMessages(int selectedGroupId) {
        StringBuilder formattedMessage = new StringBuilder();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "select m.id,m.message_text,u.username from messages m " +
                    " join users u on m.user_id = u.id where m.group_id = ? " +
                    " order by m.send_date";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, selectedGroupId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int messageId = resultSet.getInt("id");
                    String message = resultSet.getString("message_text");
                    String username = resultSet.getString("username");
                    formattedMessage.append(formattedMessage(messageId, username, message));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return formattedMessage;
    }

    //Mesajin id,username,message ozelliklerini birlestirerek donduren metod
    public String formattedMessage(int messageId, String username, String message) {
        return messageId + ":" + username + ":" + message + "\n";
    }

    //Mesajlari yildizlayan metod
    public void toggleStarred(int messageId) {
        String query = "update messages set is_starred = not is_starred where id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, messageId);

            int rowAffected = statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

