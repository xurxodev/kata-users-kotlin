package domain.valueObjects

import domain.types.ValidationErrorKey
import domain.types.get
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PasswordShould {
    @Test
    fun `return success response if argument is valid`() {
        val passwordValue = "12345678A"
        val passwordResult = Password.create(passwordValue)

        passwordResult.fold(
            { _ -> fail("Should be success") },
            { pwd -> assertEquals(passwordValue, pwd.value) }
        )
    }

    @Test
    fun `should return Id cannot be blank error if value argument is empty`() {
        val passwordResult = Password.create("")

        passwordResult.fold(
            { errors ->
                assertEquals(1, errors.size)
                assertEquals(ValidationErrorKey.FIELD_CANNOT_BE_BLANK, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `should return Invalid error if value argument is invalid`() {
        val passwordResult = Password.create("123")

        passwordResult.fold(
            { errors ->
                assertEquals(1, errors.size)
                assertEquals(ValidationErrorKey.INVALID_FIELD, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `should be equals two instances of password if it has the same property values`() {
        val password1 = Password.create("12345678A").get()
        val password2 = Password.create("12345678A").get()

        assertEquals(password1, password2)
    }

    @Test
    fun `should not be equals two instances of password if it has the same property values`() {
        val password1 = Password.create("12345678A").get()
        val password2 = Password.create("12345678B").get()

        assertNotEquals(password1, password2)
    }
}