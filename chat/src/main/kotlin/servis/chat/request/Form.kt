package servis.chat.request

data class Form(
    val gender: Gender,
    val age: Int,
    val lastName: String,
    val firstName: String,
    val photo: String
) {

    enum class Gender {
        Male, Female;
    }
}
