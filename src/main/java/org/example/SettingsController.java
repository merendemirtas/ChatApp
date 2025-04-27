package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsController {
    LoginController login = new LoginController();
    LogupController logup = new LogupController();

    //Eski sifre dogruysa sifre yeni sifreyi de hash leyerek database deki sifreyi gunceller
    public boolean changePassword(String oldPassword, String newPassword) {
        if (oldPassword != null && !oldPassword.isEmpty()) {
            if (!checkOldPassword(oldPassword)) {
                JOptionPane.showMessageDialog(null, "Old password is incorrect");
                return false;
            }
        }

        if (!isPasswordStrong(newPassword)) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and include uppercase,lowercase and number!");
            return false;
        }

        String username = sessionManager.getCurrentUser();
        String hashedNewPassword = PasswordHasher.hashPassword(newPassword);

        String query = "update users set passwordUser = ? where username = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hashedNewPassword);
            preparedStatement.setString(2, username);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "User already exists");
            } else {
                JOptionPane.showMessageDialog(null, "An error occured:" + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }

    //Sifre degisikligi fonksiyonu dogrulamak icin aldigi sifreyi hash şekile cevirip database e sorgu atar
    public boolean checkOldPassword(String oldPassword) {
        String username = sessionManager.getCurrentUser();
        if (!login.LoginUser(username, oldPassword)) {
            return false;
        }
        if (logup.registerUser(username, oldPassword)) {
            return false;
        }

        String hashedOldPassword = PasswordHasher.hashPassword(oldPassword);

        String query = "select * from users where username = ? and passwordUser = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedOldPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occured:" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //Yeni sifre guclu mu kontrolu
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && // Minimum 8 karakter
                password.matches(".*[A-Z].*") && // Büyük harf
                password.matches(".*[a-z].*") && // Küçük harf
                password.matches(".*\\d.*");  // Sayı
    }
}

