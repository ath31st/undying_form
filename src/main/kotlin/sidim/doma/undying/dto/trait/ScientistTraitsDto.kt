package sidim.doma.undying.dto.trait

class ScientistTraitsDto(
    val scientistId: Long,
    val positiveTraits: List<PositiveTraitDto>,
    val negativeTraits: List<NegativeTraitDto>,
)
