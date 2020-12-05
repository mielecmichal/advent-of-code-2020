package pl.mielecmichal.day_5

import pl.mielecmichal.utilities.Resource
import kotlin.math.pow

fun main(){
    val seatIds = Resource("day_5_input").readLines()
            .map { BoardingPass(it) }
            .map { (it.row * 8) + it.column }

    val min = seatIds.minOrNull()!!
    val max = seatIds.maxOrNull()!!
    val actualSum = seatIds.sum()
    val expectedSum = ((min + max) / 2.0) * (max - min + 1)
    val missingSeat = expectedSum - actualSum
    println(max)
    println(missingSeat.toLong())
}

class BoardingPass(letters : String) {
    val row = partition(letters.substring(0, 7).toCharArray())
    val column = partition(letters.substring(7, 10).toCharArray())

    private fun partition(coordinates : CharArray) : Long {
        var range = Pair(0L, 2.0.pow(coordinates.size).toLong() - 1)
        for(h in coordinates){
            range = when(h){
                'F', 'L' -> lowerHalf(range)
                'B', 'R' -> upperHalf(range)
                else -> throw IllegalStateException()
            }
        }
        return range.second
    }

    private fun upperHalf(pair : Pair<Long, Long>) = Pair(middle(pair), pair.second)
    private fun lowerHalf(pair : Pair<Long, Long>) = Pair(pair.first, middle(pair))
    private fun middle(pair : Pair<Long, Long>) = (pair.second + pair.first) / 2
}