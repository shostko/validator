package by.shostko.validator.sample

import kotlinx.coroutines.delay

class MainRepository {

    suspend fun login(email: String, username: String, password: String) {
        delay(500L)
    }

    suspend fun checkIfUsernameAvailable(username: String): Boolean {
        delay(500L)
        return !username.equals("shostko", ignoreCase = true)
            && !username.equals("sergey", ignoreCase = true)
    }
}
