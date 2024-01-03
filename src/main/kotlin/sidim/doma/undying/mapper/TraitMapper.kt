package sidim.doma.undying.mapper

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component
import sidim.doma.undying.dto.trait.NegativeTraitDto
import sidim.doma.undying.dto.trait.PositiveTraitDto
import sidim.doma.undying.generated.tables.pojos.NegativeTraits
import sidim.doma.undying.generated.tables.pojos.PositiveTraits

@Component
class TraitMapper(
    private val modelMapper: ModelMapper
) {
    fun fromPosPojoToDto(pojo: PositiveTraits): PositiveTraitDto {
        return modelMapper.map(pojo, PositiveTraitDto::class.java)
    }

    fun fromNegPojoToDto(pojo: NegativeTraits): NegativeTraitDto {
        return modelMapper.map(pojo, NegativeTraitDto::class.java)
    }
}