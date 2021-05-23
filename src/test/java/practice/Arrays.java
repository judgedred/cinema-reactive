package practice;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Arrays {

//    Arrays and strings
    @Test
    void permutation() {
        assertTrue(isPermutation("dfghhkj", "hgh"));
        assertFalse(isPermutation("dfghlkg", "hgh"));
        assertFalse(isPermutation("", "hgh"));
        assertFalse(isPermutation("dfghlkg", ""));
        assertFalse(isPermutation("", ""));
    }

    private boolean isPermutation(String s, String s2) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (int j = 0; j < s2.length(); j++) {
                if (c == s2.charAt(j)) {
                    if (s.length() == i + 1 || s2.length() == j + 1) {
                        return true;
                    }
                    return isPermutation(s.substring(i + 1), s2.substring(0, j) + s2.substring(j + 1));
                }
            }
        }
        return false;
    }

    @Test
    void urlify() {
        assertEquals("Mr%20John%20Smith", doUrlify("Mr John Smith    ", 13));
    }

    private String doUrlify(String s, int length) {
        int lastIndex = s.length() - 1;
        char[] chars = s.toCharArray();
        for (int i = length - 1; i >= 0; i--) {
            if (chars[i] == ' ') {
                chars[lastIndex] = '0';
                lastIndex--;
                chars[lastIndex] = '2';
                lastIndex--;
                chars[lastIndex] = '%';
                lastIndex--;
            } else {
                chars[lastIndex] = chars[i];
                lastIndex--;
            }
        }
        return new String(chars);
    }

// 1.4
    @Test
    void palindromePermutation() {
        permute("abcd", 0, "abcd".length() - 1);
//        assertTrue(checkAllPermutations("abcd", ""));
//        assertTrue(hasPalindromePermutation("hhhh g"));
//        assertTrue(hasPalindromePermutation("hhgh h"));
//        assertTrue(hasPalindromePermutation("hhgh g"));
//        assertTrue(hasPalindromePermutation("Tact Coa"));
//        assertTrue(hasPalindromePermutation("Oact Cta"));
//        assertTrue(hasPalindromePermutation("O actcta"));
//        assertTrue(hasPalindromePermutation(" Oactcta"));
//        assertTrue(hasPalindromePermutation("Oactcta "));
//        assertFalse(hasPalindromePermutation("O actbta"));
//        assertFalse(hasPalindromePermutation("TacB Coa"));
    }

//    brute force all permutations. return if found.
    private boolean checkAllPermutations(String s, String result) {
        if (s.isEmpty()) {
            System.out.println(result);
            if (isPalindrome(result)) {
                return true;
            }
        }
        for (int i = 0; i < s.length(); i++) {
            boolean found = checkAllPermutations(s.substring(0, i) + s.substring(i + 1), result + s.substring(i, i + 1));
            if (found) {
                return true;
            }
        }
        return false;
    }

//    brute force all permutations
    private void permute(String s, int start, int end) {
        if (start == end) {
            System.out.println(s);
        }
        for (int i = start; i <= end; i++) {
            s = swap(s, start, i);
            permute(s, start + 1, end);
            s = swap(s, i, start);
        }
    }

    private String swap(String s, int i, int j) {
        char[] chars = s.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }

    private boolean isPalindrome(String s) {
        if (s.length() == 1 || s.isEmpty()) {
            return true;
        }
        String first = s.substring(0, 1);
        String last = s.substring(s.length() - 1);
        if (first.equals(" ")) {
            return isPalindrome(s.substring(1));
        }
        if (last.equals(" ")) {
            return isPalindrome(s.substring(0, s.length() - 1));
        }
        return first.equalsIgnoreCase(last)
                && isPalindrome(s.substring(1, s.length() - 1));
    }

//    solution with a hash map
    private boolean hasPalindromePermutation(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int letters = 0;
        for (char c : s.toCharArray()) {
            if (c != ' ') {
                map.compute(Character.toLowerCase(c), (character, count) -> count == null ? 1 : ++count);
                letters++;
            }
        }
        int tries = letters % 2 == 0 ? 0 : 1;
        for (Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                if (tries == 0) {
                    return false;
                } else {
                    tries--;
                }
            }
        }
        printPalindrome(map, s);
        return true;
    }

    private void printPalindrome(Map<Character, Integer> map, String s) {
        char[] chars = s.toCharArray();
        Character lastLetter = null;
        int i = 0;
        int j = s.length() - 1;
        for (Entry<Character, Integer> entry : map.entrySet()) {
            Character letter = entry.getKey();
            Integer count = entry.getValue();
            if (count % 2 != 0) {
                lastLetter = letter;
                count--;
            }
            for (int k = 0; k < count / 2; k++) {
                if (chars[i] == ' ') {
                    i++;
                }
                if (chars[j] == ' ') {
                    j--;
                }
                chars[i] = letter;
                chars[j] = letter;
                i++;
                j--;
            }
        }
        if (lastLetter != null) {
            chars[i] = lastLetter;
        }
        System.out.println(new String(chars));
    }

