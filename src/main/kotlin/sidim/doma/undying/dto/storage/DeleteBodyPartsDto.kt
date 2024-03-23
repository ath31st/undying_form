package sidim.doma.undying.dto.storage

import java.io.Serializable

data class DeleteBodyPartsDto(
    val scholarId: Long,
    val bodyPartIds: List<Long>,
) : Serializable
