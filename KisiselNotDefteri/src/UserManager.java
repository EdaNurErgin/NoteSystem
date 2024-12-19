import java.sql.SQLException;

public class UserManager {
    // Singleton Tasarim Deseni
    private static UserManager instance;
    private UserDatabase userDatabase;

    // Private constructor (dışarıdan erişim engellenir)
    private UserManager() {
        this.userDatabase = new UserDatabase();
    }

    // Singleton örneğine küresel erişim noktası
    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    // Kullanıcı kaydını gerçekleştirir
    public void register(String username,String email, String password) {
        // Yeni bir kullanıcı oluşturuyoruz
        User newUser = new User(username, email, password);

        try {
            // Kullanıcıyı veritabanına kaydediyoruz
            userDatabase.saveUser(newUser);
        } catch (SQLException e) {
            System.out.println("Kayıt işlemi sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    // Kullanıcı girişini gerçekleştirir
    public int login(String username, String email, String password) {
        System.out.println(username + " için giriş işlemi yapılıyor...");

        // SQL sorgusu: Veritabanında kullanıcıyı doğrula
        boolean isUserValid = userDatabase.validateUser(username, email, password);

        if (isUserValid) {
            System.out.println("Giriş başarılı: " + username);
            int owner_id = userDatabase.getUserId(username, email, password);
            return owner_id;
        } else {
            System.out.println("Giriş bilgileri hatalı. Lütfen tekrar deneyin.");
            return -1;
        }

    }

}

