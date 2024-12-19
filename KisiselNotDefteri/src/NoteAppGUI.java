import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteAppGUI extends JFrame implements NoteObserver{
    private BackgroundContext context;
    private JList<String> notesList;
    private DefaultListModel<String> listModel;
    private JTextArea rightContentArea;
    private JButton addNewNoteButton, editNoteButton, deleteNoteButton, encryptNoteButton, exitButton, unlockNoteButton,refreshButton,colorPickerButton;

    private List<Note> fetchedNotes;
    private NoteManager noteManager;
    private int userID;
    private String guiName;

    public NoteAppGUI(String guiName) {
        this.guiName = guiName;
    }
    public NoteAppGUI(int userId) {
        userID = userId;
        context = new BackgroundContext(); // Context nesnesini başlat
        noteManager = NoteManager.getInstance();
        noteManager.addObserver(this); // Observer olarak ekle
        fetchedNotes = new ArrayList<>(); // Liste başlatıldı
        initUI();
        loadNotes(userId);
    }

    @Override
    public void update(String message) {
        System.out.println(guiName + " received update: " + message);
        JOptionPane.showMessageDialog(this, message, "Bildirim", JOptionPane.INFORMATION_MESSAGE);
        refreshNotes();  // Notları güncelle
    }

    private void initUI() {
        setTitle("Kişisel Not Defteri");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Sol Panel - Kullanıcının mevcut notlarını listele
        JPanel leftPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        notesList = new JList<>(listModel);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notesList.addListSelectionListener(e -> showNoteContent());
        JScrollPane scrollPane = new JScrollPane(notesList);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Sağ Panel - Seçilen not içeriğini göster
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightContentArea = new JTextArea();
        rightContentArea.setEditable(false);
        JScrollPane rightScrollPane = new JScrollPane(rightContentArea);
        rightPanel.add(rightScrollPane, BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Alt Panel - Butonlar
        JPanel bottomPanel = new JPanel();

        colorPickerButton = new JButton("Renk Seç");
        refreshButton = new JButton("Yenile");
        addNewNoteButton = new JButton("Yeni Not Ekle");
        editNoteButton = new JButton("Not Düzenle");
        deleteNoteButton = new JButton("Not Sil");
        encryptNoteButton = new JButton("Kilitle");
        unlockNoteButton = new JButton("Kilit Aç");

        exitButton = new JButton("Çıkış Yap");

        addNewNoteButton.addActionListener(e -> openNewNoteScreen());
        editNoteButton.addActionListener(e -> onEditNote());
        deleteNoteButton.addActionListener(e -> onDeleteNote());
        encryptNoteButton.addActionListener(e -> onEncryptNote());
        unlockNoteButton.addActionListener(e -> onUnlockNote());
        exitButton.addActionListener(e -> onExitApplication());
        refreshButton.addActionListener(e -> refreshNotes());
        colorPickerButton.addActionListener(e -> onColorPicker(mainPanel,rightContentArea,bottomPanel,notesList));


        bottomPanel.add(colorPickerButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(addNewNoteButton);
        bottomPanel.add(editNoteButton);
        bottomPanel.add(deleteNoteButton);
        bottomPanel.add(encryptNoteButton);
        bottomPanel.add(unlockNoteButton);
        bottomPanel.add(exitButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    private void onColorPicker(JPanel mainPanel, JTextArea rightContentArea, JPanel bottomPanel, JList<String> notesList) {
        String[] options = {"Pembe", "Mavi", "Yeşil", "Light", "Dark"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Arka Plan Rengini Seç",
                "Renk Seç",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0 -> context.setState(new PinkState());
            case 1 -> context.setState(new BlueState());
            case 2 -> context.setState(new GreenState());
            case 3 -> context.setState(new DefaultState());
            case 4 -> context.setState(new DarkState());
            default -> {
                JOptionPane.showMessageDialog(this, "Geçersiz seçim!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        context.applyState(rightContentArea);
        context.applyState(mainPanel);
        context.applyState(bottomPanel);
        context.applyState(notesList);
    }

    private void refreshNotes() {
        // Notları veritabanından yeniden yükle
        loadNotes(userID);
        JOptionPane.showMessageDialog(this, "Notlar başarıyla yenilendi.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadNotes(int userId) {
        fetchedNotes = noteManager.getUserNotes(userId);
        listModel.clear(); // Listeyi temizleyip yeniden yükle

        for (Note note : fetchedNotes) {
            listModel.addElement(note.getTitle());
        }
    }

    private void showNoteContent() {
        String selectedNote = notesList.getSelectedValue();
        if (selectedNote != null) {
            for (Note note : fetchedNotes) {
                if (note.getTitle().equals(selectedNote)) {
                    if (note.getContent().startsWith("EELD")) {
                        rightContentArea.setText("Şifreli Not.");
                    } else {
                        rightContentArea.setText(note.getContent());
                    }
                }
            }
        } else {
            rightContentArea.setText("");
        }
    }

    private void openNewNoteScreen() {
        NewNoteScreen newNoteScreen = new NewNoteScreen(userID);
        newNoteScreen.setNoteAddedListener(this::onNewNoteAdded);
    }

    private void onNewNoteAdded(Note newNote) {
        if (newNote instanceof ImageNote) { // Eğer not bir ImageNote ise
            ImageNote imageNote = (ImageNote) newNote; // Downcast işlemi
            String imagePath = imageNote.getImagePath(); // Resim yolunu al
            noteManager.addNoteWithImage(imageNote, imagePath); // Resim notu ekleme metodunu çağır
        } else { // Eğer not bir TextNote ise veya normal not ise
            noteManager.addNote(newNote);
        }
        fetchedNotes.add(newNote); // Notu listeye ekle
    }

    private void onEditNote() {
        int selectedIndex = notesList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen düzenlemek için bir not seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Note selectedNote = fetchedNotes.get(selectedIndex);

        // Yeni başlık girdisini al
        String newTitle = JOptionPane.showInputDialog(this, "Yeni başlığı girin:", selectedNote.getTitle());
        if (newTitle == null || newTitle.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Başlık boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newContent = "";
        String newImagePath = "";

        // Eğer seçilen not bir ImageNote ise
        if (selectedNote instanceof ImageNote) {
            // Resim seçimi işlemi
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Resim Seçin");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            // Sadece resim dosyalarını gösterelim
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Resim Dosyaları", "jpg", "png", "jpeg", "gif"));

            // Kullanıcı dosya seçene kadar bekleyelim
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                newImagePath = selectedFile.getAbsolutePath();  // Seçilen dosyanın tam yolunu alıyoruz
                newContent = newImagePath;  // Burada dosyanın yolunu içerik olarak ayarlıyoruz

            }

            // Resim dosyası seçilmediyse işlem sonlanır
            if (newImagePath == null || newImagePath.isEmpty()) {
                System.out.println("Resim dosyası seçilmedi.");
                return;
            }
        }

        // Eğer resim seçilmemişse, içerik kısmını kullanıcıdan al
        if (newContent.isEmpty()) {
            newContent = JOptionPane.showInputDialog(this, "Yeni içeriği girin:", selectedNote.getContent());
            if (newContent == null || newContent.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "İçerik boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            // Notu güncelle
            if (selectedNote instanceof ImageNote) {
                // Resim notu ise, yeni resim yolunu ve içeriği de içerecek şekilde güncelleme yapılacak
                noteManager.updateNoteWithImage(selectedNote.getNote_id(), selectedNote.getOwner_id(), newTitle, newContent, newImagePath);
            } else {
                // Sadece metin notu ise, içerik ve başlık güncellenir
                noteManager.updateNote(selectedNote.getNote_id(), selectedNote.getOwner_id(), newTitle, newContent);
            }

            // Güncellenen başlığı ve içeriği liste modeline yansıt (isteğe bağlı)
            listModel.set(selectedIndex, newTitle);
            selectedNote.setTitle(newTitle);
            selectedNote.setContent(newContent);
            if (selectedNote instanceof ImageNote) {
                ((ImageNote) selectedNote).setImagePath(newImagePath);
            }

            rightContentArea.setText(newContent);
            JOptionPane.showMessageDialog(this, "Not başarıyla güncellendi.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Not güncellenemedi. Lütfen tekrar deneyin.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void onDeleteNote() {
        int selectedIndex = notesList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir not seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Note selectedNote = fetchedNotes.remove(selectedIndex);
        noteManager.deleteNote(selectedNote.getNote_id(),userID);
        listModel.remove(selectedIndex);
        rightContentArea.setText("");

        JOptionPane.showMessageDialog(this, "Not başarıyla silindi.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onEncryptNote() {
        int selectedIndex = notesList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen şifrelemek için bir not seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Note selectedNote = fetchedNotes.get(selectedIndex);
        if (selectedNote.getContent().startsWith("EELD")) {
            JOptionPane.showMessageDialog(this, "Bu not zaten şifrelenmiş durumda.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;  // Şifreli notu tekrar şifrelemeye çalışmıyoruz
        }
        try {
            // Kullanıcıdan parola al
            String password = JOptionPane.showInputDialog(this, "Şifrelemek için bir parola girin:");
            if (password == null || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Parola boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Şifreleme işlemi
            EncryptedNoteDecorator encryptDecorator = new EncryptedNoteDecorator(selectedNote);
            SecretKey secretKey = encryptDecorator.generateKeyFromPassword(password);
            String encryptedContent = encryptDecorator.encrypt(selectedNote.getContent(), secretKey);

            // Veritabanında güncelleme
            noteManager.updateNote(selectedNote.getNote_id(), selectedNote.getOwner_id(), selectedNote.getTitle(), encryptedContent);

            // Güncellenmiş içeriği GUI'ye yansıt
            selectedNote.setContent(encryptedContent);
            rightContentArea.setText("Şifreli Not");

            JOptionPane.showMessageDialog(this, "Not başarıyla şifrelendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Şifreleme sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onUnlockNote() {
        int selectedIndex = notesList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen kilidini açmak için bir not seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Note selectedNote = fetchedNotes.get(selectedIndex);

        try {
            String password = JOptionPane.showInputDialog(this, "Notun kilidini açmak için parolanızı girin:");
            if (password == null || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Parola boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            EncryptedNoteDecorator encryptDecorator = new EncryptedNoteDecorator(selectedNote);
            SecretKey secretKey = encryptDecorator.generateKeyFromPassword(password);
            String decryptedContent = encryptDecorator.decrypt(selectedNote.getContent(), secretKey);

            // Çözülen içeriği GUI'ye yansıt
            rightContentArea.setText(decryptedContent);
            JOptionPane.showMessageDialog(this, "Not başarıyla çözüldü!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kilit açma sırasında bir hata oluştu. Lütfen doğru parolayı girin.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void onExitApplication() {
        // Observer'ı çıkartalım (detach)
        noteManager.removeObserver(this);

        // Uygulamanın sonlanacağını bildiren mesaj
        JOptionPane.showMessageDialog(this, "Uygulama Sonlanıyor...");

        // Uygulamanın kapanması
        System.exit(0);
    }

}
