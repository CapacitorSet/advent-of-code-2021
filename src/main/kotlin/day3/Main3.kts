import java.io.File

fun List<Boolean>.toInt() = this.joinToString("") { if (it) "1" else "0" }.toInt(2)

fun getOxygenRating(bitstring: List<Boolean>) = bitstring.count { it } * 2 >= bitstring.size
fun getCO2Rating(bitstring: List<Boolean>) = !getOxygenRating(bitstring)

// Transpose [xyzt xyzt xyzt] into [xxx yyy zzz ttt]
fun transpose(numbers: List<List<Boolean>>): List<List<Boolean>> =
    numbers
        .foldRight(List<List<Boolean>>(numbers.first().size) { emptyList() })
            { bits, transpose -> transpose.zip(bits) { a, b -> a + b } }
        .filter { it.isNotEmpty() }

val numbers: List<List<Boolean>> = File("day3/input").readLines()
    .map { it.map { char -> char == '1' } }
val transposed: List<List<Boolean>> = transpose(numbers)
val gamma = transposed.map { getOxygenRating(it) }.toInt()
val epsilon = transposed.map { getCO2Rating(it) }.toInt()
println(gamma*epsilon)

fun filterByRating(numbers: List<List<Boolean>>, rating_fn: (List<Boolean>) -> Boolean): Int {
    var candidates = numbers
    var index = 0
    while (candidates.size > 1) {
        val bit_column = transpose(candidates)[index]
        val criterion = rating_fn(bit_column)
        candidates = candidates.filter { it[index] == criterion }
        index += 1
    }
    return candidates.first().toInt()
}

val oxygen = filterByRating(numbers) { getOxygenRating(it) }
val co2 = filterByRating(numbers) { getCO2Rating(it) }
println(oxygen*co2)
