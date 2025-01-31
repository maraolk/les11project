package servis.chat

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatusCode
import servis.chat.client.UserClient
import servis.chat.request.Form
import servis.chat.request.UserRegister


@SpringBootTest
class ChatApplicationTests {

	@Autowired
	private lateinit var userClient: UserClient

	@Test
	fun testRegister() {
		val request = UserRegister(
			login = "ufffffff",
			password = "53465"
		)

		val response = userClient.registerUser(request)
		Assertions.assertEquals(HttpStatusCode.valueOf(200), response.statusCode)
	}

	@Test
	fun editUser() {
        val registerRequest = UserRegister(login = "opopop", password = "7454587")
        val registerResponse = userClient.registerUser(registerRequest)
        Assertions.assertEquals(HttpStatusCode.valueOf(200), registerResponse.statusCode)

        val editRequest = Form(gender = Form.Gender.Male, age = 35, lastName = "Testov", firstName = "Test", photo="url")
        val editResponse = userClient.updateUser(editRequest, registerResponse.body!!.token)
        Assertions.assertEquals(HttpStatusCode.valueOf(200), editResponse.statusCode)
    }

}