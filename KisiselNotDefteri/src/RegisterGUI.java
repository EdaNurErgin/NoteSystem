import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterGUI {
    private JFrame frame;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegisterGUI() {
        frame = new JFrame("Kayıt Ol");
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

        // Kayıt ol butonu
        JButton registerButton = new JButton("Kayıt Ol");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Kullanıcıyı kaydetme işlemi
                UserManager userManager = UserManager.getInstance();
                userManager.register(username, email, password);

                // Giriş ekranına yönlendir
                new LoginGUI();
                frame.setVisible(false);
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
