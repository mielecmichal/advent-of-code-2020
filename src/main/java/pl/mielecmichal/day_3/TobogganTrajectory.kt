package pl.mielecmichal.day_3

import pl.mielecmichal.utilities.Resource
import java.util.stream.Stream

fun main() {
    val topologyArray = Resource("day_3_input").readLines()
            .map { it.toCharArray() }
            .toTypedArray()

    val topology = Topology(topologyArray)
    val first = topology.countTrees(3, 1)
    println(first)

    val second = listOf(
            topology.countTrees(1, 1),
            first,
            topology.countTrees(5, 1),
            topology.countTrees(7, 1),
            topology.countTrees(1, 2),
    ).reduceRight { a, b -> a * b }
    println(second);
}

class Topology(private val topology: Array<CharArray>) {

    private val height = topology.size
    private val width = topology[0].size

    fun countTrees(stepX: Int, stepY: Int): Long {
        val start = Pair(stepX, stepY)
        return Stream.iterate(start) { Pair(stepX + it.first, stepY + it.second) }
                .takeWhile{it.second < height}
                .map { topology[it.second][it.first % width] }
                .filter { it == '#' }
                .count()
    }
}