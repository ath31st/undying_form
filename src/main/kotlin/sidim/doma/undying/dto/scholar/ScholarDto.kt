package sidim.doma.undying.dto.scholar

data class ScholarDto(
    val name: String,
    val age: Int,
    val physicalHealth: Int,
    val mentalHealth: Int,
    val experience: Int,
    val alchemy: Int,
    val biology: Int,
    val engineering: Int,
    val successfulExperiments: Int,
    val educationId: Int,
    val specializationId: Int?,
    val hideoutId: Long,
    val monsterId: Long,
)
