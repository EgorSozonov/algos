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
    val cntRows = s.length
    val cntCols = p.length
    if (cntCols == 0) return (cntRows == 0)
    //memotable[i][j] is true iff first i characters in given string match the first j characters of pattern.
    val memoTable: Array<BooleanArray> = Array(cntRows + 1) { BooleanArray(cntCols + 1) }

    val input = s.toCharArray()
    val pattern = p.toCharArray()


    // empty pattern can match with empty string
    memoTable[0][0] = true

    // Only '*' can match with empty string
    for (j in 1..cntCols) {
        if (pattern[j - 1] == '*') {
            memoTable[0][j] = memoTable[0][j - 1]
        }
    }

    for (i in 1 .. cntRows) {
        for (j in 1 .. cntCols) {
            if (pattern[j - 1] == '*') {
                memoTable[i][j] = memoTable[i][j - 1] || memoTable[i - 1][j]
            } else if (pattern[j - 1] == input[i - 1] || pattern[j - 1] == '?') {
                memoTable[i][j] = memoTable[i - 1][j - 1]
            } else {
                memoTable[i][j] = false
            }
        }
    }
    return memoTable[cntRows][cntCols]
}

fun runner() {
    val inputs = arrayOf(
                    Triple("aa", "a", false),
                    Triple("aa", "*", true),
                    Triple("cb", "?a", false),
                    Triple("baaabab", "*****ba*****ab", true),
                 )
    var cntCorrect = 0
    for (input in inputs) {
        val result = solution(input.first, input.second)
        if (result != input.third) {
            println("Error for input ${input.first}, pattern ${input.second}: got $result, expected ${input.third}")
        } else {
            cntCorrect += 1
        }
    }
    if (cntCorrect == inputs.size) {
        println("Wildcard matching: all results are correct")
    }
}


}