package domain.valueObjects

import domain.types.ValidationErrorKey
import domain.types.get
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EmailShould {
    @Test
    fun `return success response if email is valid`() {
        val emailValue = "info@karatestarsapp.com"
        val emailResult = Email.create(emailValue)

        emailResult.fold(
            { _ -> fail("Should be success") },
            { email -> assertEquals(emailValue, email.value) }
        )
    }

    @Test
    fun `should return Id cannot be blank error if value argument is empty`() {
        val emailResult = Email.create("")

        emailResult.fold(
            { errors ->
                assertEquals(1, errors.size)
                assertEquals(ValidationErrorKey.FIELD_CANNOT_BE_BLANK, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `should return Invalid error if value argument is invalid`() {
        val emailResult = Email.create("infokaratestarsapp.com")

        emailResult.fold(
            { errors ->
                assertEquals(1, errors.size)
                assertEquals(ValidationErrorKey.INVALID_FIELD, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `should be equals two instances of email if it has the same property values`() {
        val email1 = Email.create("info@karatestarsapp.com").get()
        val email2 = Email.create("info@karatestarsapp.com").get()

        assertEquals(email1, email2)
    }

    @Test
    fun `should not be equals two instances of email if it has the same property values`() {
        val email1 = Email.create("info@karatestarsapp.com").get()
        val email2 = Email.create("hello@karatestarsapp.com").get()

        assertNotEquals(email1, email2)
    }
}