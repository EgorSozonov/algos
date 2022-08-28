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
                            subSum += prefixSums[i -1][j - 1]
                        }
                    }
                    if (j > 0) {
                        subSum -= prefixSums[k][j - 1]
                    }
                    if (subSum == requiredSum) {
                        return requiredSum
                    } // [1, 1] [11, 2]
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
    val inputs = arrayOf(
        Triple(arrayOf(intArrayOf(27,5,-20,-9,1,26,1,12,7,-4,8,7,-1,5,8),
            intArrayOf(16,28,8,3,16,28,-10,-7,-5,-13,7,9,20,-9,26),
            intArrayOf(24,-14,20,23,25,-16,-15,8,8,-6,-14,-6,12,-19,-13),
            intArrayOf(28,13,-17,20,-3,-18,12,5,1,25,25,-14,22,17,12),
            intArrayOf(7,29,-12,5,-5,26,-5,10,-5,24,-9,-19,20,0,18),
            intArrayOf(-7,-11,-8,12,19,18,-15,17,7,-1,-11,-10,-1,25,17),
            intArrayOf(-3,-20,-20,-7,14,-12,22,1,-9,11,14,-16,-5,-12,14),
            intArrayOf(-20,-4,-17,3,3,-18,22,-13,-1,16,-11,29,17,-2,22),
            intArrayOf(23,-15,24,26,28,-13,10,18,-6,29,27,-19,-19,-8,0),
            intArrayOf(5,9,23,11,-4,-20,18,29,-6,-4,-11,21,-6,24,12),
            intArrayOf(13,16,0,-20,22,21,26,-3,15,14,26,17,19,20,-5),
            intArrayOf(15,1,22,-6,1,-9,0,21,12,27,5,8,8,18,-1),
            intArrayOf(15,29,13,6,-11,7,-6,27,22,18,22,-3,-9,20,14),
            intArrayOf(26,-6,12,-10,0,26,10,1,11,-10,-16,-18,29,8,-8),
            intArrayOf(-19,14,15,18,-10,24,-9,-7,-19,-14,23,23,17,-5,6)
        ), -100, -101),
        Triple(arrayOf( intArrayOf(1, 0, 1), intArrayOf(0, -2, 3) ), 2, 2),
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