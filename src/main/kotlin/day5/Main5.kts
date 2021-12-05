import java.io.File

class Point(val x: Int, val y: Int)
class Line(val from: Point, val to: Point)

fun toward(from: Int, to: Int): IntProgression {
    val step = if (from > to) -1 else 1
    return IntProgression.fromClosedRange(from, to, step)
}

val lines = File("day5/input").readLines()
val vents = lines.map {
    val parts = it.split(" -> ")
    val from = parts[0].split(",").map { it.toInt() }
    val to = parts[1].split(",").map { it.toInt() }
    Line(Point(from[0], from[1]), Point(to[0], to[1]))
}

val size = vents.maxOf { maxOf(it.from.x, it.from.y, it.to.x, it.to.y) }+1
val grid = MutableList(size) { MutableList(size) { 0 } }

// Divide into straight ones (part 1) and diagonal ones (part 2)
val vent_partition = vents.partition { (it.from.x == it.to.x) or (it.from.y == it.to.y) }
vent_partition.first.forEach {
    for (x in toward(it.from.x, it.to.x))
        for (y in toward(it.from.y, it.to.y))
            grid[x][y] += 1
}
println(grid.flatten().count { it > 1 })
vent_partition.second.forEach {
    toward(it.from.x, it.to.x).zip(toward(it.from.y, it.to.y)).forEach { (x, y) -> grid[x][y] += 1 }
}
println(grid.flatten().count { it > 1 })