import java.util.*;

public class VigenereCipherCracker {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String decipher(String ciphertext) {
        int keyLength = findProbableKeyLength(ciphertext, 20);
        System.out.println("Probably key length: " + keyLength);

        String[] groups = divideIntoGroups(ciphertext, keyLength);

        String key = findKey(groups);
        System.out.println("Key found: " + key);

        return decrypt(ciphertext, key);
    }

    public static String findKey(String[] groups) {
        StringBuilder key = new StringBuilder();
        for (String group : groups) {
            int shift = findShift(group);
            key.append((char) ('a' + shift));
        }
        return key.toString();
    }

    public static String[] divideIntoGroups(String ciphertext, int keyLength) {
        String[] groups = new String[keyLength];
        for (int i = 0; i < keyLength; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < ciphertext.length(); j += keyLength) {
                sb.append(ciphertext.charAt(j));
            }
            groups[i] = sb.toString();
        }
        return groups;
    }

    public static int findShift(String group) {
        int[] frequencies = new int[26];
        for (char c : group.toCharArray()) {
            if (Character.isLetter(c)) {
                frequencies[c - 'a']++;
            }
        }

        int bestShift = 0;
        double maxCorrelation = 0;
        for (int shift = 0; shift < 26; shift++) {
            double correlation = 0;
            for (int i = 0; i < 26; i++) {
                correlation += frequencies[(i + shift) % 26] * Helper.ENGLISH_LETTER_FREQUENCIES[i];
            }
            if (correlation > maxCorrelation) {
                maxCorrelation = correlation;
                bestShift = shift;
            }
        }
        return bestShift;
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            char k = key.charAt(i % key.length());
            plaintext.append((char) ((c - k + 26) % 26 + 'A'));
        }
        return plaintext.toString();
    }

    // Function to find the key based on frequency analysis
    public static int findProbableKeyLength(String ciphertext, int maxKeyLength) {
        ciphertext = ciphertext.toLowerCase().replaceAll("[^a-z]", "");
        return findKeyLength(ciphertext, maxKeyLength);
    }

    // Function to find probable key length using Index of Coincidence
    private static int findKeyLength(String ciphertext, int maxKeyLength) {
        Map<Integer, Double> keyLengthScores = new HashMap<>();

        for (int keyLength = 1; keyLength <= maxKeyLength; keyLength++) {
            double score = calculateIC(ciphertext, keyLength);
            keyLengthScores.put(keyLength, score);
        }

        return keyLengthScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(1);
    }

    // Calculate Index of Coincidence for a given key length
    private static double calculateIC(String ciphertext, int keyLength) {
        double totalIC = 0;
        for (int i = 0; i < keyLength; i++) {
            String subsequence = getNthSubsequence(ciphertext, i, keyLength);
            totalIC += indexOfCoincidence(subsequence);
        }
        return totalIC / keyLength;
    }

    // Compute Index of Coincidence
    private static double indexOfCoincidence(String text) {
        int[] frequencies = new int[ALPHABET.length()];
        int total = text.length();

        for (char c : text.toCharArray()) {
            frequencies[c - 'a']++;
        }

        double ic = 0;
        for (int count : frequencies) {
            ic += count * (count - 1);
        }
        return ic / (total * (total - 1));
    }

    // Extract nth subsequence based on key length
    private static String getNthSubsequence(String text, int start, int step) {
        StringBuilder subsequence = new StringBuilder();
        for (int i = start; i < text.length(); i += step) {
            subsequence.append(text.charAt(i));
        }
        return subsequence.toString();
    }
}