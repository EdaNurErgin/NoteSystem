public abstract class Note {
    private String title; // Başlık
    private int note_id;
    private String content; // İçerik
    private int owner_id; //Not Sahibi


    public Note(String title, String content, int owner_id) {
        this.setContent(content);
        this.owner_id=owner_id;
        this.setTitle(title);
    }



    // Getter metodları
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // Abstract metod (alt sınıflar implement etmeli)
    public abstract void saveToDatabase();  // Bu metod, her alt sınıf için farklı şekilde implement edilecek


    public int getNote_id() {
        return note_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }
}
