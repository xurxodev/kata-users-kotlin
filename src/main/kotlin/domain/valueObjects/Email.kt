package domain.valueObjects

import domain.types.Either
import domain.types.ValidationErrorKey
import domain.utils.validateRegexp
import domain.utils.validateRequired
import kotlin.math.floor

const val EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

@JvmInline
value class Email private constructor(val value: String) {
    companion object {

        fun create(value: String): Either<List<ValidationErrorKey>, Email> {
            val requiredError = validateRequired(value)
            val regexpErrors = validateRegexp(value, Regex(EMAIL_PATTERN))

            return if (requiredError.isNotEmpty()) {
                Either.Left(requiredError);
            } else if (regexpErrors.isNotEmpty()) {
                Either.Left(regexpErrors);
            } else {
                Either.Right(Email(format(value)))
            }
        }

        private fun format(email: String): String {
            return email.trim().lowercase();
        }
    }
}



