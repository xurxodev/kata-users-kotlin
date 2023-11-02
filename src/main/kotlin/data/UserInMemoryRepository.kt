package data

import domain.entities.User
import domain.errors.GetUserError
import domain.errors.GetUsersError
import domain.errors.UnexpectedError
import domain.repositories.UserRepository
import domain.types.Either
import domain.valueObjects.Email
import java.lang.Exception

class UserInMemoryRepository : UserRepository {
    private val users: MutableList<User> = mutableListOf()

    override fun getGetByEmail(email: Email): Either<GetUserError, User> {
        return try {
            val user = users.find { it.email == email }

            if (user == null) {
                Either.Left(GetUserError.ResourceNotFoundError)
            } else {
                Either.Right(user)
            }
        } catch (e: Exception) {
            Either.Left(GetUserError.UnexpectedError(e.message ?: "unexpected error"))
        }
    }

    override fun getUsers(): Either<GetUsersError, List<User>> {
        return try {
            Either.Right(users)
        } catch (e: Exception) {
            Either.Left(GetUsersError.UnexpectedError(e.message ?: "unexpected error"))
        }
    }

    override fun save(user: User): Either<UnexpectedError, Unit> {
        return try {
            this.users.add(user)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(UnexpectedError(e.message ?: "unexpected error"))
        }
    }
}