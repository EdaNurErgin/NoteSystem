import javax.swing.JOptionPane;

public class UserActivity implements NoteObserver { //Concreete Observer
    private String username;

    public UserActivity(String username) {
        this.username = username;
    }

    @Override
    public void update(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
