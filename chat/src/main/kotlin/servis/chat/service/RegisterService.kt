package servis.chat.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import servis.chat.dto.Form
import servis.chat.dto.UserRegister
import servis.chat.repository.UserRepository
import servis.chat.response.FormResponse
import servis.chat.response.UserResponse
import kotlin.random.Random

@Service
class RegisterService(
    val userRepository: UserRepository
) {

    private fun generateToken(length: Int): String {
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
    }

    fun register(userRegister: UserRegister): ResponseEntity<UserResponse> {
        if (userRegister.login.isBlank() || userRegister.password.isBlank()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        val token = generateToken(Random.nextInt(10, 30))
        val id = userRepository.register(userRegister, token)
        return ResponseEntity(UserResponse(id, userRegister.login, token), HttpStatus.OK)
    }

    fun makeInfo(form: Form, token: String): ResponseEntity<FormResponse> {
        val id = userRepository.makeInfo(form, token)
        return ResponseEntity(FormResponse(id), HttpStatus.OK)
    }
}
