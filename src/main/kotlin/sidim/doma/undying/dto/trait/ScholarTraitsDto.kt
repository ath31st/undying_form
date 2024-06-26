package sidim.doma.undying.dto.trait

data class ScholarTraitsDto(
    val scholarId: Long,
    val positiveTraits: List<PositiveTraitDto>,
    val negativeTraits: List<NegativeTraitDto>,
)
