package pl.mielecmichal.day_4

import pl.mielecmichal.utilities.Resource
import java.util.function.Predicate

fun main() {
    val passportsText = Resource("day_4_input").readText();
    val passports = passportsText
            .split("\n\n")
            .asSequence()
            .map { it.replace("\n", " ").trim() }
            .map { Passport(it) }

    passports.filter { it.hasValidKeys() }
            .count()
            .also { println(it) }

    passports.filter { it.hasValidKeys() }
            .filter { it.hasValidValues() }
            .count()
            .also { println(it) }
}

class Passport(passportLine: String) {
    private val fields: Map<String, String>
    private val validators: Map<String, Predicate<String>> = mapOf(
            Pair("byr", Predicate { LongRange(1920, 2002).contains(it.toLong()) }),
            Pair("iyr", Predicate { LongRange(2010, 2020).contains(it.toLong()) }),
            Pair("eyr", Predicate { LongRange(2020, 2030).contains(it.toLong()) }),
            Pair("hgt", Predicate { when(it.takeLast(2)){
                "cm" ->  LongRange(150, 193).contains(it.split("cm")[0].toLong())
                "in" ->  LongRange(59, 76).contains(it.split("in")[0].toLong())
                else -> false
            } }),
            Pair("hcl", Predicate {it.matches(Regex("^#([a-f0-9]{6})$"))}),
            Pair("ecl", Predicate { setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it) }),
            Pair("pid", Predicate {it.matches(Regex("^([0-9]{9})$"))}),
            Pair("cid", Predicate {true}),
            )

    init {
        fields = parsePassport(passportLine)
    }

    fun hasValidKeys(): Boolean {
        return fields.keys.containsAll(setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))
    }

    fun hasValidValues(): Boolean {
        return fields.entries
                .filterNot { validators[it.key]?.test(it.value) ?: true }
                .count() == 0
    }

    private fun parsePassport(passportLine: String): Map<String, String> {
        val keyValues = passportLine.split(' ')
        return keyValues
                .map { it.split(':') }
                .map { Pair(it[0], it[1]) }
                .toMap()
    }
}