import java.io.File

fun Boolean.toInt() = if (this) 1 else 0

val digits = mapOf(
    //     a  b  c  d  e  f  g
    listOf(1, 1, 1, 0, 1, 1, 1) to 0,
    listOf(0, 0, 1, 0, 0, 1, 0) to 1,
    listOf(1, 0, 1, 1, 1, 0, 1) to 2,
    listOf(1, 0, 1, 1, 0, 1, 1) to 3,
    listOf(0, 1, 1, 1, 0, 1, 0) to 4,
    listOf(1, 1, 0, 1, 0, 1, 1) to 5,
    listOf(1, 1, 0, 1, 1, 1, 1) to 6,
    listOf(1, 0, 1, 0, 0, 1, 0) to 7,
    listOf(1, 1, 1, 1, 1, 1, 1) to 8,
    listOf(1, 1, 1, 1, 0, 1, 1) to 9,
)

val lines = File("day8/input").readLines().map { it.trim().split(" | ").map { it.split(" ") } }
println(lines.sumOf { it[1].count { it.length in listOf(2, 4, 3, 7) } })

println(lines.sumOf {
    val groups = it[0].map { it.toSet() }.groupBy { it.size }
    val adg = groups[5]!!.reduceRight { a, b -> a intersect b } // adg = 3 & 5 & 2
    val a = adg.intersect(groups[3]!![0]) // a = adg & 7
    val d = adg.intersect(groups[4]!![0]) // d = adg & 4
    val g = adg - a - d
    val abfg = groups[6]!!.reduceRight { a, b -> a intersect b } // abfg = 0 & 6 & 9
    val cf = groups[2]!![0] // cf = 1
    val f = cf.intersect(abfg)
    val c = cf - abfg
    val b = abfg - a - f - g
    val e = groups[7]!![0] - a - b - c - d - f - g // e = 8 - everything

    it[1].map { digit ->
        digits[listOf(a, b, c, d, e, f, g).map { segment -> digit.contains(segment.first()).toInt() }]!!
    }.joinToString("").toInt()
})