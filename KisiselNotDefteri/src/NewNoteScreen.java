import javax.swing.*;
import java.awt.*;
import java.io.File;

// Yeni Not Ekranı
public class NewNoteScreen extends JFrame {
    private JTextField titleField;
    private JButton saveButton;
    private JComboBox<String> noteTypeComboBox;
    private JTextField imagePathField;
    private JButton browseButton;
    private int userId;
    private JPanel dynamicPanel;
    private JTextArea textContentArea;
    private NoteAddedListener listener;

    public NewNoteScreen(int userId) {
        this.userId = userId;
        initUI();
    }

    private void initUI() {
        setTitle("Yeni Not Ekle");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Üst Panel
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Dinamik alan
        dynamicPanel = new JPanel(new CardLayout());
        JPanel dynamicContainer = createDynamicPanel();
        mainPanel.add(dynamicContainer, BorderLayout.CENTER);


        // Alt panel ile düğme işlemleri
        JPanel bottomPanel = new JPanel();
        saveButton = new JButton("Kaydet");
        saveButton.addActionListener(e -> saveNote());
        bottomPanel.add(saveButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 5, 5));

        JLabel titleLabel = new JLabel("Başlık:");
        titleField = new JTextField();
        JLabel typeLabel = new JLabel("Not Türü:");
        noteTypeComboBox = new JComboBox<>(new String[]{"TextNote", "ImageNote"});

        // Dinamik alanlar için etkinleştirme işlemi
        noteTypeComboBox.addActionListener(e -> toggleFields());

        topPanel.add(titleLabel);
        topPanel.add(titleField);
        topPanel.add(typeLabel);
        topPanel.add(noteTypeComboBox);

        return topPanel;
    }

    private JPanel createDynamicPanel() {
        JPanel container = new JPanel(new BorderLayout());

        // TextNote Panel
        JPanel textNotePanel = new JPanel();
        textContentArea = new JTextArea();
        JScrollPane textScrollPane = new JScrollPane(textContentArea);
        textScrollPane.setBorder(BorderFactory.createTitledBorder("Metin İçeriği"));
        textNotePanel.setLayout(new BorderLayout());
        textNotePanel.add(textScrollPane, BorderLayout.CENTER);

        // ImageNote Panel
        JPanel imagePanel = new JPanel();
        imagePathField = new JTextField();
        browseButton = new JButton("Gözat");
        browseButton.addActionListener(e -> browseImage());
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Görsel Yolu"));
        imagePanel.add(imagePathField, BorderLayout.CENTER);
        imagePanel.add(browseButton, BorderLayout.EAST);

        dynamicPanel.add(textNotePanel, "TextNote");
        dynamicPanel.add(imagePanel, "ImageNote");

        container.add(dynamicPanel, BorderLayout.CENTER);
        container.add(dynamicPanel, BorderLayout.CENTER);

        // Gösterim için varsayılan alanı tetikle
        toggleFields();

        return container;
    }

    // Alanlar için mantık toggle işlemlerini gerçekleştirin
    private void toggleFields() {
        String selectedType = (String) noteTypeComboBox.getSelectedItem();
        CardLayout layout = (CardLayout) dynamicPanel.getLayout();

        // Dinamik olarak CardLayout değiştirme
        if ("TextNote".equals(selectedType)) {
            layout.show(dynamicPanel, "TextNote");
        } else if ("ImageNote".equals(selectedType)) {
            layout.show(dynamicPanel, "ImageNote");
        }
    }

    private void browseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void saveNote() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Başlık alanı boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedType = (String) noteTypeComboBox.getSelectedItem();
        Note newNote = null;

        if ("TextNote".equals(selectedType)) {
            String content = textContentArea.getText().trim();
            newNote = new TextNote(title, content, userId);
        } else if ("ImageNote".equals(selectedType)) {
            String imagePath = imagePathField.getText().trim();
            if (imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen bir görsel yolu seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            newNote = new ImageNote(title, imagePath, "", userId);
        }

        if (newNote != null && listener != null) {
            listener.onNoteAdded(newNote);
            dispose();
        }
    }
    interface NoteAddedListener {
        void onNoteAdded(Note note);
    }

    public void setNoteAddedListener(NoteAddedListener listener) {
        this.listener = listener;
    }


}
