package tech.sozonov.algos.LinkedList;

class LinkedList(val payload: Int, var next: LinkedList?) {


companion object {
    fun make(inp: IntArray): LinkedList {
        if (inp.isEmpty()) throw IllegalArgumentException("Array must not be empty")
        var result = LinkedList(inp[inp.size - 1], null)
        for (i in inp.size - 2 downTo 0) {
            val newItem = LinkedList(inp[i], result)
            result = newItem
        }
        return result
    }

    fun LinkedList.reverse(): LinkedList {
        var prev: LinkedList? = null
        var curr: LinkedList? = this
        var next: LinkedList? = this.next
        while (curr != null) {

            curr.next = prev
            prev = curr
            curr = next
            next = curr?.next ?: null
        }
        return prev!!
    }

    fun equality(a: LinkedList, b: LinkedList): Boolean {
        if (a.payload != b.payload) return false
        var currA = a.next
        var currB = b.next
        while (currA != null) {
            if (currB == null || currA.payload != currB.payload) return false

            currA = currA.next
            currB = currB.next
        }
        return true
    }

    fun linkedListRunner() {
        val testOneEl = LinkedList(7, null)
        val expectOneEl = LinkedList(7, null)

        val testOneResult = equality(testOneEl.reverse(), expectOneEl)
        if (!testOneResult) {
            println("Reversing one-element list has failed")
        }

        val testThreeEls = make(intArrayOf(1, 2, 3))
        val expectThreeEls = make(intArrayOf(3, 2, 1))
        val reversed = testThreeEls.reverse()
        val testThreeResult = equality(reversed, expectThreeEls)
        if (!testThreeResult) {

            println("Reversing three-element list has failed")
        }
        println("Linked list runner finished")
    }
}


}