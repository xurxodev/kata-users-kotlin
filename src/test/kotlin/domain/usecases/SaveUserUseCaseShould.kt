package domain.usecases

import domain.entities.User
import domain.errors.GetUserError
import domain.errors.GetUsersError
import domain.errors.SaveUserError
import domain.errors.UnexpectedError
import domain.repositories.UserRepository
import domain.types.Either
import domain.types.get
import domain.valueObjects.Email
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

val users = listOf(
    User.create("Jorge Sánchez Fernández", "xurxodev@gmail.com", "12345678A").get(),
    User.create("David Sánchez Fernández", "nonexisted@gmail.com", "12345678B").get(),
)

class SaveUserUseCaseShould {
    @Test
    fun `should save successfully a new valid use`() {
        val useCase = givenThereAreNoUsers()

        val result = useCase(users[0])

        assertTrue(result.isRight)
    }

    @Test
    fun `should return an duplicate resource error`() {
        val useCase = givenThereAreUsers()

        val result = useCase(users[0])

        result.fold(
            { error ->
                assertTrue(error is SaveUserError.DuplicateUserError)
            },
            { _ -> fail("Should be fail") }
        )
    }

    private fun givenThereAreNoUsers(): SaveUserUseCase {
        return SaveUserUseCase(object : UserRepository {
            override fun getGetByEmail(email: Email): Either<GetUserError, User> {
                return Either.Left(GetUserError.ResourceNotFoundError)
            }

            override fun getUsers(): Either<GetUsersError, List<User>> {
                return Either.Right(listOf())
            }

            override fun save(user: User): Either<UnexpectedError, Unit> {
                return Either.Right(Unit)
            }
        })
    }

    private fun givenThereAreUsers(): SaveUserUseCase {
        return SaveUserUseCase(object : UserRepository {
            override fun getGetByEmail(email: Email): Either<GetUserError, User> {
                return Either.Right(users[0])
            }

            override fun getUsers(): Either<GetUsersError, List<User>> {
                return Either.Right(users)
            }

            override fun save(user: User): Either<UnexpectedError, Unit> {
                return Either.Right(Unit)
            }
        })
    }
}