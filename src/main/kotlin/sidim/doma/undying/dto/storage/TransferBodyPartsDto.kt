package sidim.doma.undying.dto.storage

import java.io.Serializable
import java.util.UUID

data class TransferBodyPartsDto(
    val scholarId: Long,
    val actionUUID: UUID,
    val bodyPartIds: List<Long>,
) : Serializable
