package pl.mielecmichal.day_2

import pl.mielecmichal.utilities.Resource
import java.util.function.BiPredicate

fun main(){
    val entries = Resource("day_2_input")
            .readLines()
            .map { PasswordEntry(it) }

    println(entries.filter { it.isCorrect(PasswordEntry.Rule.RULE_ONE) }.count())
    println(entries.filter { it.isCorrect(PasswordEntry.Rule.RULE_TWO) }.count())
}

class PasswordEntry(text : String) {
    private val password : String
    private val policy : PasswordPolicy

    init{
        val split = text.split(": ")
        val policy = split[0]
        this.password = split[1]
        this.policy = PasswordPolicy(policy)
    }

    fun isCorrect(rule : Rule) : Boolean {
        return rule.predicate.test(policy,password);
    }

    enum class Rule ( val predicate : BiPredicate<PasswordPolicy, String>){
        RULE_ONE(PasswordPolicy::checkRuleOne),
        RULE_TWO(PasswordPolicy::checkRuleTwo)
    }
}

data class PasswordPolicy(val text : String) {

    private val letter : Char
    private val minOccurrences : Int
    private val maxOccurrences : Int

    init{
        val bySpace = text.split(" ")
        val occurrences = bySpace[0]
        val byDash = occurrences.split("-")

        this.letter = bySpace[1].toCharArray()[0]
        this.minOccurrences = byDash[0].toInt()
        this.maxOccurrences = byDash[1].toInt()
    }

    fun checkRuleOne(password: String): Boolean {
        val occurrences = password.toCharArray()
                .filter { it == letter }
                .count()

        return IntRange(minOccurrences, maxOccurrences).contains(occurrences)
    }

    fun checkRuleTwo(password: String): Boolean {
        val firstPosition  = password[minOccurrences - 1]
        val secondPosition  = password[maxOccurrences - 1]
        return listOf(firstPosition, secondPosition)
                .filter { it == letter }
                .count() == 1
    }


}