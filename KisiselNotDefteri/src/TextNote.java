public class TextNote extends Note{

    public TextNote(String title, String content, int owner_id) {
        super(title, content, owner_id);  // note_id'yi constructor'a ekliyoruz
    }

    @Override
    public void saveToDatabase() {
        // TextNote veritabanına kaydetme işlemi (sadece başlık ve içerik kaydedilir)
        NoteManager.getInstance().addNote(this);  // Veritabanına kaydediyoruz
    }
}
