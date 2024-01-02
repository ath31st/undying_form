package sidim.doma.undying.mapper

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.model.NegativeTrait

@Component
class NegativeTraitMapper(
    private val modelMapper: ModelMapper
) {
    fun fromPojoToModel(negativeTraits: NegativeTraits): NegativeTrait {
        return modelMapper.map(negativeTraits, NegativeTrait::class.java)
    }
}