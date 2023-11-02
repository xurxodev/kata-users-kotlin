package domain.errors

sealed class GetUserError {
    data object ResourceNotFoundError : GetUserError()
    data class UnexpectedError(val message: String) : GetUserError()
}

sealed class GetUsersError {
    data class UnexpectedError(val message: String) : GetUsersError()
}

data class UnexpectedError (val message: String)

sealed class SaveUserError {
    data object DuplicateUserError : SaveUserError()
    data class UnexpectedError(val message: String) : SaveUserError()
}
