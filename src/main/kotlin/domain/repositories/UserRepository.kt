package domain.repositories

import domain.entities.User
import domain.errors.GetUserError
import domain.errors.GetUsersError
import domain.errors.UnexpectedError
import domain.types.Either
import domain.valueObjects.Email

interface UserRepository {
    fun getGetByEmail(email: Email): Either<GetUserError, User>
    fun getUsers(): Either<GetUsersError, List<User>>
    fun save(user: User):Either<UnexpectedError,Unit>
}