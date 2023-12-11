package org.example;

import java.util.HashMap;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MorseCodeConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input
        System.out.print("Enter an English string to convert to Morse code: ");
        String userInput = scanner.nextLine();

        // Convert to Morse code
        String morseCode = convertToMorseCode(userInput);
        System.out.println("Morse Code: " + morseCode);

        // Hashing using SHA-256
        String hashedString = getSHA256(morseCode);
        System.out.println("SHA-256 Hash: " + hashedString);

        // Caesar Cipher with a 5-character shift
        String caesarCipher = performCaesarCipher(userInput, 5);
        System.out.println("Caesar Cipher (5-character shift): " + caesarCipher);

        // Brute force all 0-25 shifts of the English string
        bruteForceCaesarCipher(userInput);

        scanner.close();
    }

    public static String convertToMorseCode(String text) {
        // Morse code mapping
        HashMap<Character, String> morseCodeMap = new HashMap<>();
        morseCodeMap.put('A', "~"); morseCodeMap.put('B', "!"); morseCodeMap.put('C', "@");
        morseCodeMap.put('D', "#"); morseCodeMap.put('E', "$"); morseCodeMap.put('F', "%");
        morseCodeMap.put('G', "^"); morseCodeMap.put('H', "&"); morseCodeMap.put('I', "*");
        morseCodeMap.put('J', "~!"); morseCodeMap.put('K', "@#"); morseCodeMap.put('L', "$%");
        morseCodeMap.put('M', "^&"); morseCodeMap.put('N', "**"); morseCodeMap.put('O', "~!!");
        morseCodeMap.put('P', "!@@"); morseCodeMap.put('Q', "@##$"); morseCodeMap.put('R', "#$#$");
        morseCodeMap.put('S', "$%%$"); morseCodeMap.put('T', "%%%^"); morseCodeMap.put('U', "^&&&");
        morseCodeMap.put('V', "&*&*"); morseCodeMap.put('W', "****"); morseCodeMap.put('X', "!*!*");
        morseCodeMap.put('Y', "@&@&"); morseCodeMap.put('Z', "#^#^"); morseCodeMap.put('1', "$%$%");
        morseCodeMap.put('2', "%%^%^"); morseCodeMap.put('3', "@&#$%"); morseCodeMap.put('4', "$^^%$-");
        morseCodeMap.put('5', "~~$#$"); morseCodeMap.put('6', "!!@#!"); morseCodeMap.put('7', "&^*%*");
        morseCodeMap.put('8', "#*&^@"); morseCodeMap.put('9', "$!~@#."); morseCodeMap.put('0', "***@*");

        StringBuilder morseCodeBuilder = new StringBuilder();
        text = text.toUpperCase();

        for (char c : text.toCharArray()) {
            if (c == ' ') {
                morseCodeBuilder.append(" ");
            } else if (morseCodeMap.containsKey(c)) {
                morseCodeBuilder.append(morseCodeMap.get(c)).append(" ");
            }
        }

        return morseCodeBuilder.toString().trim();
    }

    public static String getSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String performCaesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char shifted = (char) (ch + shift);
                if ((Character.isLowerCase(ch) && shifted > 'z') || (Character.isUpperCase(ch) && shifted > 'Z')) {
                    shifted = (char) (ch - (26 - shift));
                }
                result.append(shifted);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static void bruteForceCaesarCipher(String text) {
        System.out.println("Brute Force Caesar Cipher:");

        for (int i = 0; i < 26; i++) {
            System.out.println("Shift " + i + ": " + performCaesarCipher(text, i));
        }
    }
}
