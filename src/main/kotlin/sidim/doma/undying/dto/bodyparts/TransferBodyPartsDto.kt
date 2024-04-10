package sidim.doma.undying.dto.bodyparts

import java.io.Serializable
import java.util.*

data class TransferBodyPartsDto(
    val scholarId: Long,
    val actionUuid: UUID,
    val bodyPartIds: List<Long>,
) : Serializable
