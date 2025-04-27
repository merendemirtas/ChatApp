package org.example;

import java.sql.*;

public class LoginController {

    //Connection sinifindaki veritabanima yapilan baglantiyi temsil eden nesne olusturuyorum
    //Static olmasinin nedeni tek bir baglanti benim butun kullanicilarima baglanmami saglıyor zaten
    private static Connection connection;

    //Kullanici adi ve sifreyi veritabanindan kontrol etme metodu
    public boolean LoginUser(String username, String password) {
        //Girilen sifreyi hashliyoruz.
        String hashedPassword = PasswordHasher.hashPassword(password);

        //Veri tabanina gonderilecek sql sorgusunu tanimliyorum
        String query = "SELECT * FROM users WHERE username = ? and passwordUser = ?";

        try {

            //Veri tabani baglantisi
            connection = DBConnection.getConnection();

            //Guvenli bir sekilde sql sorgularimizi gondermemizi saglayan preparedstatement sinifinin nesnesini olusturuyorum
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            //ilk soru isareti yerine kullanici adini digerine sifre gondererek veritabani sorgusunu olusturuyorum
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            //executeQuery metoduyla birlikte sorgumu uyguluyorum ve cevap alıyorum bu cevap resultSet nesnemde tutuluyor
            //resultSet nesnesi benim cevabimi tutuyor ve ben bunda next() metoduyla gezinebiliyorum
            ResultSet resultSet = preparedStatement.executeQuery();

            //Cevapta geziniyorum eger kayit bulunursa cevap true olarak donuyor
            if (resultSet.next()) {

                int id = resultSet.getInt("id");
                sessionManager.startSession(resultSet.getString("username"));
                sessionManager.setCurrentUserId(id);
                return true;
            }
        } catch (SQLException e) {
            //Bize hatanin ne oldugunu kolsolda gosteriyor
            System.out.println(e.getMessage());
            //Bize hatanin hangi sinifin hangi satirinda gerceklestigini dondurur
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {

                    //Veri tabani islemi sonlandiktan sonra baglantiyi kapatiyorum
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Fonksiyon bu satira kadar geldiyse demekki kullanici bulunamamistir bu yuzden false donduruyorum
        return false;
    }
}
