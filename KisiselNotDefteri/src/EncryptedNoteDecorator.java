import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.security.SecureRandom;

public class EncryptedNoteDecorator extends NoteDecorator {
    public EncryptedNoteDecorator(Note decoratedNote) {
        super(decoratedNote);
    }

    @Override
    public void saveToDatabase() {
        try {
            // Kullanıcıya parola sorulmuyorsa sabit bir parola belirleyebilirsiniz.
            String password = "defaultPassword";
            SecretKey secretKey = generateKeyFromPassword(password);

            // Şifreli başlık ve içerik oluşturma
            String encryptedTitle = encrypt(decoratedNote.getTitle(), secretKey);
            String encryptedContent = encrypt(decoratedNote.getContent(), secretKey);

            // Şifreli başlık ve içeriği yeni bir TextNote olarak kaydetme
            TextNote encryptedNote = new TextNote(encryptedTitle, encryptedContent, decoratedNote.getOwner_id());
            encryptedNote.saveToDatabase();
        } catch (Exception e) {
            System.err.println("Veritabanına şifreli not kaydedilirken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static SecretKey generateKeyFromPassword(String password) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(password.getBytes());
        byte[] truncatedKey = new byte[16]; // AES için 16 byte anahtar gerekir
        System.arraycopy(keyBytes, 0, truncatedKey, 0, truncatedKey.length);
        return new SecretKeySpec(truncatedKey, "AES");
    }

    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        // AES şifreleme için Cipher'ı başlatıyoruz
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // IV (Initialization Vector) oluşturuluyor
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Şifreleme işlemi
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // Şifreli metni Base64 formatında kodlayıp IV'yi metnin başına ekliyoruz
        byte[] ivAndCipherText = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, ivAndCipherText, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, ivAndCipherText, iv.length, encryptedBytes.length);

        // Şifreli metni döndürüyoruz
        return "EELD" + Base64.getEncoder().encodeToString(ivAndCipherText);
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        // "EELD" etiketini kes
        if (encryptedText.startsWith("EELD")) {
            encryptedText = encryptedText.substring(4);
        }

        // Base64 formatında kodlanmış şifreli metni çözüyoruz
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);

        // IV'yi şifreli metnin başından ayırıyoruz
        byte[] iv = Arrays.copyOfRange(decodedBytes, 0, 16);  // İlk 16 byte IV'dir
        byte[] cipherText = Arrays.copyOfRange(decodedBytes, 16, decodedBytes.length);  // Geri kalan byte'lar şifreli metin

        // AES şifreleme için Cipher'ı başlatıyoruz
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        // Şifreli metni deşifre ediyoruz
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }
}
