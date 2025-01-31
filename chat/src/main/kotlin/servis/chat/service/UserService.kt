package servis.chat.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.random.Random
import servis.chat.dto.Profile
import servis.chat.repository.ReactionRepository
import servis.chat.repository.UserRepository
import servis.chat.request.Form
import servis.chat.request.UserRegister
import servis.chat.request.ReactionRequest
import servis.chat.response.FormResponse
import servis.chat.response.UserResponse
import servis.chat.response.ReactionResponse

@Service
class UserService(
    val userRepository: UserRepository,
    val reactionRepository: ReactionRepository
) {

    private fun generateRandomString(length: Int): String {
        val symbols = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { symbols.random() }
            .joinToString("")
    }

    fun register(userRegister: UserRegister) : ResponseEntity<UserResponse> {
        val token = generateRandomString(Random.nextInt(10, 50))
        val id = userRepository.register(userRegister, token)
        return ResponseEntity(UserResponse(id, userRegister.login, token), HttpStatus.OK)
    }

    fun addInfo(form: Form, token: String): ResponseEntity<FormResponse> {
        val id = userRepository.makeInfo(form, token)
        return ResponseEntity(FormResponse(id, form), HttpStatus.OK)
    }

    fun getUsers(page: Int, size: Int, sortBy: String, token: String): ResponseEntity<List<Profile>> {
        if (userRepository.getUserByToken(token) == null) {
            return ResponseEntity(listOf(), HttpStatus.UNAUTHORIZED)
        }
        val profiles = userRepository.getProfile(page, size, sortBy)
        return ResponseEntity(profiles, HttpStatus.OK)
    }

    fun react(reaction: ReactionRequest, id: Int, token: String) : ResponseEntity<ReactionResponse> {
        val user = userRepository.getUserByToken(token)
        if (user == null) {
            return ResponseEntity(ReactionResponse(false), HttpStatus.NOT_FOUND)
        }
        reactionRepository.react(reaction.reaction, id, user.id)
        return ResponseEntity(ReactionResponse(reaction.reaction), HttpStatus.OK)
    }
}