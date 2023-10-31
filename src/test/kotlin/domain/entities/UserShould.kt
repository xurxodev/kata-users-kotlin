package domain.entities

import domain.types.ValidationErrorKey
import domain.valueObjects.Id
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UserShould {
    @Test
    fun `not return errors if all fields are valid`() {
        val result = User.create("Jorge Sanchéz Fernández", "xurxodev@gmail.com", "12345678A")

        result.fold(
            { _ -> fail("Should be success") },
            { user -> assertNotNull(user) }
        )
    }

    @Test
    fun `return invalid field error for empty name`() {
        val result = User.create("", "xurxodev@gmail.com", "12345678A")

        result.fold(
            { errors ->
                assertEquals(
                    ValidationErrorKey.FIELD_CANNOT_BE_BLANK,
                    errors.find { it.property == "name" }!!.errors[0]
                )
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `return invalid field error for empty email`() {
        val result = User.create("Jorge Sanchéz Fernández", "", "12345678A")

        result.fold(
            { errors ->
                assertEquals(
                    ValidationErrorKey.FIELD_CANNOT_BE_BLANK,
                    errors.find { it.property == "email" }!!.errors[0]
                )
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `return invalid field error for empty password`() {
        val result = User.create("Jorge Sanchéz Fernández", "xurxodev@gmail.com", "")

        result.fold(
            { errors ->
                assertEquals(
                    ValidationErrorKey.FIELD_CANNOT_BE_BLANK,
                    errors.find { it.property == "password" }!!.errors[0]
                )
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `return invalid field error non valid empty email`() {
        val result = User.create("Jorge Sanchéz Fernández", "xurxodevgmail", "12345678A")

        result.fold(
            { errors ->
                assertEquals(
                    ValidationErrorKey.INVALID_FIELD,
                    errors.find { it.property == "email" }!!.errors[0]
                )
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `return invalid field error non valid empty password`() {
        val result = User.create("Jorge Sanchéz Fernández", "xurxodev@gmail", "1234")

        result.fold(
            { errors ->
                assertEquals(
                    ValidationErrorKey.INVALID_FIELD,
                    errors.find { it.property == "password" }!!.errors[0]
                )
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `be equals two instances of user if it has the same id`() {
        val id = Id.generateId().value
        val user1 = User.create("Jorge Sanchéz Fernández", "xurxodev@gmail", "12345678A", id)
        val user2 = User.create("David Sanchéz Fernández", "ddddev@gmail", "12345678A", id)

        assertEquals(user1, user2)
    }

    @Test
    fun `not be equals two instances of user if it has different id`() {
        val user1 = User.create("Jorge Sanchéz Fernández", "xurxodev@gmail", "12345678A")
        val user2 = User.create("David Sanchéz Fernández", "ddddev@gmail", "12345678A")

        assertNotEquals(user1, user2)
    }
}