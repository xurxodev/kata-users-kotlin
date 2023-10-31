package domain.valueObjects

import domain.types.Either
import domain.types.ValidationErrorKey
import domain.utils.validateRegexp
import domain.utils.validateRequired
import kotlin.math.floor

const val PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        fun create(value: String): Either<List<ValidationErrorKey>, Password> {
            val requiredError = validateRequired(value)
            val regexpErrors = validateRegexp(value, Regex(PASSWORD_PATTERN))

            return if (requiredError.isNotEmpty()) {
                Either.Left(requiredError)
            } else if (regexpErrors.isNotEmpty()) {
                Either.Left(regexpErrors)
            } else {
                Either.Right(Password(value))
            }
        }
    }
}



