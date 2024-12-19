import Model.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {
    private DatabaseConnection dbConnection;

    public UserDatabase() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    // Kullanıcıyı veritabanına kaydetme metodu
    public void saveUser(User user) throws SQLException {
        // Kullanıcıyı kontrol etmek için sorgu
        String checkSql = "SELECT id FROM users WHERE email = ? OR name = ?";
        // Kullanıcıyı eklemek için sorgu
        String insertSql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            // Kullanıcı kontrolü için email ve name parametrelerini ayarlıyoruz
            checkStmt.setString(1, user.getEmail());
            checkStmt.setString(2, user.getUsername());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Kullanıcı zaten mevcut
                    JOptionPane.showMessageDialog(null,
                            "Bu kullanıcı zaten kayıtlı: " + user.getUsername(),
                            "Bilgilendirme",
                            JOptionPane.INFORMATION_MESSAGE);
                    return; // Yeni kayıt eklemeden çıkış yapıyoruz
                }
            }

            // Kullanıcı mevcut değilse, ekleme işlemini yap
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, user.getUsername());
                insertStmt.setString(2, user.getEmail());
                insertStmt.setString(3, user.getPassword());

                // Kullanıcıyı veritabanına ekle
                insertStmt.executeUpdate();
                JOptionPane.showMessageDialog(null,
                        "Kullanıcı başarıyla kaydedildi: " + user.getUsername(),
                        "Başarılı",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    //kullanıcı dogrulama islemi
    public boolean validateUser(String username, String email, String password) {
        String sql = "SELECT * FROM users WHERE name = ? AND email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);

            try (ResultSet rs = stmt.executeQuery()) {
                // Eğer sonuç dönerse, kullanıcı doğrulanmış demektir
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Kullanıcı doğrulanırken bir hata oluştu: " + e.getMessage(),
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false; // Doğrulama başarısız
    }

    public int getUserId(String username, String email, String password) {
        String sql = "SELECT id FROM users WHERE name = ? AND email = ? AND password = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1; // Kullanıcı bulunamadı
                }
            }
        } catch (SQLException e) {
            // Hata mesajını ayrıntılı şekilde yazdıralım
            e.printStackTrace();
            System.out.println("Hata: " + e.getMessage());
            return -1;
        }
    }
}
