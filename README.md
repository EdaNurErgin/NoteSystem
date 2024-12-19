# NoteSystem
 Note System
 
---Kisisel Not Defteri---

---Proje Hakkinda
Kisisel Not Defteri, kullanicilarin metin ve gorsel notlar olusturmasini, duzenlemesini ve yonetmesini saglayan bir masaustu uygulamasidir. Bu proje, modern yazilim gelistirme prensiplerini ve tasarim desenlerini kullanarak, kullanici dostu bir deneyim sunmayi amaclamaktadir.

---Proje Amaci
Kisisel Not Defteri uygulamasinin temel amaclari:
-Kullanicilarin organize bir sekilde metin ve gorsel notlarini kaydetmelerini saglamak.
-Notlari sifreleyerek veri guvenligini arttirmak.
-Renkli arka plan temalari ile kullanici deneyimini kisisellestirmek.
-Gorsel ve metin tabanli notlarin yonetimini bir araya getirerek kullanim kolayligi sunmak.

---Proje Ozellikleri
--Kullanici Yonetimi:
Yeni kullanici kayit sistemi
Kullanici girisi ve dogrulama
-Not Yonetimi:
Metin ve gorsel notlar olusturma
Notlari duzenleme ve silme
-Not sifreleme (Decorator tasarim deseni kullanilmistir)
-Tema Destegi:
Farkli renk modlari: Mavi, Koyu, Yesil, Pempe
Gelismis Kullanici Arayuzu:
Kolay kullanilabilir Swing tabanli GUI

---Proje Yapisi
Proje, asagidaki klasor yapisina sahiptir:
src/
-Ana Siniflar:
MainGUI.java: Ana arayuzu isler. Calismayi baslatir.
-Kullanici Yonetimi:
User.java: Kullanici bilgilerini temsil eder.
UserManager.java: Kullanici yonetimi icin metodlar.
UserDatabase.java: Kullanici bilgilerini saklar.
-Not Yonetimi:
Note.java: Genel not sinifi.
TextNote.java: Metin tabanli notlar.
ImageNote.java: Gorsel tabanli notlar.
NoteManager.java: Not yonetimini saglar.
TextNoteFactory.java, ImageNoteFactory.java: Farkli turde not nesnesi olusturmak icin.
NoteDecorator.java, EncryptedNoteDecorator.java: Notlari sifrelemek veya ek ozellik kazandirmak icin kullanilir.
NoteFactory.java: Not tiplerini olusturur.
NoteObserver.java, Subject, UserActivity.java: Gozlemci bilgilendirilmesi.
-Arka Plan Temalari:
BackgroundState.java: Tema bazli mekanizma.
BlueState.java, DarkState.java, GreenState.java, PinkState.java: Tema cesitleri.
Arayuzler:
LoginGUI.java: Giris ekranı.
RegisterGUI.java: Kayit ekranı.
NewNoteScreen.java: Yeni not olusturma ekranı.
-Veritabanı Baglantisi:
DatabaseConnection.java: Veritabanı baglantisi.

---Kurulum
Proje Deposu: Bu projeyi GitHub deposundan klonlayin ya da indirin.
git clone <repository-url>
Gelistirme Ortami: IDE'nizde (IntelliJ IDEA kullanilarak proje yazildi) projeyi acin.
Bagimliliklari Kontrol Edin: Gerekli kutuphanelerin yuklu oldugundan emin olun.

---Calistirma:
MainGUI.java dosyasini calistirin.
