package domain.types

enum class ValidationErrorKey {
    FIELD_CANNOT_BE_BLANK,
    INVALID_FIELD
}

val validationErrorMessages: Map<ValidationErrorKey, (field: String) -> String> = mapOf(
    ValidationErrorKey.FIELD_CANNOT_BE_BLANK to { field: String -> "${capitalize(field)} cannot be blank" },
    ValidationErrorKey.INVALID_FIELD to { field: String -> "Invalid ${field.lowercase()}" }
)

fun capitalize(text: String): String {
    return text.substring(0, 1).uppercase() + text.substring(1);
}

data class ValidationError(
    val property: String,
    val value: Any,
    val errors: List<ValidationErrorKey>
)