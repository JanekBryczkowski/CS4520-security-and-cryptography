public class CaesarCipherDecipher {

    // Function to decipher the ciphertext without knowing the shift
    public static String decipher(String ciphertext) {
        double minChiSquared = Double.MAX_VALUE;
        int bestShift = 0;

        double[] ciphertextFrequencies = calculateFrequencies(ciphertext);

        for (int shift = 0; shift < 26; shift++) {
            double[] shiftedFrequencies = new double[26];

            for (int i = 0; i < 26; i++) {
                shiftedFrequencies[i] = ciphertextFrequencies[(i + shift) % 26];
            }

            double chiSquared = calculateChiSquared(shiftedFrequencies, Helper.ENGLISH_LETTER_FREQUENCIES);

            if (chiSquared < minChiSquared) {
                minChiSquared = chiSquared;
                bestShift = shift;
            }
        }

        System.out.println("Detected Shift: " + bestShift);

        return decipherWithShift(ciphertext, bestShift);
    }

    // Function to calculate letter frequencies in a string
    private static double[] calculateFrequencies(String text) {
        int[] counts = new int[26];
        int totalLetters = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                counts[c - 'a']++;
                totalLetters++;
            }
        }

        double[] frequencies = new double[26];
        for (int i = 0; i < 26; i++) {
            frequencies[i] = (counts[i] / (double) totalLetters) * 100;
        }

        return frequencies;
    }

    // Function to compute the Chi-Squared statistic
    private static double calculateChiSquared(double[] observed, double[] expected) {
        double chiSquared = 0.0;

        for (int i = 0; i < 26; i++) {
            double expectedCount = expected[i];
            if (expectedCount > 0) {
                chiSquared += Math.pow(observed[i] - expectedCount, 2) / expectedCount;
            }
        }

        return chiSquared;
    }

    // Function to decipher the ciphertext using a guessed shift
    private static String decipherWithShift(String ciphertext, int shift) {
        StringBuilder plaintext = new StringBuilder();

        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char deciphered = (char) ((c - 'a' - shift + 26) % 26 + 'a');
                plaintext.append(deciphered);
            } else {
                plaintext.append(c);
            }
        }

        return plaintext.toString();
    }
}