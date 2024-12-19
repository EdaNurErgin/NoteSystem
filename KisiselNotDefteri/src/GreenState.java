import javax.swing.*;
import java.awt.*;

class GreenState implements BackgroundState {
    @Override
    public void changeBackground(JComponent component) {
        if (component instanceof JPanel) {
            component.setBackground(Color.GREEN); // JPanel için arkaplan rengi değiştir
            component.setForeground(Color.BLACK);
        } else if (component instanceof JTextArea) {
            component.setBackground(Color.GREEN); // JTextArea için arkaplan rengi değiştir
            component.setForeground(Color.BLACK);
        } else if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            JViewport viewport = scrollPane.getViewport();
            Component view = viewport.getView();
            if (view instanceof JTextArea) {
                view.setBackground(Color.GREEN); // JScrollPane içerik JTextArea ise, arkaplan rengini değiştir
                component.setForeground(Color.BLACK);
            }
        }else if (component instanceof JList) {
            component.setBackground(Color.GREEN); // JList (notesList) için arkaplan rengi değiştir
            component.setForeground(Color.BLACK);
        }
    }
}
