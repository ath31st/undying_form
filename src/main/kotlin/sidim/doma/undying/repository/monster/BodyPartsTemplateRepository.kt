package sidim.doma.undying.repository.monster

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import sidim.doma.undying.generated.tables.references.HAND_TEMPLATES
import sidim.doma.undying.generated.tables.references.HEAD_TEMPLATES
import sidim.doma.undying.generated.tables.references.LEG_TEMPLATES
import sidim.doma.undying.generated.tables.references.UPPER_BODY_TEMPLATES

@Repository
class BodyPartsTemplateRepository(private val dslContext: DSLContext) {
    private val th = HAND_TEMPLATES
    private val tl = LEG_TEMPLATES
    private val tub = UPPER_BODY_TEMPLATES
    private val the = HEAD_TEMPLATES
}