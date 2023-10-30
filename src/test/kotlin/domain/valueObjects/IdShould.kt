package domain.valueObjects

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IdShould {

    @Test
    fun `return right type if result is success`() {

        val id = Id.generateId()

        assertNotNull(id.value)
    }
}