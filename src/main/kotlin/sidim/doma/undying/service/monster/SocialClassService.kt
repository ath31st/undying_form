package sidim.doma.undying.service.monster

import org.springframework.stereotype.Service
import sidim.doma.undying.dto.NewSocialClassDto
import sidim.doma.undying.generated.tables.pojos.SocialClasses
import sidim.doma.undying.repository.monster.SocialClassRepository

@Service
class SocialClassService(private val socialClassRepository: SocialClassRepository) {
    fun createSocialClass(dto: NewSocialClassDto): SocialClasses {
        return socialClassRepository.saveNewSocialClass(dto)
    }
}