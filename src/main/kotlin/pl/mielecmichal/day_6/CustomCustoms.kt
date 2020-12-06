package pl.mielecmichal.day_6

import pl.mielecmichal.utilities.Resource

fun main(){
    val passportsText = Resource("day_6_input").readText();
    passportsText
            .split("\n\n")
            .asSequence()
            .map { it.replace("\n", " ").trim() }
            .map { it.split(" ") }
            .map { Group(it)}
            .map { it.count2() }
            .sum()
            .also { println(it) }


}

class Group(private val peopleAnswers : List<String>) {

    fun count() : Long {
        val counts = mutableMapOf<Char, Long>()

        peopleAnswers.flatMap { it.toCharArray().toList() }
                .forEach {
                    counts[it] = 1
                }

        return counts.values.sum()
    }

    fun count2() : Int {
        val counts = mutableMapOf<Char, Long>()

        peopleAnswers.flatMap { it.toCharArray().toList() }
                .forEach {
                    counts[it] = counts.getOrDefault(it, 0) + 1
                }

        return counts.filter { it.value == peopleAnswers.size.toLong() }.count()
    }

}