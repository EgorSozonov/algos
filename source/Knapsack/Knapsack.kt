package tech.sozonov.algos.Knapsack;
import java.lang.Integer.max


/**
 * Returns [total value, total weight, list of included items]ю
 * Memory estimate: maxWeight * (1 + countItems)
 * CPU estimate: maxWeight * countItems
 */
fun knapsack(input: Pair<IntArray, IntArray>, maxWeight: Int): Triple<Int, Int, List<Int>> {
    val inputValues = input.first
    val inputWeights = input.second
    if (inputWeights.isEmpty()) throw IllegalArgumentException("Input array is empty!")
    if (maxWeight <= 0) throw IllegalArgumentException("Max weight must be positive!")
    if (inputWeights.size != inputValues.size) throw IllegalArgumentException("Input arrays have different sizes!")

    val memoTable = IntArray(maxWeight + 1)
    val itemInclusion = ByteArray(inputWeights.size*(maxWeight + 1))
    val numItems = inputValues.size

    var i = 0
    while (i < inputWeights.size) {
        for (w in maxWeight downTo 1) {
            var ifInclude: Int
            val ifExclude = memoTable[w]
            if (inputWeights[i] < w) {
                ifInclude = memoTable[w - inputWeights[i]] + inputValues[i]
                memoTable[w] = max(ifInclude, ifExclude)
                if (ifInclude > ifExclude) {
                    itemInclusion.copyInto(itemInclusion, numItems*w, numItems*(w - inputWeights[i]), numItems*(w - inputWeights[i] + 1))
                    itemInclusion[numItems*w + i] = 1
                }
            }
        }
        i++
    }
    val resultValue: Int = memoTable[maxWeight]
    var resultWeight = 0
    val includedItems = mutableListOf<Int>()
    for (j in inputWeights.indices) {
        if (itemInclusion[numItems*maxWeight + j] > 0) {
            includedItems.add(j)
            resultWeight += inputWeights[j]
        }
    }
    return Triple(resultValue, resultWeight, includedItems)
}

fun knapsackRunner() {
    val knapResult = knapsack(Pair(intArrayOf(7, 8, 4), intArrayOf(3, 8, 6)), 10)

    println("Result of knapsack for is: value ${knapResult.first}, weight ${knapResult.second}, included items: ${knapResult.third.joinToString(", ")}")

    val knapResult2 = knapsack(Pair(intArrayOf(14, 5, 5, 5, 4), intArrayOf(8, 3, 3, 3, 2)), 10)
    println("Second result of knapsack is: value ${knapResult2.first}, weight ${knapResult2.second}, included items: ${knapResult2.third.joinToString(", ")}")
}