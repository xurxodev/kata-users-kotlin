package domain.entities

import domain.types.Either
import domain.types.ValidationError
import domain.types.ValidationErrorKey
import domain.types.get
import domain.utils.validateRequired
import domain.valueObjects.Email
import domain.valueObjects.Id
import domain.valueObjects.Password

class User private constructor(id: Id, val name: String, val email: Email, val password: Password) : Entity(id) {
    companion object {
        fun create(
            name: String,
            email: String,
            password: String,
            id: String = Id.generateId().value
        ): Either<List<ValidationError>, User> {
            return validateAndCreate(id, name, email, password)
        }

        private fun validateAndCreate(
            id: String,
            name: String,
            email: String,
            password: String
        ): Either<List<ValidationError>, User> {
            val idValidation = Id.createExisted(id)
            val emailValidation = Email.create(email)
            val passwordValidation = Password.create(password)

            val errors = listOf(
                extractError("id", idValidation, id),
                extractError("email", emailValidation, email),
                extractError("password", passwordValidation, password),
                ValidationError(property = "name", value = name, errors = validateRequired(name))
            ).filter { it.errors.isNotEmpty() }

            return if (errors.isEmpty()) {
                Either.Right(
                    User(
                        id = idValidation.get(),
                        name = name,
                        email = emailValidation.get(),
                        password = passwordValidation.get(),
                    )
                )
            } else {
                Either.Left(errors)
            }
        }

        private fun <T> extractError(
            property: String,
            validation: Either<List<ValidationErrorKey>, T>,
            value: Any
        ): ValidationError {
            val errors: List<ValidationErrorKey> = validation.fold({ errors -> errors }, { _ -> listOf() })

            return ValidationError(property, value, errors)
        }
    }
}