import java.io.File

// [0] = fish with timer=0 (not counting sons), etc.
typealias FishPopulation = Array<Long>

fun tick(p: FishPopulation) =
    arrayOf(
        p[1], p[2], p[3], p[4], p[5], p[6], // Timer values 0 to 5
        p[7] + p[0], // Timer=6 comes from timer=7 (newborns) or from timer=0 (respawning)
        p[8], // Timer=7 comes from timer=8
        p[0], // Timer=8 comes from timer=0 (birth)
    )

val fish = File("day6/input").readText().trimEnd().split(",").map { it.toInt() }
var population = (0..8).map { n -> fish.count { it == n }.toLong() }.toTypedArray()

for (i in 1..80)
    population = tick(population)
println(population.sum())

for (i in 81..256)
    population = tick(population)
println(population.sum())