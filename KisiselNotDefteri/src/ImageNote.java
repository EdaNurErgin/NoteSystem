public class ImageNote extends Note{

    private String imagePath;

    public ImageNote(String title, String imagePath,String content, int owner_id) {
        super(title, content, owner_id);
        this.setImagePath(imagePath);
    }



    @Override
    public void saveToDatabase() {
        // ImageNote veritabanına kaydetme işlemi için, NotManager'dan addNoteWithImage() metodunu çağırıyoruz
        NoteManager.getInstance().addNoteWithImage(this, getImagePath());  // Burada imagePath de ekleniyor
    }


    // Getter method for imagePath
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
