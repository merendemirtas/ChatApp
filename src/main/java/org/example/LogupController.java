package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogupController {

    //Hesabi olusturdugum ve veritabanina kaydettigim sinif
    public boolean registerUser(String username, String password) {
        //Sifrenin güclü olmasini zorunlu kilan metod
        if (!isPasswordStrong(password)) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and include uppercase,lowercase,number and special characters!");
            return false;
        }

        //Sifreyi gondererek hash halini çekiyorum ve tanimladigim degiskene atiyorum
        String hashedPassword = PasswordHasher.hashPassword(password);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean registrationSuccess = false;

        try {
            connection = DBConnection.getConnection();
            //Hesap bilgileriyle users tablosunda olusturmak istedigim satir icin sql e komut gonderiyorum
            String query = "insert into users (username,passwordUser) values (?,?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            int control = preparedStatement.executeUpdate();

            //eger olay basariyla gerceklestiyse control degeri olması gereken 0 degerinden fazla olur.Bende control degiskenimi kontrol ediyorum
            if (control > 0) {
                //Olusturulan hesabin kullanici adini ve sifresini sessionManager da tuturorum.
                sessionManager.startSession(username);
                int userId = getUserIdByUsername(username);
                sessionManager.setCurrentUserId(userId);
                registrationSuccess = true;
            }
        } catch (SQLException e) {
            //Hesabin kullanici ismi zaten kullanılıyorsa bana donecegini bildigim 1062 hata koduna ozel hata mesaji olusturuyorum.
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "User already exists");
            } else {
                JOptionPane.showMessageDialog(null, "An error occured:" + e.getMessage());
                e.printStackTrace();
            }


        }
        //Islem bittikten sonra olusturdugum baglantilari kapatiyorum
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return registrationSuccess;
    }

    //Kullanici adindan kullanicinin id sini donduren metod
    public int getUserIdByUsername(String username) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String query = "SELECT id FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new SQLException("");
            }
        }
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && // Minimum 8 karakter
                password.matches(".*[A-Z].*") && // Büyük harf
                password.matches(".*[a-z].*") && // Küçük harf
                password.matches(".*\\d.*");  // Sayı
    }
}
