package servis.chat.repository

import org.springframework.stereotype.Component
import servis.chat.request.Form
import servis.chat.dto.Profile
import servis.chat.dto.User
import servis.chat.request.UserRegister




@Component
class UserRepository(
    val userRepository: MutableList<User>
){
    private fun convertGender(formGender: Form.Gender): User.Gender {
        return when (formGender) {
            Form.Gender.Female -> User.Gender.Female
            Form.Gender.Male -> User.Gender.Male
        }
    }

    fun getUserByToken(token: String): User? {
        return userRepository.find { it.token == token }
    }


    fun register(userRegister:UserRegister,token:String): Int {
        userRepository.add(User(id=userRepository.size,token=token , login = userRegister.login, password = userRegister.password ))
        return userRepository.size-1
    }

    fun getProfile(page: Int, size: Int, sortBy: String) : List<Profile> {
        return userRepository.map { user ->
            Profile(
                age=user.age ?: 0,
                lastName=user.lastName ?: "",
                firstName = user.firstName ?: "",
                photoUrl = user.photo ?: ""
            )
        }
    }

    fun makeInfo(form: Form, token:String): Int {
        var id = -1
        userRepository.forEach{
                user ->
            if (user.token == token) {
                user.age = form.age
                user.gender= convertGender(form.gender)
                user.lastName = form.lastName
                user.firstName = form.firstName
                id = user.id
                user.photo = form.photo
            }
        }
        return id
    }
}
