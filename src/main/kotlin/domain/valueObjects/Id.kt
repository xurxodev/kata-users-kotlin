package domain.valueObjects

import kotlin.math.floor

const val abc = "abcdefghijklmnopqrstuvwxyz"
val letters = abc.plus(abc.uppercase())
val ALLOWED_CHARS = "0123456789${letters}"
val NUMBER_OF_CODEPOINTS = ALLOWED_CHARS.length
const val CODE_SIZE = 11;

@JvmInline
value class Id private constructor(
    val value: String,
) {

    companion object {
        fun generateId(): Id {
            // First char should be a letter
            var randomChars = letters.substring(randomWithMax(letters.length))

            for (i in 1..CODE_SIZE) {
                randomChars += ALLOWED_CHARS.substring(randomWithMax(NUMBER_OF_CODEPOINTS));
            }

            return Id(value =randomChars)
        }
    }
}

fun randomWithMax(max: Int):Int {
    return floor(Math.random() * max).toInt()
}



