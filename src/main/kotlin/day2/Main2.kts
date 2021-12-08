import java.io.File

data class Position(val x: Int, val y: Int, val aim: Int)

val instructions = File("day2/input").readLines().map {
    val parts = it.split(" ")
    Pair(parts[0], parts[1].toInt())
}
val pos1 = instructions
    .fold(Position(0, 0, 0)) { pos, instr -> when (instr.first) {
        "up" ->      Position(pos.x, pos.y - instr.second, 0)
        "down" ->    Position(pos.x, pos.y + instr.second, 0)
        "forward" -> Position(pos.x + instr.second, pos.y, 0)
        else -> throw RuntimeException("Unknown direction " + instr.first)
    } }
println(pos1.x * pos1.y)

val pos2 = instructions
    .fold(Position(0, 0, 0)) { pos, instr -> when (instr.first) {
        "up" ->      Position(pos.x, pos.y, pos.aim - instr.second)
        "down" ->    Position(pos.x, pos.y, pos.aim + instr.second)
        "forward" -> Position(pos.x + instr.second, pos.y + pos.aim * instr.second, pos.aim)
        else -> throw RuntimeException("Unknown direction " + instr.first)
    } }
println(pos2.x * pos2.y)