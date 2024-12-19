import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class DefaultState implements BackgroundState {

    @Override
    public void changeBackground(JComponent component) {
        if (component instanceof JPanel) {
            component.setBackground(Color.WHITE); // JPanel için arkaplan rengi değiştir
            component.setForeground(Color.BLACK);
        } else if (component instanceof JTextArea) {
            component.setBackground(Color.WHITE); // JTextArea için arkaplan rengi değiştir
            component.setForeground(Color.BLACK);
        } else if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            JViewport viewport = scrollPane.getViewport();
            Component view = viewport.getView();
            if (view instanceof JTextArea) {
                view.setBackground(Color.WHITE); // JScrollPane içerik JTextArea ise, arkaplan rengini değiştir
                component.setForeground(Color.BLACK);
            }
        }else if (component instanceof JList) {
            component.setBackground(Color.WHITE); // JList (notesList) için arkaplan rengi değiştir
            component.setForeground(Color.BLACK);
        }
    }
}
