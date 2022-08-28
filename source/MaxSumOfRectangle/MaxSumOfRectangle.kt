package tech.sozonov.algos.MaxSumOfRectangle

import java.lang.StringBuilder

/**
 * Leetcode: 363. Max Sum of Rectangle No Larger Than K
 *
 * Given an m x n matrix and an integer k, return the max sum of a rectangle in the matrix such that its sum is no larger than k.
 * It is guaranteed that there will be a rectangle with a sum no larger than k.
 *
 * Constraints:

 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -100 <= matrix[i][j] <= 100
 * -105 <= k <= 105
 * Follow up: What if the number of rows is much larger than the number of columns?
 *
 *
 * Example A
 * Input: matrix = [ [1, 0, 1], [0, -2, 3] ], k = 2
 * Output: 2
 * Explanation: Because the sum of the blue rectangle [[0, 1], [-2, 3]] is 2, and 2 is the max number no larger than k (k = 2).
 *
 * Example B
 * Input: matrix = [ [2, 2, -1] ], k = 3
 * Output: 3
 */
object MaxSumOfRectangle {


/**
 * First we calculate prefix sums pr[i, j]: sums of submatrices from [0, 0] to [i, j]. This is O(R*C).
 * Then, for any sum of a submatrix from [i, j] to [k, l], sum equals
 * S[i, j, k, l] = pr[k, l] + pr[i - 1, j - 1]  - pr[k, j - 1] - pr[i - 1, l]
 * This memoization drives the overall asympto from O(R^3*C^3) down to O(R^2*C^2)
 */
fun solution(matrix: Array<IntArray>, requiredSum: Int): Int {
    val cntRows = matrix.size
    val cntCols = matrix[0].size
    val prefixSums = calcPrefixSums(matrix)
    var bestSum: Int = Int.MIN_VALUE

    for (i in 0 until cntRows) {
        for (j in 0 until cntCols) {
            for (k in i until cntRows) {
                for (l in j until cntCols) {
                    var subSum = prefixSums[k][l]
                    if (i > 0) {
                        subSum -= prefixSums[i - 1][l]
                        if (j > 0) {
                            subSum -= prefixSums[k][j - 1]
                        }
                    }
                    if (j > 0) {
                        subSum -= prefixSums[k][j - 1]
                    }
                    if (subSum == requiredSum) return requiredSum
                    if (subSum < requiredSum && subSum > bestSum) {
                        bestSum = subSum
                    }
                }
            }
        }
    }
    return bestSum

}

fun runner() {
    val inputs = arrayOf(Triple(arrayOf( intArrayOf(1, 0, 1), intArrayOf(0, -2, 3) ), 2, 2),
                                                     Triple(arrayOf( intArrayOf(2, 2, -1)), 3, 3),
    )
    var correctResults = 0
    for (input in inputs) {
        val result = solution(input.first, input.second)
        if (result == input.third) {
            correctResults += 1
        } else {
            println("Incorrect result in MaxSumOfRectangle: expected ${input.third} but got $result")
        }
    }
    if (correctResults == inputs.size) {
        println("All results in MaxSumOfRectangle are correct")
    }
}


/**
 * Calculates the memo table with prefix sums of the input matrix
 */
private fun calcPrefixSums(matrix: Array<IntArray>): Array<IntArray> {
    val cntRows = matrix.size
    val cntCols = matrix[0].size

    val result: Array<IntArray> = Array(cntRows) { IntArray(cntCols) }
    // First calc the top row and the left column
    result[0][0] = matrix[0][0]
    for (j in 1 until cntCols) {
        result[0][j] = result[0][j - 1] + matrix[0][j]
    }
    for (i in 1 until cntRows) {
        result[i][0] = result[i - 1][0] + matrix[i][0]
    }

    // Then calc the rest of the table according to the formula
    // res[i (j+1)] = res[i j] + res[(i - 1) (j + 1)] - res[(i - 1) j] + matrix[i (j + 1)]
    for (i in 1 until cntRows) {
        for (j in 0 until (cntCols - 1)) {
            result[i][j + 1] = result[i][j] + result[i - 1][j + 1] - result[i - 1][j] + matrix[i][j + 1]
        }
    }
    return result
}


}