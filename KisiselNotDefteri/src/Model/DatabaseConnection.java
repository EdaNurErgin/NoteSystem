package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/notsistemi";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; //5761

    // Singleton constructor, connection open only once
    private DatabaseConnection() {
        try {
            // Bağlantı zaten varsa, kullanılmasına izin ver
            if (connection == null || connection.isClosed()) {
                this.connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
                System.out.println("Veritabanı bağlantısı sağlandı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Veritabanı bağlantısı kurulamadı.", e);
        }
    }

    // Singleton getInstance method to get single instance of DatabaseConnection
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Get connection method, ensures that connection is open
    public Connection getConnection() throws SQLException {
        // Eğer bağlantı kapalıysa, yeni bağlantı açılır
        if (connection == null || connection.isClosed()) {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Yeni veritabanı bağlantısı sağlandı.");
        }
        return connection;
    }

    // Optional: Connection close method (Only when needed)
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
