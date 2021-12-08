import java.io.File

fun solveFor(numbers: List<Int>, offset: Int): Int =
    numbers.indices
        .take(numbers.size - offset)
        .count { i -> numbers[i+offset] > numbers[i] }

fun main(args: Array<String>) {
    val numbers = File("day1/input").readLines().map { it.toInt() }
    println(solveFor(numbers, 1))
    println(solveFor(numbers, 3))
}