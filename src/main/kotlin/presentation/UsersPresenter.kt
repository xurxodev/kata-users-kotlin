package presentation

import domain.entities.User
import domain.errors.GetUsersError
import domain.errors.SaveUserError
import domain.types.validationErrorMessages
import domain.usecases.GetUsersUseCase
import domain.usecases.SaveUserUseCase

class UsersPresenter(
    private val view: UsersView,
    private val getUsersUseCase: GetUsersUseCase,
    private val saveUserUserCase: SaveUserUseCase
) {

    fun onInitialize() {
        this.view.showWelcomeMessage()
    }

    fun onStop() {
        this.view.showGoodbyeMessage()
    }

    fun loadUsersListAndRequestNew() {
        val result = getUsersUseCase()

        result.fold(
            { error ->
                when (error) {
                    is GetUsersError.UnexpectedError -> this.view.showError(error.message)
                }
            },
            { users ->
                if (users.isEmpty()) {
                    this.view.showEmptyCase()
                } else {
                    this.view.showUsers(users)
                }
            }
        )

        this.onAddUserOptionSelected()
    }

    private fun onAddUserOptionSelected() {
        val name = this.view.requestUsername
        val email = this.view.requestEmail
        val password = this.view.requestPassword

        val userValidation = User.create(name, email, password)

        userValidation.fold(
            { errors ->
                val errors = errors.map { error -> error.errors.map { validationErrorMessages[it]?.let { it1 -> it1(error.property) } } }
                    .flatten().joinToString("\n")

                this.showErrorAndShowListAndRequestNew(errors)
            },
            { user ->
                this.saveUser(user)
            })
    }

    private fun saveUser(user: User) {
        val saveResult = this.saveUserUserCase(user)

        saveResult.fold(
            { error ->
                when (error) {
                    is SaveUserError.UnexpectedError -> this.showErrorAndShowListAndRequestNew(error.message)
                    is SaveUserError.DuplicateUserError -> this.showErrorAndShowListAndRequestNew(
                        "There is already an user with email ${user.email.value}"
                    )
                }
            },
            { _ ->
                //this.loadUsersListAndRequestNew()
            }

        )
    }

    private fun showErrorAndShowListAndRequestNew(message: String) {
        this.view.showError(message)
        //this.loadUsersListAndRequestNew()
    }

    interface UsersView {
        fun showError(message: String)
        fun showEmptyCase()
        fun showWelcomeMessage()
        fun showGoodbyeMessage()
        fun showUsers(users: List<User>)

        val requestUsername: String

        val requestEmail: String

        val requestPassword: String
    }
}