import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<NoteObserver> observers = new ArrayList<>();

    // Gözlemci ekleme
    public void attach(NoteObserver observer) {
        observers.add(observer);
    }

    // Gözlemci çıkarma
    public void detach(NoteObserver observer) {
        observers.remove(observer);
    }

    // Gözlemcilere bildirim gönderme
    public void notifyObservers(String message) {
        for (NoteObserver observer : observers) {
            observer.update(message);

        }
    }
}
