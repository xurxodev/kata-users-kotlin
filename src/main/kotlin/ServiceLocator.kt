import data.UserInMemoryRepository
import domain.usecases.GetUsersUseCase
import domain.usecases.SaveUserUseCase
import presentation.UsersPresenter
import presentation.UsersTerminalView

fun getUsersPresenter(): UsersPresenter {
    val userRepository = UserInMemoryRepository()
    val getUsers = GetUsersUseCase(userRepository)
    val saveUser = SaveUserUseCase(userRepository)

    return UsersPresenter(UsersTerminalView(), getUsers, saveUser)
}