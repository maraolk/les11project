package servis.chat.controller

import org.springframework.web.bind.annotation.*
import servis.chat.dto.Form
import servis.chat.dto.UserRegister
import servis.chat.service.RegisterService

@RestController
class RegisterController (
    val registrationService: RegisterService
) {

    @PostMapping("/users")
    fun register(@RequestBody userRegister: UserRegister) = registrationService.register(userRegister)

    @PutMapping("/users")
    fun addInfo(@RequestBody form: Form, @RequestHeader("Authorization") token: String) = registrationService.makeInfo(form, token)
}
