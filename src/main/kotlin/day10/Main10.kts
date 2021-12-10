import java.io.File

data class ExpressionStack(val stack: List<Char> = emptyList(), val error_score: Int = 0)
val lines = File("day10/input").readLines().map { it.toCharArray() }

val result = lines.map { chars ->
    chars.fold(ExpressionStack()) { acc, char -> when (char) {
        in listOf('(', '[', '{', '<') -> ExpressionStack(acc.stack + char, acc.error_score)
        else -> ExpressionStack(acc.stack.dropLast(1), acc.error_score + when (char) {
            acc.stack.last() -> 0
            ')' -> if (acc.stack.last() == '(') 0 else 3
            ']' -> if (acc.stack.last() == '[') 0 else 57
            '}' -> if (acc.stack.last() == '{') 0 else 1197
            '>' -> if (acc.stack.last() == '<') 0 else 25137
            else -> throw RuntimeException("Unknown char $char")
        })
    } }
}
println(result.sumOf { it.error_score })

val p2_scores = result.filter { it.error_score == 0 }.map {
    it.stack.foldRight(0L) { char, score -> 5*score + when (char) {
        '(' -> 1
        '[' -> 2
        '{' -> 3
        '<' -> 4
        else -> throw RuntimeException("Unknown char $char")
    } }
}
println(p2_scores.sorted()[p2_scores.size/2])