package domain.valueObjects

import domain.types.ValidationErrorKey
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IdShould {

    @Test
    fun `return right type if result is success`() {

        val id = Id.generateId()

        assertNotNull(id.value)
    }

    @Test
    fun `return success creating from a valid existed id`() {
        val existedId = Id.generateId()

        val idResult = Id.createExisted(existedId.value)

        idResult.fold(
            { _ -> fail("Should be success") },
            { id -> assertEquals(id.value, existedId.value) }
        )
    }

    @Test
    fun `return Id cannot be blank error if value argument is empty`() {
        val idResult = Id.createExisted("")

        idResult.fold(
            { errors ->
                assertEquals(1,errors.size)
                assertEquals(ValidationErrorKey.FIELD_CANNOT_BE_BLANK, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `return InvalidId error if value argument is invalid`() {
        val idResult = Id.createExisted("wrong id")

        idResult.fold(
            { errors ->
                assertEquals(1,errors.size)
                assertEquals(ValidationErrorKey.INVALID_FIELD, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `return InvalidId error if value argument starts with a number`() {
        val idResult = Id.createExisted("0kWynlWMjJR")

        idResult.fold(
            { errors ->
                assertEquals(1,errors.size)
                assertEquals(ValidationErrorKey.INVALID_FIELD, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }

    @Test
    fun `return InvalidId error if value argument contains non-alphanumeric characters`() {
        val idResult = Id.createExisted("AkWy_lWMjJR")

        idResult.fold(
            { errors ->
                assertEquals(1,errors.size)
                assertEquals(ValidationErrorKey.INVALID_FIELD, errors[0])
            },
            { _ -> fail("should be fail") }
        )
    }
}