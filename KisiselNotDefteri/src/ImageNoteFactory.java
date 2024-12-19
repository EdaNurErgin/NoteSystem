public class ImageNoteFactory extends NoteFactory{

   /* @Override
    public Note createNote(String title, String content, int owner_id) {
        String defaultImagePath = "/path/to/default/image.jpg";
        return new ImageNote(title,content,owner_id,defaultImagePath); */

        @Override
        public Note createNote(String title, String content, int owner_id) {
            String defaultImagePath = "/path/to/default/image.jpg";  // Varsayılan resim yolu
            return new ImageNote(title, content,defaultImagePath, owner_id);
    }
}
