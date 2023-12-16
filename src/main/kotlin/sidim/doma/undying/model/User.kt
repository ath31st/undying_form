package sidim.doma.undying.model

import java.time.LocalDate

class User(
    val id: Long,
    val username: String,
    val name: String,
    val registerDate: LocalDate,
    val isActive: Boolean,
    val isBlocked: Boolean,
)