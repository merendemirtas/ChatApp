package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StarredMessagesController {

    //Yildizli mesajlari ceken ve arraylist olarak donduren fonksiyon
    public ArrayList<String> getStarredMessages(int userId) {
        ArrayList<String> starredMessages = new ArrayList<>();
        String query ="SELECT m.*, u.* "+
                " FROM messages m "+
        " JOIN users u ON m.user_id = u.id "+
        " WHERE m.is_starred = true AND m.user_id = ?";

        try(Connection connection=DBConnection.getConnection()){
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,userId);
            ResultSet rs=ps.executeQuery();

            while (rs.next()){
                int messageId = rs.getInt("id");
                String messageText = rs.getString("message_text");
                String username = rs.getString("username");

                starredMessages.add(messageId+":"+messageText+":"+username);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return starredMessages;
    }
}
