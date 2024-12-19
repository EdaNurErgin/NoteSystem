import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class DarkState implements BackgroundState {
    @Override
     public void changeBackground(JComponent component) {
            if (component instanceof JPanel) {
                component.setBackground(Color.DARK_GRAY); // JPanel için arkaplan rengi değiştir
                component.setForeground(Color.WHITE);
            } else if (component instanceof JTextArea) {
                component.setBackground(Color.DARK_GRAY); // JTextArea için arkaplan rengi değiştir
                component.setForeground(Color.WHITE);
            } else if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                JViewport viewport = scrollPane.getViewport();
                Component view = viewport.getView();
                if (view instanceof JTextArea) {
                    view.setBackground(Color.DARK_GRAY); // JScrollPane içerik JTextArea ise, arkaplan rengini değiştir
                    component.setForeground(Color.WHITE);
                }
            }else if (component instanceof JList) {
                component.setBackground(Color.DARK_GRAY); // JList (notesList) için arkaplan rengi değiştir
                component.setForeground(Color.WHITE);
            }

    }
}
