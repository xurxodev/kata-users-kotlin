fun main() {
    val presenter = getUsersPresenter()

    presenter.onInitialize()

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            presenter.onStop()
        }
    })

    while (true) {
        presenter.loadUsersListAndRequestNew()
    }
}