package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Bu sinif sql olusturdugum database e ulasmak icin actıgım bir sinif.Bu sinifta JDBC kullanarak veritabani baglantisi olusturuyorum.
public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/App";
    private static final String user = "root";
    private static final String password = "Eren1905";

    //Baglantiyi tutacak sitatic bir Connection nesnesi
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        try {
            //JDBC sürücüsünü yükleyen komut
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Veritabanina baglanti kuran connection nesnesi olusturuyorum
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("JDBC driver bulunamadi!");
        }
        return connection;
    }
}
