public class Helper {
    public static double[] ENGLISH_LETTER_FREQUENCIES = {
            8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153,
            0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056,
            2.758, 0.978, 2.360, 0.150, 1.974, 0.074
    };

    public static String restoreFormatting(String ciphertext, String decryptedText) {
        StringBuilder formattedText = new StringBuilder();
        int decryptedIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);

            if ((cipherChar >= 'A' && cipherChar <= 'Z') || (cipherChar >= 'a' && cipherChar <= 'z')) {
                if (decryptedIndex < decryptedText.length()) {
                    char decryptedChar = decryptedText.charAt(decryptedIndex++);
                    if (Character.isUpperCase(cipherChar)) {
                        formattedText.append(Character.toUpperCase(decryptedChar));
                    } else {
                        formattedText.append(Character.toLowerCase(decryptedChar));
                    }
                }
            } else {
                formattedText.append(cipherChar);
            }
        }

        return formattedText.toString();
    }
}
