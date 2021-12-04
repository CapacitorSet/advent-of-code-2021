import java.io.File

data class Board(val numbers: List<List<Int>>) {
    fun check(marked_numbers: List<Int>) =
        (numbers + numbers.indices.map { idx -> numbers.map { col -> col[idx] } })
            .any { col_or_row -> marked_numbers.containsAll(col_or_row) }
    fun score(marked_numbers: List<Int>) =
        (numbers.flatten().toSet() - marked_numbers.toSet()).sum() * marked_numbers.last()
}

val lines = File("day4/input").readLines()
val numbers = lines[0].split(",").map {it.toInt()}
var boards = lines.asSequence().drop(1)
    .map { it.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }
    .filter { it.isNotEmpty() }
    .windowed(5, 5, false)
    .map { lines -> Board(lines) }.toList()

data class Outcome(val marked: List<Int>, val success: List<Board>, val fail: List<Board>)
val outcomes = numbers
    .scan<Int, List<Int>>(emptyList()) { a, b -> a + b }
    .map { marked -> Outcome(marked, boards.filter { it.check(marked) }, boards.filterNot { it.check(marked) }) }

val p1_winner = outcomes.find { it.success.size == 1 }!!
println(p1_winner.success[0].score(p1_winner.marked))
val p2_winner = outcomes.findLast { it.fail.size == 1 }!!.fail[0]
val p2_numbers = outcomes.find { it.fail.isEmpty() }!!.marked
println(p2_winner.score(p2_numbers))