package domain.valueObjects

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
        );
    }
}