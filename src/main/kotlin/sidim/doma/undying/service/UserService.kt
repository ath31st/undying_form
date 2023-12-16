package sidim.doma.undying.service

import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class UserService(val dsl: DSLContext) {
    public fun createUser() {
        //dsl.insertInto(USERS)
    }
}