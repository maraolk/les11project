package servis.chat.repository

import org.springframework.stereotype.Repository
import servis.chat.dto.Form
import servis.chat.dto.User
import servis.chat.dto.UserRegister


@Repository
class UserRepository(
    val userRepository: MutableList<User>
){
    private fun convertGender(formGender: Form.Gender): User.Gender {
        return when (formGender) {
            Form.Gender.Female -> User.Gender.Male
            Form.Gender.Male -> User.Gender.Female
        }
    }
    fun register(user:UserRegister,token:String): Int {
        userRepository.add(User(id=userRepository.size,token=token , login = user.login, password = user.password ))
        return userRepository.size-1
    }
    fun makeInfo(form: Form, token:String): Int {
        var id = 52
        userRepository.forEach{
                user ->
            if (user.token == token) {
                user.age = form.age
                user.gender= convertGender(form.gender)
                user.lastName = form.lastName
                user.firstName = form.firstName
                id = user.id
            }
        }
        return id
    }
}
