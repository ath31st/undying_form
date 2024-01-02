package sidim.doma.undying.mapper

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.pojos.PositiveTraits
import sidim.doma.undying.model.PositiveTrait

@Component
class PositiveTraitMapper(
    private val modelMapper: ModelMapper
) {
    fun fromPojoToModel(positiveTraits: PositiveTraits): PositiveTrait {
        return modelMapper.map(positiveTraits, PositiveTrait::class.java)
    }
}