//    1.5
    @Test
    void oneAway() {
        assertTrue(isOneAway("pale", "ple"));
        assertFalse(isOneAway("pale", "epl"));
        assertTrue(isOneAway("pales", "pale"));
        assertTrue(isOneAway("pale", "bale"));
        assertFalse(isOneAway("pale", "lape"));
        assertTrue(isOneAway("pale", "spale"));
        assertFalse(isOneAway("pale", "saple"));
        assertTrue(isOneAway("spale", "pale"));
        assertTrue(isOneAway("pale", "pale"));
        assertFalse(isOneAway("ppale", "palep"));
        assertFalse(isOneAway("pale", "bake"));
        assertFalse(isOneAway("pale", "pables"));
    }

    private boolean isOneAway(String s1, String s2) {
        if (Math.abs(s1.length() - s2.length()) > 1) {
            return false;
        }
        int tries = 1;
        for (int i = 0, j = 0; i < s1.length() && j < s2.length(); i++, j++) {
            if (s1.charAt(i) != s2.charAt(j)) {
                if (tries == 0) {
                    return false;
                } else {
                    tries--;
                    if (s1.length() > s2.length()) {
                        j--;
                    } else if (s1.length() < s2.length()) {
                        i--;
                    }
                }
            }
        }
        return true;
    }

//    1.6
    @Test
    void stringCompression() {
        assertEquals("a2b1c5a3", compress("aabcccccaaa"));
        assertEquals("abc", compress("abc"));
    }

    private String compress(String s) {
        if (s.length() == 0 || s.length() == 1) {
            return s;
        }
        char[] chars = new char[s.length()];
        char prev = s.charAt(0);
        int count = 1;
        int index = 0;
        for (int i = 1; i < s.length(); i++) {
            if (index + 1 > s.length() - 1) {
                return s;
            }
            if (s.charAt(i) == prev) {
                count++;
            }
            if (s.charAt(i) != prev || i == s.length() - 1) {
                chars[index] = prev;
                chars[index + 1] = Character.forDigit(count, 10);
                prev = s.charAt(i);
                count = 1;
                index += 2;
            }
        }
        return new String(chars, 0, index);
    }

//    1.7
    @Test
    void rotateMatrix() {
        int[][] data =
                {
                        {5, 3, 0},
                        {4, 2, 1},
                        {7, 3, 8}
                };
        int[][] result =
                {
                        {7, 4, 5},
                        {3, 2, 3},
                        {8, 1, 0}
                };
        int[][] data2 =
                {
                        {5, 3, 0, 3},
                        {4, 2, 1, 2},
                        {7, 3, 8, 6},
                        {9, 5, 1, 7}
                };
        int[][] result2 =
                {
                        {9, 7, 4, 5},
                        {5, 3, 2, 3},
                        {1, 8, 1, 0},
                        {7, 6, 2, 3}
                };
//        assertArrayEquals(result, doRotateMatrix2(data));
        assertArrayEquals(result2, doRotateMatrix2(data2));
    }

    private int[][] doRotateMatrix(int[][] data) {
        int[][] result = new int[data.length][data.length];
        for (int j = 0; j < data.length; j++) {
            for (int i = data.length - 1; i >= 0 ; i--) {
                result[j][(data.length - 1) - i] = data[i][j];
            }
        }
        return result;
    }

    private int[][] doRotateMatrix2(int[][] data) {
        int n = data.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                int top = data[first][i];
                data[first][i] = data[last - offset][first];
                data[last - offset][first] = data[last][last - offset];
                data[last][last - offset] = data[i][last];
                data[i][last] = top;
            }
        }
        return data;
    }

//    1.8
    @Test
    void zeroMatrix() {
        int[][] data =
                {
                        {5, 3, 0, 2},
                        {4, 2, 1, 3},
                        {7, 3, 8, 9}
                };
        int[][] result =
                {
                        {0, 0, 0, 0},
                        {4, 2, 0, 3},
                        {7, 3, 0, 9}
                };
        int[][] data2 =
                {
                        {5, 3, 0},
                        {4, 2, 1},
                        {7, 3, 8},
                        {9, 5, 1}
                };
        int[][] result2 =
                {
                        {0, 0, 0},
                        {4, 2, 0},
                        {7, 3, 0},
                        {9, 5, 0}
                };
        int[][] data3 =
                {
                        {5, 3, 0, 2},
                        {4, 0, 1, 3},
                        {7, 3, 8, 9}
                };
        int[][] result3 =
                {
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {7, 0, 0, 9}
                };
        assertArrayEquals(result, doZeroMatrix(data));
        assertArrayEquals(result2, doZeroMatrix(data2));
        assertArrayEquals(result3, doZeroMatrix(data3));
    }

    private int[][] doZeroMatrix(int[][] data) {
        Set<Integer> rows = new HashSet<>();
        Set<Integer> columns = new HashSet<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    rows.add(i);
                    columns.add(j);
                }
            }
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (rows.contains(i) || columns.contains(j)) {
                    data[i][j] = 0;
                }
            }
        }
        return data;
    }

    private int[][] doZeroMatrix2(int[][] data) {
//        at first check first row and first column for initial zeros
//        use first row and first column for zeros navigation
//        then fill based on navigation
//        then fill based on initial
        return data;
    }

//    1.9
    @Test
    void stringRotation() {
        assertTrue(isStringRotation("erbottlewat", "waterbottle"));
        assertTrue(isStringRotation("ttlewaterbo", "waterbottle"));
        assertTrue(isStringRotation("tlewaterbot", "waterbottle"));
        assertFalse(isStringRotation("tlewaterlbot", "waterbottle"));
    }

    private boolean isStringRotation(String s1, String s2) {
        String s3 = s1.concat(s1);
        return s3.contains(s2);
    }
}
