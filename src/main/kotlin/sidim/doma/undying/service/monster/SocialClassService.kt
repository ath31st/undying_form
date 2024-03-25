package sidim.doma.undying.service.monster

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import sidim.doma.undying.dto.NewSocialClassDto
import sidim.doma.undying.exceptionhandler.exception.SocialClassException
import sidim.doma.undying.generated.tables.pojos.SocialClasses
import sidim.doma.undying.repository.monster.SocialClassRepository

@Service
class SocialClassService(private val socialClassRepository: SocialClassRepository) {
    fun getSocialClasses(isActive: Boolean?): List<SocialClasses> {
        return socialClassRepository.getSocialClasses(isActive)
    }

    fun createSocialClass(dto: NewSocialClassDto): SocialClasses {
        checkExistsSocialClassByName(dto.name)
        return socialClassRepository.saveNewSocialClass(dto)
    }

    fun checkExistsSocialClassByName(name: String) {
        if (socialClassRepository.isSocialClassExistByName(name)) {
            throw SocialClassException("Social class with name $name already exists", HttpStatus.CONFLICT)
        }
    }
}