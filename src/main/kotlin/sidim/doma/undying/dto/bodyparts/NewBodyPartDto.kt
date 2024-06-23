package sidim.doma.undying.dto.bodyparts

data class NewBodyPartDto(
    val scholarId: Long,
    val quality: Int,
    val integrity: Int,
    val side: String?,
    val templateId: Int,
)
