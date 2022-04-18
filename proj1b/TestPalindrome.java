import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator cc = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));

        assertTrue(palindrome.isPalindrome("aka"));

        assertTrue(palindrome.isPalindrome(""));

        assertTrue(palindrome.isPalindrome("a"));

        assertTrue(palindrome.isPalindrome("kayak"));
    }

    @Test
    public void testIsPalindromeWithCC() {
        assertTrue(palindrome.isPalindrome("flake", cc));
    }
}
