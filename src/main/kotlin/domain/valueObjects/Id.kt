package domain.valueObjects

import domain.types.Either
import domain.types.ValidationErrorKey
import domain.utils.validateRegexp
import domain.utils.validateRequired
import kotlin.math.floor

const val abc = "abcdefghijklmnopqrstuvwxyz"
val letters = abc.plus(abc.uppercase())
val ALLOWED_CHARS = "0123456789${letters}"
val NUMBER_OF_CODEPOINTS = ALLOWED_CHARS.length
const val CODE_SIZE = 10

/**
 * JkWynlWMjJR' // valid
 * 0kWynlWMjJR' // invalid (Uid can not start with a number)
 * AkWy$lWMjJR  // invalid (Uid can only contain alphanumeric characters.
 */
val ID_PATTERN = "^[a-zA-Z]{1}[a-zA-Z0-9]{10}$"

@JvmInline
value class Id private constructor(
    val value: String,
) {

    companion object {
        fun generateId(): Id {
            // First char should be a letter
            val letterIndex = randomWithMax(letters.length)
            var randomChars = letters.substring(letterIndex,letterIndex +1)

            for (i in 1..CODE_SIZE) {
                val index = randomWithMax(letters.length)
                randomChars += ALLOWED_CHARS.substring(index,index +1)
            }

            return Id(value = randomChars)
        }

        fun createExisted(id: String): Either<List<ValidationErrorKey>, Id> {
            val requiredError = validateRequired(id)
            val regexpErrors = validateRegexp(id, Regex(ID_PATTERN))

            if (requiredError.isNotEmpty()) {
                return Either.Left(requiredError);
            } else if (regexpErrors.isNotEmpty()) {
                return Either.Left(regexpErrors);
            } else {
                return Either.Right(Id(id))
            }
        }
    }
}

fun randomWithMax(max: Int): Int {
    return floor(Math.random() * max).toInt()
}



