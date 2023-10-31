package domain.entities

import domain.valueObjects.Id

abstract class Entity(val id: Id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}