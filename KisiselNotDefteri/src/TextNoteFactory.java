public class TextNoteFactory extends NoteFactory{

    @Override
    public Note createNote(String title, String content, int owner_id) {
        return new TextNote(title,content,owner_id);
    }
}
