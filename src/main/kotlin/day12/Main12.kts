import java.io.File

val neighbors = File("day12/input").readLines().flatMap {
    val parts = it.split("-");
    listOf(Pair(parts[0], parts[1]), Pair(parts[1], parts[0]))
}.filterNot { (it.first == "end") || (it.second == "start") }.groupBy({ it.first }) { it.second }.toMap()

fun dfs(moves: List<String>, is_part2: Boolean): Int =
    if (moves.last() == "end")
        1
    else
        neighbors[moves.last()]!!
            .filter {
                if (it[0].isUpperCase())
                    true
                else {
                    val occurrences = (moves + it).filter { it[0].isLowerCase() } // small caves
                        .groupBy { it }.map { it.value.size } // number of occurrences
                        .sortedDescending().take(2) // the two highest counts
                    // The highest occurrence count is 2 for part 2, 1 for part 1; and the next highest is 1.
                    (occurrences[0] <= if (is_part2) 2 else 1) && (occurrences[1] == 1)
                }
            }
            .sumOf { dfs(moves + it, is_part2) }

println(dfs(listOf("start"), false))
println(dfs(listOf("start"), true))