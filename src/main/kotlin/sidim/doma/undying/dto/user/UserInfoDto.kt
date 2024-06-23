package sidim.doma.undying.dto.user

data class UserInfoDto(
    val userId: Long,
    val role: String,
    val username: String,
    val email: String,
)
