import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI {
    public static void main(String[] args) {
        // Ana pencere oluşturuluyor
        JFrame frame = new JFrame("Not Uygulaması");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        // Ana panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        // Kayıt Ol butonu
        JButton registerButton = new JButton("Kayıt Ol");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterGUI();
                frame.setVisible(false); // Ana ekranı gizle
            }
        });

        // Giriş Yap butonu
        JButton loginButton = new JButton("Giriş Yap");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginGUI();
                if (frame != null) {
                    frame.dispose(); // Ana ekranı kapat
                }
            }
        });

        panel.add(registerButton);
        panel.add(loginButton);
        frame.add(panel);
        frame.setVisible(true);
    }
}
