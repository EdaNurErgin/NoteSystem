import javax.swing.*;
import java.awt.*;

public class BackgroundContext {
    private BackgroundState currentState;
    public BackgroundContext() {
        this.currentState = new DefaultState(); // Varsayılan durumu başlat
    }
    public void setState(BackgroundState state) {
        this.currentState = state;
    }

    public void applyState(JComponent component) {
        currentState.changeBackground(component);
    }
}
