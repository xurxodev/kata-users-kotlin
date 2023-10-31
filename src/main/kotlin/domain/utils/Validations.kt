package domain.utils

import domain.types.ValidationErrorKey

fun validateRequired(value: String): List<ValidationErrorKey> {
    return  if (value.isEmpty()) listOf(ValidationErrorKey.FIELD_CANNOT_BE_BLANK) else listOf()
}

 fun validateRegexp(value: String, regex: Regex):List<ValidationErrorKey> {
    return if (regex.containsMatchIn(value)) listOf() else listOf(ValidationErrorKey.INVALID_FIELD)
}
