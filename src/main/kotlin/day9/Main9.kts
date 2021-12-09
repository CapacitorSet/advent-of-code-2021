import java.io.File

val rows = File("day9/input").readLines().map { it.split("").filter { it.isNotEmpty() }.map { it.toInt() } }
val x_size = rows.size
val y_size = rows[0].size

fun neighbors_of(x: Int, y: Int) =
    listOf(
        Pair(maxOf(x - 1, 0), y),
        Pair(minOf(x + 1, x_size-1), y),
        Pair(x, maxOf(y - 1, 0)),
        Pair(x, minOf(y + 1, y_size-1)),
    ).filterNot { (_row, _col) -> x == _row && y == _col }

var local_minima = rows.indices.flatMap { row -> rows[0].indices.map { col -> Pair(row, col) } }
    .filter { (rowIdx, colIdx) ->
        neighbors_of(rowIdx, colIdx).all { (_row, _col) -> rows[_row][_col] > rows[rowIdx][colIdx] }
    }
println(local_minima.sumOf { (row, col) -> rows[row][col] + 1 })

val basins = local_minima.map { point ->
    var basin = emptySet<Pair<Int, Int>>()
    var new_in_basin = listOf(point)
    while (new_in_basin.isNotEmpty()) {
        new_in_basin = new_in_basin
            .flatMap { (min_row, min_col) -> neighbors_of(min_row, min_col) }
            .filterNot { (b_row, b_col) -> rows[b_row][b_col] == 9 }
            .filterNot { basin.contains(it) } // do not count cells already in the basin
        basin = basin + new_in_basin
    }
    basin
}
println(basins.map { it.size }.sorted().takeLast(3).reduceRight { a, b -> a * b })
