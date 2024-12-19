public class User {
    private String username;
    private String password;
    private String email;

    // Constructor
    public User(String username, String email,String password) {
        this.username = username;
        this.password = password;
        this.setEmail(email);
    }

    // Getter ve Setter metodları eklenebilir
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
