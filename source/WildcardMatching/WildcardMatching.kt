package tech.sozonov.algos.WildcardMatching

/**
 *
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:

 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).

 * The matching should cover the entire input string (not partial).
 *
 * Constraints:
 * 0 <= s.length, p.length <= 2000
 * s contains only lowercase English letters.
 * p contains only lowercase English letters, '?' or '*'.
 *
 *
 * Example 1:
 * Input: s = "aa", p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".

 * Example 2:
 * Input: s = "aa", p = "*"
 * Output: true
 * Explanation: '*' matches any sequence.

 * Example 3:
 * Input: s = "cb", p = "?a"
 * Output: false
 * Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 */
object WildcardMatching {


fun solution(s: String, p: String): Boolean {
    //Let T[i][j] is true if first i characters in given string matches the first j characters of pattern.
    val memoTable: Array<ByteArray> = arrayOf(s.length) { byteArrayOf(p.length) }
}


}