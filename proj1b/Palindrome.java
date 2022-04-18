public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> x = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            x.addLast(word.charAt(i));
        }
        return x;
    }

    public boolean isPalindrome(String word) {
        int n = word.length();
        for (int i = 0; i < n / 2; i++) {
            if (word.charAt(i) != word.charAt(n - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int n = word.length();
        for (int i = 0; i < n / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(n - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}
