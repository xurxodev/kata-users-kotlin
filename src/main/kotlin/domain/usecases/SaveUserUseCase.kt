package domain.usecases

import domain.entities.User
import domain.errors.GetUserError
import domain.errors.SaveUserError
import domain.repositories.UserRepository
import domain.types.Either
import domain.types.mapLeft

class SaveUserUseCase (private val userRepository: UserRepository) {
    operator fun invoke(user: User): Either<SaveUserError, Unit> {

        val userResult = userRepository.getGetByEmail(user.email)

        return userResult.fold({ error ->
            when (error) {
                is GetUserError.ResourceNotFoundError -> {
                    userRepository.save(user).mapLeft { SaveUserError.UnexpectedError(it.message) }
                }

                is GetUserError.UnexpectedError -> {
                    Either.Left(SaveUserError.UnexpectedError(error.message))
                }
            }
        }, { _ -> Either.Left(SaveUserError.DuplicateUserError) })
    }
}