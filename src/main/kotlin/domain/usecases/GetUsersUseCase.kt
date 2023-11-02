package domain.usecases

import domain.entities.User
import domain.errors.GetUsersError
import domain.repositories.UserRepository
import domain.types.Either

class GetUsersUseCase (private val userRepository: UserRepository) {
    operator fun invoke(): Either<GetUsersError, List<User>> {
        return this.userRepository.getUsers()
    }
}