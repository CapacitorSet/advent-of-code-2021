import java.io.File

val lines = File("day13/input").readLines()
var points = lines.filter { ',' in it }
    .map { val parts = it.split(",").map { it.toInt() }; Pair(parts[0], parts[1]) }
val folds = lines.filter { '=' in it }.map { Pair('x' in it, it.split("=").last().toInt()) }

folds.forEach { fold ->
    val x_size = points.maxOf { it.first }
    val y_size = points.maxOf { it.second }
    points = points.map {
        if (fold.first)
            Pair(if (it.first > fold.second) x_size - it.first else it.first, it.second)
        else
            Pair(it.first, if (it.second > fold.second) y_size - it.second else it.second)
    }.toSet().toList()

    if (fold == folds.first())
        println(points.size)
    if (fold == folds.last()) {
        for (y in 0..y_size) {
            for (x in 0..x_size) {
                print(if (Pair(x, y) in points) '#' else ' ')
            }
            println()
        }
    }
}