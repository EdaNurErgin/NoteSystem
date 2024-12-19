import javax.swing.*;
import java.awt.*;

public class PinkState implements BackgroundState{
    @Override
    public void changeBackground(JComponent component) {
        if (component instanceof JPanel) {
            component.setBackground(Color.PINK); // JPanel için arkaplan rengi değiştir
        } else if (component instanceof JTextArea) {
            component.setBackground(Color.PINK); // JTextArea için arkaplan rengi değiştir
        } else if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            JViewport viewport = scrollPane.getViewport();
            Component view = viewport.getView();
            if (view instanceof JTextArea) {
                view.setBackground(Color.PINK); // JScrollPane içerik JTextArea ise, arkaplan rengini değiştir
            }
        }else if (component instanceof JList) {
            component.setBackground(Color.PINK); // JList (notesList) için arkaplan rengi değiştir
        }
    }
}
