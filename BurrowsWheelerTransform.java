import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* Let's say we have the String "abbbbbcccccdddee"
     We can save space by writing it as 1a5b5c3d2e
   Doing this with real English words isn't very practical since they don't tend to have many repeated characters
   Introducing Burrows-Wheeler transform, a process that helps with converting words to repeating strings of characters
     Clumps some of the same characters together, not necessarily all
     More importantly though, it's reversible

   Example: bananarama
   1. Write every shift of input
     b a n a n a r a m a
     a b a n a n a r a m
     m a b a n a n a r a
     a m a b a n a n a r
     r a m a b a n a n a
     a r a m a b a n a n
     n a r a m a b a n a
     a n a r a m a b a n
     n a n a r a m a b a
     a n a n a r a m a b
   2. Sort shifts into alphabetical order
                       v
     a b a n a n a r a m   0
     a m a b a n a n a r   1
     a n a n a r a m a b   2
     a n a r a m a b a n   3
     a r a m a b a n a n   4
   > b a n a n a r a m a < 5
     m a b a n a n a r a
     n a n a r a m a b a
     n a r a m a b a n a
     r a m a b a n a n a
                       ^
   3. Output is last column, and the row # the original String is (starting at 0)
     mrbnnaaaaa 5
 */

public class BurrowsWheelerTransform {

  public static String[] encode(String text) {
    // Turns bananarama to bananaramabananarama
    String doubleS = text + text;
    int length = text.length();
    if (length == 0) {
      return null;
    }
    // Gets first 10 10-long Strings from bananaramabananarama
    String[] strings = new String[length];
    for (int i = 0; i < length; i++) {
      strings[i] = doubleS.substring(i, i + length);
    }
    // Sorts it alphabetically
    Arrays.sort(strings);
    StringBuilder stuff = new StringBuilder();
    int index = 0;
    // Gets last character
    for (int i = 0; i < length; i++) {
      stuff.append(strings[i].charAt(length - 1));
      // Sets index of original String
      if (strings[i].equals(text)) {
        index = i;
      }
    }
    return new String[] {stuff.toString(), String.valueOf(index)};
  }

  /*
  mrbnnaaaaa 5
  String is sorted to aaaaabmnnr
  Sorted   Stuff   Unsorted
    a       ...       m
    a       ...       r
    a       ...       b
    a       ...       n
    a       ...       n
  > b       ...       a <
    m       ...       a
    n       ...       a
    n       ...       a
    r       ...       a
  Sorted character at index 5 is b, unsorted b has third sorted a, third unsorted a has first sorted n, first unsorted n has fourth sorted a, fourth unsorted a has second sorted n, ...
  Getting all the sorted letters gives bananarama
   */
  public static String decode(String text, int num) {
    int length = text.length();
    if (length == 0) {
      return "";
    }
    // Gets first column
      // text to char[], sorted, to String, to ArrayList
        // char[] for sorting, String for working normally, ArrayList for indexOf-ing
    char[] sorted = text.toCharArray();
    Arrays.sort(sorted);
    String sortedString = new String(sorted);
    ArrayList<Character> sortedChars = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      sortedChars.add(sortedString.charAt(i));
    }
    int[] first = new int[length];
    ArrayList<Integer> last = new ArrayList<>();
    int index;
    for (int i = 0; i < length; i++) {
      first[i] = i;
      // Gets indices of unsorted letters in order
      index = sortedChars.indexOf(text.charAt(i));
      last.add(index);
      // The way I first worked this out, "Fourth sorted a and unsorted a" were considered third after having already done third a
        // This effectively implements that... I think
        // Honestly not too sure about that or this explanation for that matter; it's been a hot minute since I made this whole mess
        // I also think it's unnecessary, but I don't know how to get rid of it
          // Doesn't seem too urgent though, so I'm fine leaving it here
      sortedChars.set(index, null);
    }
    // Makes StringBuilder initialized with sorted character at given index num
    StringBuilder str = new StringBuilder(sortedString.substring(num, num + 1));
    int firstInt = num;
    int lastIndex;
    // - 1 to not append another b at the end
      // Getting all unsorted characters probably works, and it shouldn't need initialization or - 1
    for (int i = 0; i < length - 1; i++) {
      lastIndex = last.indexOf(firstInt);
      firstInt = first[lastIndex];
      // Appends sorted characters
      str.append(sortedString.charAt(firstInt));
    }
    return str.toString();
  }

  public static void print(String str) {
    System.out.println(str);
  }

  // Prints Strings in String[]
  public static void print(String[] arr) {
    for (String str : arr) {
      System.out.println(str);
    }
  }

  public static void main(String[] args) {
    print(Objects.requireNonNull(encode("bananarama")));
    print(decode("mrbnnaaaaa", 5));
  }
}
