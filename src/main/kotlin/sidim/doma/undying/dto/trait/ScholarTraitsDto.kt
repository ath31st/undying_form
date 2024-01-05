package sidim.doma.undying.dto.trait

class ScholarTraitsDto(
    val scholarId: Long,
    val positiveTraits: List<PositiveTraitDto>,
    val negativeTraits: List<NegativeTraitDto>,
)
