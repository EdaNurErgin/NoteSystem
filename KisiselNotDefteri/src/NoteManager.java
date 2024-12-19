import Model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteManager extends Subject{
    private static NoteManager instance;
    private DatabaseConnection dbConnection;

    private NoteManager() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    public static NoteManager getInstance() {
        if (instance == null) {
            synchronized (NoteManager.class) {
                if (instance == null) {
                    instance = new NoteManager();
                }
            }
        }
        return instance;
    }

    private List<NoteObserver> observers = new ArrayList<>();

    public void addObserver(NoteObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(NoteObserver observer) {
        observers.remove(observer);
    }
    public void addNote(Note note) {
        String checkSql = "SELECT COUNT(*) FROM notes WHERE title = ? AND owner_id = ?";
        String insertSql = "INSERT INTO notes (title, content, owner_id) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            // Aynı başlık var mı kontrol et
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, note.getTitle());
                checkStmt.setInt(2, note.getOwner_id());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Bu başlığa sahip bir not zaten mevcut. Yeni not eklenmedi.");
                        return; // Aynı başlık bulundu, ekleme yapılmıyor
                    }
                }
            }
            // Yeni not ekle
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, note.getTitle());
                insertStmt.setString(2, note.getContent());
                insertStmt.setInt(3, note.getOwner_id());
                int affectedRows = insertStmt.executeUpdate();
                System.out.println("Not başarıyla eklendi.");
                notifyObservers("Not başarıyla eklendi: " + note); //gozlemciye meesaj gonderiyoruz
                if(affectedRows>0){
                    try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);  // Yeni ID'yi alıyoruz
                            note.setNote_id(generatedId);  // `note` nesnesine yeni ID'yi atıyoruz
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Not eklenirken bir hata oluştu.");
        }
    }


    public void addNoteWithImage(ImageNote note, String imagePath) {
        String insertSql = "INSERT INTO notes (title, content, owner_id, imagePath) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            // Yeni resim notunu ekle
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, note.getTitle());
                insertStmt.setString(2, note.getContent());
                insertStmt.setInt(3, note.getOwner_id());
                insertStmt.setString(4, imagePath);
                insertStmt.executeUpdate();
                System.out.println("Resim notu başarıyla eklendi.");
                notifyObservers("Resim notu başarıyla eklendi: " + note);//gozlemciye meesaj gonderiyoruz
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Resim notu eklenirken bir hata oluştu.");
        }
    }
    public void deleteNote(int noteId, int ownerId) {
        String sql = "DELETE FROM notes WHERE note_id = ? AND owner_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, noteId);
            stmt.setInt(2, ownerId);
            stmt.executeUpdate();
            System.out.println("Not başarıyla silindi.");
            notifyObservers("Not silindi: ID = " + noteId); //gozlemciye meesaj gonderiyoruz
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateNote(int noteId, int ownerId, String newTitle, String newContent) throws SQLException {
        String sql = "UPDATE notes SET title = ?, content = ? WHERE note_id = ? AND owner_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newTitle);  // Yeni başlık
            stmt.setString(2, newContent);  // Yeni içerik
            stmt.setInt(3, noteId);  // Güncellenecek notun ID'si
            stmt.setInt(4, ownerId);  // Notun sahibinin ID'si

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Not bulunamadı veya güncellenemedi. Not ID: " + noteId + " ve Kullanıcı ID: " + ownerId + " ile eşleşen bir kayıt bulunamadı.");
            } else {
                System.out.println("Not başarıyla güncellendi.");
                notifyObservers("Not güncellendi: ID = " + noteId);
            }
        } catch (SQLException e) {
            throw new SQLException("Veritabanı hatası: " + e.getMessage(), e);
        }

    }
    public void updateNoteWithImage(int noteId, int ownerId, String newTitle, String newContent, String newImagePath) throws SQLException {
        String sql = "UPDATE notes SET title = ?, content = ?, imagePath = ? WHERE note_id = ? AND owner_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newTitle);  // Yeni başlık
            stmt.setString(2, newContent);  // Yeni içerik
            stmt.setString(3, newImagePath);  // Yeni resim yolu
            stmt.setInt(4, noteId);  // Güncellenecek notun ID'si
            stmt.setInt(5, ownerId);  // Notun sahibinin ID'si

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Not bulunamadı veya güncellenemedi.");
            } else {
                System.out.println("Resim notu başarıyla güncellendi.");
                notifyObservers("Resim notu güncellendi: ID = " + noteId);
            }
        } catch (SQLException e) {
            throw new SQLException("Veritabanı hatası: " + e.getMessage(), e);
        }
    }



    // Kullanıcıya ait notları listele
    public List<Note> getUserNotes(int ownerId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE owner_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Burada sorgudaki "?" sembolüne ownerId değerini atıyoruz
            stmt.setInt(1, ownerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String imagePath = rs.getString("imagePath"); // Resimli notlar için

                    if (imagePath != null && !imagePath.isEmpty()) {
                        ImageNote imageNote = new ImageNote(title, content, imagePath, ownerId);
                        imageNote.setNote_id(rs.getInt("note_id"));
                        notes.add(imageNote);

                    } else {
                        TextNote textNote = new TextNote(title, content, ownerId);
                        textNote.setNote_id(rs.getInt("note_id"));
                        notes.add(textNote);                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }

}
