package presentation

import domain.entities.User
import getUsersPresenter
import java.util.*

class UsersTerminalView : UsersPresenter.UsersView {
    override fun showError(message: String) {
        println("Ups, something went wrong :(")
        println(message)
        println("Try again!")
    }

    override fun showEmptyCase() {
        println("Users is empty! :(");
    }

    override fun showUsers(users: List<User>) {
        println("Users!:");
        users.forEach {
            println("${it.name} - ${it.email.value}");
        }
    }


    override fun showWelcomeMessage() {
        //system("clear")
        println("Welcome to your users kata typescript :)");
    }

    override fun showGoodbyeMessage() {
        println("\nSee you soon!");
    }


    override val requestUsername: String
        get() {
            print("Creating new user.\nName? ")
            return read()
        }
    override val requestEmail: String
        get() {
            print("Email? ")
            return read()
        }
    override val requestPassword: String
        get() {
            print("Password? ")
            return read()
        }

    private fun read(): String {
        val scanner = Scanner(System.`in`)

        return scanner.nextLine()
        //return readln()
    }


}