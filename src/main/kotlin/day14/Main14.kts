import java.io.File

val lines = File("day14/input").readLines()
val rules = lines.drop(2).associate { val parts = it.split(" -> "); Pair(parts[0], parts[1]) }
var polymer_counts = lines.first().windowed(2, 1).groupBy { it }.mapValues { it.value.size.toLong() }

fun update_polymer_counts(counts: Map<String, Long>): Map<String, Long> =
    counts.flatMap { entry -> // for each pair AB with insertion C, return pairs AC and CB
        when (val output = rules[entry.key]) {
            null -> listOf(Pair(entry.key, entry.value))
            else -> listOf(
                Pair(entry.key[0].toString() + output.toString(), entry.value),
                Pair(output.toString() + entry.key[1].toString(), entry.value)
            )
        }
    }.groupBy { it.first }.mapValues { entries -> entries.value.sumOf { entry -> entry.second } } // aggregate pairs

fun letter_diff(polymer_counts: Map<String, Long>): Long {
    val letter_counts = polymer_counts
        .flatMap { entry -> listOf(Pair(entry.key[0], entry.value), Pair(entry.key[1], entry.value)) }
        .groupBy { it.first }.mapValues { entries -> entries.value.sumOf { entry -> entry.second } }
        .toMutableMap()
    letter_counts[lines[0].first()] = letter_counts[lines[0].first()]!! + 1
    letter_counts[lines[0].last()] = letter_counts[lines[0].last()]!! + 1
    return letter_counts.maxOf { it.value / 2 } - letter_counts.minOf { it.value / 2 }
}

for (i in 1..10)
    polymer_counts = update_polymer_counts(polymer_counts)
println(letter_diff(polymer_counts))

for (i in 1..30)
    polymer_counts = update_polymer_counts(polymer_counts)
println(letter_diff(polymer_counts))