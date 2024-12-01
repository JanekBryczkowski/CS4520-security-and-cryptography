import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static int CIPHER_USED = 2; //1 - Caesar Cipher, 2 - Vigenere Cipher

    public static void main(String[] args) {
        try {
            Scanner fileScanner = new Scanner(new File("input_files/input_text_2.txt"));

            StringBuilder cipherTextUnformatted = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                cipherTextUnformatted.append(fileScanner.nextLine()).append("\n");
            }

            fileScanner.close();

            System.out.println("Cipher Text Unformatted:");
            System.out.println(cipherTextUnformatted);

            String cipherText = stringFormatter(cipherTextUnformatted.toString());
            System.out.println("Cipher Text:");
            System.out.println(cipherText);

            if (CIPHER_USED == 1) {
                String decryptedUnformatted = CaesarCipherDecipher.decipher(cipherText);
                String plaintext = Helper.restoreFormatting(String.valueOf(cipherTextUnformatted), decryptedUnformatted);
                System.out.println("Plaintext:");
                System.out.println(plaintext);
            } else if (CIPHER_USED == 2) {
                String decryptedUnformatted = VigenereCipherCracker.decipher(cipherText);
                String plaintext = Helper.restoreFormatting(String.valueOf(cipherTextUnformatted), decryptedUnformatted);
                System.out.println("\nPlaintext:");
                System.out.println(plaintext);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public static String stringFormatter(String unformattedText) {
        return unformattedText
                .replaceAll("[^a-zA-Z\\s]", "")
                .replaceAll(" ", "")
                .toLowerCase();
    }
}