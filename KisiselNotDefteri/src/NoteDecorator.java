public abstract class NoteDecorator extends Note {
    protected Note decoratedNote;

    // NoteDecorator constructor'ı, Note sınıfının constructor'ına uygun parametrelerle çağrılır
    public NoteDecorator(Note note) {
        super(note.getTitle(), note.getContent(), note.getOwner_id());  // Note constructor'ına doğru parametreyi geçiriyoruz
        this.decoratedNote = note;
    }

    @Override
    public void saveToDatabase() {
        decoratedNote.saveToDatabase();
    }

//    Neden kullanıldı?
//
//    Notlara dinamik olarak yeni özellikler veya işlevler eklemek için kullanılır.
//    Örneğin, bir notun üzerine "önemli" etiketi eklemek veya bir notun stilini değiştirmek gibi işlemleri destekler.
//    Avantajları:
//
//    Not sınıfını değiştirmeden yeni özellikler ekleme olanağı sağlar.
//    Kod tekrarını önler ve genişletilebilirliği artırır.
//    Nasıl çalışır?
//
//    NoteDecorator temel sınıfı, bir notu sarmalar ve ek işlevsellik sağlar.
//    Yeni özellikler gerektiğinde, mevcut sınıfları değiştirmek yerine yeni bir dekoratör sınıfı eklenir.
}
