/**
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * E.g.
 *
 * Input: s = "pwwkew"
 *
 * Output: 3
 *
 * Explanation: The answer is "wke", with the length of 3.
 *
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Challenge definition taken from LeetCode.
 */
public class Main
{

  /**
   * The problem could be solved with Map, Set or an array but let's first try to solve it without the usage of any data structure.
   *
   * To do so, for each element we visit we could check if such occurs among the elements on its left side.
   *
   * 1. If not -> we proceed with the elements on the right and updating the longest sequence if the current one exceeds it.
   * 2. If duplicate exists at position x, for each subsequent check we only need to visit elements until x + 1.
   *
   * To explain the uniqueness integrity as we visit elements, let's imagine the following scenario:
   *
   * E.g. Input: abcdebfg
   *
   * Starting from the 0th index, it'll look like:
   *
   * [0] = a (no characters on the left -> unique)
   * [1] = b (a on the left -> a != b -> unique)
   * [2] = c (a,b => unique)
   * ...
   * [5] = b (e,d,c,<b>b</b>,a -> not unique)
   *
   * When we encounter a duplicate on left, we should not visit the elements starting from 0 up to duplicate position anymore to guarantee we'll only pass
   * through the unique sequence which could turn out to be the longest.
   *
   * Getting back to [5] - we resolve it with cutting down <b>ab</b> subset from abcdebfg and for all further checks we'll only work with cdebfg.
   *
   * Ultimately, we're keeping a subset of unique elements and updating the longest sequence if its size exceeds it. When a duplicate is found
   * we find its rightmost position on the left side of the currently visited element and cut the subset before that position (including it).
   *
   * We're narrowing the scope of checked elements as we go, similar to what we do in (link to binary search).
   *
   * To explain the time complexity against the worst case, let's use the input: abcdefgh (all unique characters)
   *
   * E.g. if we are at n=5 we'll traverse through the elements at positions 0,1,2,3,4. Starting from the leftmost element towards the rightmost,
   * the process looks like:
   *
   * 1. 0 (chars on the left)
   * 2. 1
   * 3. 2
   * 4. 3
   * 5. 4
   * 6. 5
   * 7. 6
   * 8. 7
   *
   * we see that it results to the summation formula: n(n+1)/2 = 1+2+3+4+5+6+7 (n=7) = 28. By removing the constants and non-dominant terms
   * we achieve a runtime of n^2.
   *
   * Time Complexity: O(n^2)
   * Space Complexity: O(1)
   */
  public static int findV1(String input) {
    if (input == null || input.isEmpty()) {
      return 0;
    }

    int longest = 1;
    int stop = 0;

    for (int i = 1; i < input.length(); i++) {
      int j = i - 1;

      while (j >= stop && input.charAt(i) != input.charAt(j)) {
        j--;
      }

      if (j >= 0 && input.charAt(i) == input.charAt(j)) {
        stop = j + 1;
      }

      if ((i - stop + 1) > longest) {
        longest = (i - stop + 1);
      }
    }

    return longest;
  }

  /***
   * Stepping upon the solution above, we could present an array with as many slots as there are characters in the
   * ASCII. (We will start from ' ' = 32 (the beginning of the symbols) and going upwards until we cover uppercase, lowercase letters and digits) which
   * results in a working set of 96 elements and that's the size we need. Thanks to the hashing function <b>getPos(..)</b>, we're able to create an accurate mapping.
   *
   * With the following approach we could achieve the BCR of O(n) and space complexity of O(1).
   *
   * Because of the data structure usage, now we don't need to visit the previous elements at all because we store them in the array as we go.
   * We'll initialize a counter variable and increment it by 1 for each unique element. When a duplicate is encountered, we subtract the difference
   * between 0 and the <b>latest</b> duplicate position (including), which acts as the lightweight equivalent of cutting the subset into a unique one.
   *
   * Time Complexity: O(n)
   * Space Complexity: O(1)
   */
  public static int findV2(String input) {
    if (input == null || input.isEmpty()) {
      return 0;
    }

    int longest = 1;
    int skip = 0;

    int[] table = new int[96];

    for (int i = 0; i < input.length(); i++) {
      int charPos = getPos(input.charAt(i));

      // do not update if the latest
      // duplicate position is <= skip
      if (table[charPos] > skip) {
        skip = table[charPos];
      }

      // in case of a duplicate
      // always update with the last
      // occurrence of it
      table[charPos] = i + 1;

      if (i - skip + 1 > longest) {
        longest = i - skip + 1;
      }
    }

    return longest;
  }

  /**
   * ' ' is at 32nd position in the ASCII table, which denotes the beginning of the symbols that we are supposed to work with.
   * Everything before that character down to 0 is not out concern.
   */
  private static int getPos(char c) {
    return c - ' ';
  }

}
