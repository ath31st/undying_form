package sidim.doma.undying.model

data class Storage(val storageId: Long, val capacity: Int, val bodyParts: MutableList<BodyPart>)