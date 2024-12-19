import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends Subject {
    private JFrame frame;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private int userId;

    public LoginGUI() {
        frame = new JFrame("Giriş Yap");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel oluşturuluyor
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Kullanıcı adı
        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameField = new JTextField();

        // E-posta
        JLabel emailLabel = new JLabel("E-posta:");
        emailField = new JTextField();

        // Şifre
        JLabel passwordLabel = new JLabel("Şifre:");
        passwordField = new JPasswordField();

        // Giriş yap butonu
        JButton loginButton = new JButton("Giriş Yap");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Giriş işlemi
                UserManager userManager = UserManager.getInstance();
                userId = userManager.login(username, email, password);

                if (getUserId() != -1) {
                    //JOptionPane.showMessageDialog(frame, "Giriş başarılı!");
                    // Observer olarak UserActivity'i ekleyelim
                    UserActivity userActivity = new UserActivity(username);
                    attach(userActivity);  // Observer olarak ekleme
                    userActivity.update("Giriş başarılı!");
                    new NoteAppGUI(userId); // NoteAppGUI ekranına yönlendir
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(frame, "Giriş bilgileri hatalı!");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Boş bir hücre için
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public int getUserId() {
        return userId;
    }
}
