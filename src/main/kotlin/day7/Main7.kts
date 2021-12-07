import java.io.File
import kotlin.math.abs

fun L1_score(median: Int, crabs: List<Int>) = crabs.sumOf { crab -> abs(crab - median) }
fun binomial_score(median: Int, crabs: List<Int>) = crabs.sumOf { crab -> val n = abs(crab - median) + 1; (n*n-n)/2 }

val crabs = File("day7/input").readText().trimEnd().split(",").map { it.toInt() }.sorted()
// The median minimizes the L1 norm.
println(listOf(crabs[crabs.size / 2], crabs[crabs.size / 2 + 1]).minOf { L1_score(it, crabs) })
println((crabs.minOrNull()!! until crabs.maxOrNull()!!).minOf { binomial_score(it, crabs) })
