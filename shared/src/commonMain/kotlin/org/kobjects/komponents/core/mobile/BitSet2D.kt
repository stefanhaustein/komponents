package org.kobjects.komponents.core.mobile

class BitSet2D {
    private val data = mutableListOf<MutableList<Boolean>>()

    fun set(row: Int, col: Int, value: Boolean) {
        while (data.size <= row) {
            data.add(mutableListOf())
        }
        val bitRow = data[row]
        while (bitRow.size <= col) {
            bitRow.add(false)
        }
        bitRow.set(col, value)
    }

    fun get(row: Int, col: Int): Boolean {
        if (row >= data.size) {
            return false
        }
        val bitRow = data[row]
        return if (col < bitRow.size) bitRow[col] else false
    }

}