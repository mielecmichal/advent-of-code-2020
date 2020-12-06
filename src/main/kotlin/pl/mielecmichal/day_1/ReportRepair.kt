package pl.mielecmichal.day_1

import pl.mielecmichal.utilities.Resource

fun main() {
    val reportText = Resource("day_1_input").readLines()
    val processor = ReportRepairSolver(reportText)
    println(processor.findTwoCompanions(2020))
    println(processor.findThreeCompanions(2020))
}

class ReportRepairSolver(reportLines: List<String>) {

    private val report : List<Long> = reportLines.map { it.toLong() }

    fun findThreeCompanions(sum : Long) : Long? {
        report.forEach{
            val companions = findTwoCompanions(sum - it)
            if(companions != null){
                return companions * it
            }
        }
        return null
    }

    fun findTwoCompanions(sum : Long) : Long? {
        val companions = mapCompanions(sum)
        report.forEach{
            if(companions.containsKey(it)){
                val companionIndex = companions.getValue(it)
                return it * report[companionIndex]
            }
        }
        return null
    }

    private fun mapCompanions(sum : Long): Map<Long, Int> {
       return report
               .mapIndexed{index, number -> Pair(sum - number, index)}
               .toMap()
    }
}