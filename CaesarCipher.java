// Encrypts message by shifting every letter by a certain number
  // Shifting by 1 to the right makes a to b, b to c, c to d, ... , y to z, and z to a

public class CaesarCipher {
  // Shifts right btw
  public static String encode(String text, int shift) {
    // Turns shift number into equivalent number from 0 to 25
      // Turns 1 to 1, 27 to 1, -1 to 25, etc.
    shift = shift > 0 ? shift % 26 : (26 + (shift % 26)) % 26;
    StringBuilder newText = new StringBuilder();
    int character;
    for (int i = 0; i < text.length(); i++) {
      character = text.charAt(i);
      // If character is a letter, shift it
        // First part checks lowercase, second uppercase
      if ((character >= 65 && character <= 90) || (character >= 97 && character <= 122)) {
        newText.append(shiftLetter(text.charAt(i), shift));
      } else {
        // If not a letter, keep it as is
        newText.append((char) character);
      }
    }
    return newText.toString();
  }

  // Prints all permutations of Caesar ciphers of a String
  public static String[] getPermutations(String text) {
    String[] permutations = new String[26];
    for (int i = 0; i < 26; i++) {
      permutations[i] = encode(text, i);
    }
    return permutations;
  }

  // Shifts letter by certain number
  public static char shiftLetter(char letter, int shift) {
    // Utilizes ascii, a format for converting characters to numbers that computers can work with
    // For uppercase letters
    if ((int) letter >= 97) {
      return (char) (((int) letter + shift - 97) % 26 + 97);
    }
    // For lowercase letters
    return (char) (((int) letter + shift - 65) % 26 + 65);
  }

  // Prints Strings in String[]
  public static void print(String[] arr) {
    for (String str : arr) {
      System.out.println(str);
    }
  }

  public static void main(String[] args) {
    // Ozxy fqbfdx xrnqj fsi xjnej ymj ifd!
    String text = "Let's see if this works.";
    int shift = 1;
    System.out.println(encode(text, shift));
  }
}
