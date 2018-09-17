import java.util.Scanner;

/**
 * A class that "encrypts" data through a simple transformation.
 * <p>
 * The provided code is incomplete. Modify it so that it works properly and passes the unit tests in
 * <code>EncryptTest.java</code>.
 *
 * @see <a href="https://cs125.cs.illinois.edu/MP/1/">MP1 Documentation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Caesar Cipher Documentation</a>
 */
public class Encrypt {
    /** Minimum shift. */
    public static final int MIN_SHIFT = -1024;

    /** Maximum shift. */
    public static final int MAX_SHIFT = 1024;

    /** Transformation start. */
    public static final int TRANSFORM_START = (int) ' ';

    /** Transformation end. */
    public static final int TRANSFORM_END = (int) '~';

    /** Modulo to use for our transformation. */
    public static final int TRANSFORM_MODULUS = TRANSFORM_END - TRANSFORM_START + 1;

    /**
     * Encrypt a single line of text using a rotate-N transformation.
     * <p>
     * The printable range of ASCII characters starts at decimal value 32 (' ') and ends at 126
     * ('~'). You should shift characters within this range by the shift value provided. For
     * example, ' ' (32) shift 1 becomes '!' (33), while '~' (126) shift 1 wraps around and becomes
     * ' ' (32). You may want to explore modular arithmetic to simplify the transformation.
     * <p>
     * Your function should return a new character array, not modify the one that it is passed.
     * <p>
     * Both encrypter and decrypter may receive invalid inputs. If the character array contains invalid
     * characters (outside of the range defined above), or if the shift value is outside the range
     * defined above (e.g., larger than MAX_SHIFT), you should return null.
     * <p>
     * <strong>Your solution must match the expected output exactly, otherwise you will not receive
     * credit.</strong>
     * <p>
     * Complete the Javadoc comment for this function and write it.
     *
     * @see <a href="http://www.asciitable.com/">ASCII Character Table</a>
     * @param line an array of characters
     * @param shift an integer for the amount of shifts
     * @return car another array of characters
     */
    public static char[] encrypter(final char[] line, final int shift) {
       char[] ncryptCar = new char[line.length];
       int newPos;
        if (line == null) {
           return null;
       }
       if (shift > MAX_SHIFT || shift < MIN_SHIFT) {
           return null;
       }
       for (int i = 0; i < line.length; i++) {
           int asc2 = (int) line[i];
           if (asc2 < TRANSFORM_START || asc2 > TRANSFORM_END) {
               return null;
           }
           newPos = asc2 + shift;
           while (newPos < TRANSFORM_START) {
                newPos = newPos + TRANSFORM_MODULUS;
           }
           while (newPos > TRANSFORM_END) {
                newPos = newPos - TRANSFORM_MODULUS;
           }
           char newCar = (char) newPos;
           ncryptCar[i] = newCar;
       }
       return ncryptCar;
    }

    /**
     * Decrypt a single line of text using a rotate-N transformation.
     * <p>
     * See comment for encrypter above.
     *
     * @param line array of characters to decrypt
     * @param shift amount to shift each character
     * @return line decrypted by rotating the specified amount
     * @see <a href="http://www.asciitable.com/">ASCII Character Table</a>
     */
    public static char[] decrypter(final char[] line, final int shift) {
        char[] ncryptCar1 = new char[line.length];
        int newPos1;
        if (line == null) {
            return null;
        }
        if (shift > MAX_SHIFT || shift < MIN_SHIFT) {
            return null;
        }
        for (int j = 0; j < line.length; j++) {
            int asc21 = (int) line[j];
            if (asc21 < TRANSFORM_START || asc21 > TRANSFORM_END) {
                return null;
            }
            newPos1 = asc21 - shift;
            while (newPos1 < TRANSFORM_START) {
                newPos1 = newPos1 + TRANSFORM_MODULUS;
            }
            while (newPos1 > TRANSFORM_END) {
                newPos1 = newPos1 - TRANSFORM_MODULUS;
            }
            char newCar1 = (char) newPos1;
            ncryptCar1[j] = newCar1;
        }
        return ncryptCar1;
    }

    /* ********************************************************************************************
     * You do not need to modify code below this comment.
     **********************************************************************************************/

    /**
     * Solicits a single line of text from the user, encrypts it using a random shift, and then
     * decrypts it.
     * <p>
     * You are free to review this function, but should not modify it. Note that this function is
     * not tested by the test suite, as it is purely to aid your own interactive testing.
     *
     * @param unused unused input arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] unused) {

        String linePrompt = "Enter a line of text, or a blank line to exit:";
        String shiftPrompt = "Enter an integer to shift by:";

        /*
         * Two steps here: first get a line, then a shift integer.
         */
        Scanner lineScanner = new Scanner(System.in);
        repeat: while (true) {
            String line = null;
            Integer shift = null;

            System.out.println(linePrompt);
            //noinspection LoopStatementThatDoesntLoop
            while (lineScanner.hasNextLine()) {
                line = lineScanner.nextLine();
                if (line.equals("")) {
                    break repeat;
                } else {
                    break;
                }
            }

            System.out.println(shiftPrompt);
            while (lineScanner.hasNextLine()) {
                Scanner intScanner = new Scanner(lineScanner.nextLine());
                if (intScanner.hasNextInt()) {
                    shift = intScanner.nextInt();
                    if (intScanner.hasNext()) {
                        shift = null;
                        System.out.println("Invalid input: please enter only a single integer.");
                    }
                } else {
                    System.out.println("Invalid input: please enter an integer.");
                }
                intScanner.close();
                if (shift != null) {
                    break;
                }
            }

            if (line == null || line.equals("")) {
                throw new RuntimeException("Should have a line at this point");
            }
            if (shift == null) {
                throw new RuntimeException("Should have a shift value at this point");
            }

            char[] originalCharacterArray = line.toCharArray();
            char[] encryptedCharacterArray = encrypter(originalCharacterArray, shift);
            char[] decryptedCharacterArray = decrypter(encryptedCharacterArray, shift);

            System.out.println("Encrypted line with ROT-" + shift + ":");
            assert encryptedCharacterArray != null;
            System.out.println(String.valueOf(encryptedCharacterArray));
            System.out.println("Decrypted line:");
            System.out.println(String.valueOf(decryptedCharacterArray));
            System.out.println("Original line:");
            System.out.println(String.valueOf(originalCharacterArray));
        }
        lineScanner.close();
    }
}
