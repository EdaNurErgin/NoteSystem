//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//       /* // Kullanıcı giriş işlemi
//        UserManager userManager = UserManager.getInstance();
//        //userManager.register("gokalp", "emailGokalp","8080");
//        int currentUserId = userManager.login("gokalp", "emailGokalp","8080");
//        UserManager userManager2 = UserManager.getInstance();
//        int currentUserId2 = userManager2.login("Eda", "EdaNur","12345");
//
//        // Giriş başarılıysa not ekleme işlemleri yapılır
//        if (currentUserId != -1) {
//            // Text Note ekleme
//            NoteFactory textNoteFactory = new TextNoteFactory();
//            Note textNote = textNoteFactory.createNote("Baslik metin", "Bu bir metin notudur.", currentUserId);
//            textNote.saveToDatabase();
//
//            // Image Note ekleme
//            NoteFactory imageNoteFactory = new ImageNoteFactory();
//            Note imageNote = imageNoteFactory.createNote("Baslik image", "Bu bir resim notudur.", currentUserId);
//            imageNote.saveToDatabase();
//        } else {
//            System.out.println("Giriş başarısız. Not ekleme işlemi gerçekleştirilemez.");
//        }
//
//        if (currentUserId2 != -1) {
//            // Text Note ekleme
//            NoteFactory textNoteFactory2 = new TextNoteFactory();
//            Note textNote2 = textNoteFactory2.createNote("eda metin", "eda Bu bir metin notudur.", currentUserId);
//            textNote2.saveToDatabase();
//
//            // Image Note ekleme
//            NoteFactory imageNoteFactory2 = new ImageNoteFactory();
//            Note imageNote2 = imageNoteFactory2.createNote("eda image", "eda Bu bir resim notudur.", currentUserId);
//            imageNote2.saveToDatabase();
//        } else {
//            System.out.println("Giriş başarısız. Not ekleme işlemi gerçekleştirilemez.");
//        }*/
//
//      /*  UserManager userManager = UserManager.getInstance();
//
//        // Kullanıcı giriş işlemi
//        int currentUserId = userManager.login("gokalp", "emailGokalp", "8080");
//        int currentUserId2 = userManager.login("Eda", "EdaNur", "12345");
//
//        if (currentUserId != -1) {
//            System.out.println("Giriş başarılı: Kullanıcı ID " + currentUserId);
//
//            // 1. Text Note ekleme
//            NoteFactory textNoteFactory = new TextNoteFactory();
//            Note textNote = textNoteFactory.createNote("Baslik metin", "Bu bir metin notudur.", currentUserId, 1);  // ID'yi manuel olarak veriyoruz
//            textNote.saveToDatabase();  // Veritabanına kaydetme işlemi
//            NoteManager.getInstance().addNote((TextNote) textNote);  // Veritabanına kaydediyoruz
//            System.out.println("Metin notu başarıyla eklendi: " + textNote.getTitle());
//
//            // 2. Image Note ekleme
//            NoteFactory imageNoteFactory = new ImageNoteFactory();
//            Note imageNote = imageNoteFactory.createNote("Baslik image", "Bu bir resim notudur.", currentUserId, 2);  // ID'yi manuel olarak veriyoruz
//            imageNote.saveToDatabase();  // Veritabanına kaydetme işlemi
//            NoteManager.getInstance().addNoteWithImage((ImageNote) imageNote, "path/to/image.jpg");  // Veritabanına kaydediyoruz
//            System.out.println("Resim notu başarıyla eklendi: " + imageNote.getTitle());
//
//            // 3. Listeleme: Kullanıcıya ait notları listele
//            List<Note> userNotes = NoteManager.getInstance().getUserNotes(currentUserId);
//            System.out.println("Kullanıcıya ait notlar:");
//            for (Note note : userNotes) {
//                System.out.println("Başlık: " + note.getTitle() + " | İçerik: " + note.getContent());
//            }
//
//            // 4. Güncelleme işlemi: Notu güncelle
//            String newTitle = "Yeni Başlık";
//            String newContent = "Bu bir güncellenmiş metin notudur.";
//            NoteManager.getInstance().updateNote(textNote.getNoteId(), currentUserId, newTitle, newContent);
//            System.out.println("Not başarıyla güncellendi: " + newTitle);
//
//            // 5. Güncellenen notları listele
//            userNotes = NoteManager.getInstance().getUserNotes(currentUserId);
//            System.out.println("Güncellenen notlar:");
//            for (Note note : userNotes) {
//                System.out.println("Başlık: " + note.getTitle() + " | İçerik: " + note.getContent());
//            }
//
//            // 6. Silme işlemi: Notu sil
//            NoteManager.getInstance().deleteNote(textNote.getNoteId(), currentUserId);
//            System.out.println("Not başarıyla silindi.");
//
//            // 7. Silinen notları kontrol et (Listele)
//            userNotes = NoteManager.getInstance().getUserNotes(currentUserId);
//            System.out.println("Silinen notlardan sonra kullanıcıya ait notlar:");
//            for (Note note : userNotes) {
//                System.out.println("Başlık: " + note.getTitle() + " | İçerik: " + note.getContent());
//            }
//
//        } else {
//            System.out.println("Giriş başarısız. Not ekleme işlemi gerçekleştirilemez.");
//        }
//
//        // İkinci kullanıcı işlemleri
//        if (currentUserId2 != -1) {
//            System.out.println("Giriş başarılı: Kullanıcı ID " + currentUserId2);
//
//            // 1. Text Note ekleme
//            NoteFactory textNoteFactory2 = new TextNoteFactory();
//            Note textNote2 = textNoteFactory2.createNote("eda metin", "eda Bu bir metin notudur.", currentUserId2, 3);  // ID'yi manuel olarak veriyoruz
//            textNote2.saveToDatabase();  // Veritabanına kaydetme işlemi
//            NoteManager.getInstance().addNote((TextNote) textNote2);  // Veritabanına kaydediyoruz
//            System.out.println("Metin notu başarıyla eklendi: " + textNote2.getTitle());
//
//            // 2. Image Note ekleme
//            NoteFactory imageNoteFactory2 = new ImageNoteFactory();
//            Note imageNote2 = imageNoteFactory2.createNote("eda image", "eda Bu bir resim notudur.", currentUserId2, 4);  // ID'yi manuel olarak veriyoruz
//            imageNote2.saveToDatabase();  // Veritabanına kaydetme işlemi
//            NoteManager.getInstance().addNoteWithImage((ImageNote) imageNote2, "path/to/eda_image.jpg");  // Veritabanına kaydediyoruz
//            System.out.println("Resim notu başarıyla eklendi: " + imageNote2.getTitle());
//
//            // 3. Listeleme: Kullanıcıya ait notları listele
//            List<Note> userNotes2 = NoteManager.getInstance().getUserNotes(currentUserId2);
//            System.out.println("Kullanıcıya ait notlar:");
//            for (Note note : userNotes2) {
//                System.out.println("Başlık: " + note.getTitle() + " | İçerik: " + note.getContent());
//            }
//
//        } else {
//            System.out.println("Giriş başarısız. Not ekleme işlemi gerçekleştirilemez.");
//        }
//*/
//
//        UserManager userManager = UserManager.getInstance();
//
//        // Kullanıcı giriş işlemi
//        int currentUserId = userManager.login("gokalp", "emailGokalp", "8080");
//        int currentUserId2 = userManager.login("Eda", "EdaNur", "12345");
//
//        // Kullanıcı 1 işlemleri
//        if (currentUserId != -1) {
//            System.out.println("Giriş başarılı: Kullanıcı ID " + currentUserId);
//
//            // 1. Text Note ekleme
//            addTextNoteToDatabase(currentUserId);
//
//            // 2. Image Note ekleme
//            addImageNoteToDatabase(currentUserId);
//
//            // 3. Kullanıcıya ait notları listele
//            listUserNotes(currentUserId);
//
//            // 4. Notu güncelle
//            updateNote(currentUserId);
//
//            // 5. Güncellenen notları listele
//            listUserNotes(currentUserId);
//
//            // 6. Notu sil
//            deleteNote(currentUserId);
//
//            // 7. Silinen notlardan sonra notları listele
//            listUserNotes(currentUserId);
//
//        } else {
//            System.out.println("Giriş başarısız. Not ekleme işlemi gerçekleştirilemez.");
//        }
//
//        // Kullanıcı 2 işlemleri
//        if (currentUserId2 != -1) {
//            System.out.println("Giriş başarılı: Kullanıcı ID " + currentUserId2);
//
//            // 1. Text Note ekleme
//            addTextNoteToDatabase(currentUserId2);
//
//            // 2. Image Note ekleme
//            addImageNoteToDatabase(currentUserId2);
//
//            // 3. Kullanıcıya ait notları listele
//            listUserNotes(currentUserId2);
//           // deleteNote(currentUserId2);
//
//            updateNote(currentUserId2);
//            listUserNotes(currentUserId2);
//
//        } else {
//            System.out.println("Giriş başarısız. Not ekleme işlemi gerçekleştirilemez.");
//        }
//    }
//
//    // Text Note ekleme işlemi
//    private static void addTextNoteToDatabase(int ownerId) {
//        NoteFactory textNoteFactory = new TextNoteFactory();
//        Note textNote = textNoteFactory.createNote("EDA metin", "EDA bir metin notudur.", ownerId, 1);  // ID'yi manuel olarak veriyoruz
//
//
//
//        // Başlangıçta DraftState olarak ayarlandı
//        System.out.println("Başlangıç Durumu: " + textNote.getState().getClass().getSimpleName());
//
//        // Durumu değiştirelim (PublishedState)
//        textNote.getState().changeState(textNote);  // Durum geçişini gerçekleştirir
//        textNote.setState(new PublishedState());  // Durumu değiştirelim (PublishedState)
//
//        // Durum değiştiğini doğrulamak için
//        System.out.println("Son Durum: " + textNote.getState().getClass().getSimpleName());
//
//       /* // Şifreleme işlemi uyguluyoruz
//        textNote = new EncryptedNoteDecorator(textNote);  // Note'ı şifreli hale getiriyoruz
//
//        textNote.saveToDatabase();  // Veritabanına kaydetme işlemi
//        NoteManager.getInstance().addNote((TextNote) textNote);  // Veritabanına kaydediyoruz
//        System.out.println("Metin notu başarıyla eklendi: " + textNote.getTitle()); */
//
//        // Şifreleme işlemi uyguluyoruz, ve sadece Note olarak kullanıyoruz
//
//       /* Note encryptedNote = new EncryptedNoteDecorator(textNote);  // Note'ı şifreli hale getiriyoruz
//
//        encryptedNote.saveToDatabase();  // Veritabanına kaydetme işlemi
//        NoteManager.getInstance().addNote(encryptedNote);  // Veritabanına kaydediyoruz
//        System.out.println("Metin notu başarıyla eklendi: " + encryptedNote.getTitle()); */
//        // Şifreleme işlemi uyguluyoruz, ve sadece Note olarak kullanıyoruz
//        Note encryptedNote = new EncryptedNoteDecorator(textNote);  // Note'ı şifreli hale getiriyoruz
//
//        encryptedNote.saveToDatabase();  // Veritabanına kaydetme işlemi
//        NoteManager.getInstance().addNote(encryptedNote);  // Veritabanına kaydediyoruz
//        System.out.println("Metin notu başarıyla eklendi: " + encryptedNote.getTitle());
//    }
//
//    // Image Note ekleme işlemi
//    private static void addImageNoteToDatabase(int ownerId) {
//        NoteFactory imageNoteFactory = new ImageNoteFactory();
//        Note imageNote = imageNoteFactory.createNote("Baslik image", "Bu bir resim notudur.", ownerId, 2);  // ID'yi manuel olarak veriyoruz
//        imageNote.saveToDatabase();  // Veritabanına kaydetme işlemi
//        NoteManager.getInstance().addNoteWithImage((ImageNote) imageNote, "path/to/image.jpg");  // Veritabanına kaydediyoruz
//        System.out.println("Resim notu başarıyla eklendi: " + imageNote.getTitle());
//    }
//
//    // Kullanıcıya ait notları listeleme
//    private static void listUserNotes(int ownerId) {
//        List<Note> userNotes = NoteManager.getInstance().getUserNotes(ownerId);
//        System.out.println("Kullanıcıya ait notlar:");
//        if (userNotes.isEmpty()) {
//            System.out.println("Kullanıcıya ait hiç not bulunmamaktadır.");
//        } else {
//            for (Note note : userNotes) {
//                System.out.println("Başlık: " + note.getTitle() + " | İçerik: " + note.getContent());
//            }
//        }
//    }
//
//    // Notu güncelleme işlemi
//    private static void updateNote(int ownerId) {
//        String newTitle = "EDA";
//        String newContent = "EDA Bu bir güncellenmiş metin notudur.";
//        List<Note> userNotes = NoteManager.getInstance().getUserNotes(ownerId);
//
//        if (userNotes.isEmpty()) {
//            System.out.println("Güncellenecek not bulunmamaktadır.");
//            return;
//        }
//
//        // İlk notu güncelliyoruz
//        Note noteToUpdate = userNotes.get(0);
//        NoteManager.getInstance().updateNote(noteToUpdate.getNoteId(), ownerId, newTitle, newContent);
//        System.out.println("Not başarıyla güncellendi: " + newTitle);
//    }
//
//    // Notu silme işlemi
//    private static void deleteNote(int ownerId) {
//        List<Note> userNotes = NoteManager.getInstance().getUserNotes(ownerId);
//
//        if (userNotes.isEmpty()) {
//            System.out.println("Silinecek not bulunmamaktadır.");
//            return;
//        }
//
//        // İlk notu siliyoruz
//        Note noteToDelete = userNotes.get(0);
//        NoteManager.getInstance().deleteNote(noteToDelete.getNoteId(), ownerId);
//        System.out.println("Not başarıyla silindi.");
//    }
//}
//
//
