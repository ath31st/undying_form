package sidim.doma.undying.dto.action

import java.util.UUID

class NewFindingBodyPartsInGraveyardReq(
    val uuid: UUID,
    val graveyardId: Int,
    val scholarId: Long,
)
