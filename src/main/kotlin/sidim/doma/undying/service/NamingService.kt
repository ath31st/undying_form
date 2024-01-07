package sidim.doma.undying.service

import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.util.Locale
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import sidim.doma.undying.exceptionhandler.exception.NamingException

@Service
class NamingService {
    private val csvPath = "classpath:csv_data/"
    private val adjectiveFile = csvPath + "hideout_adjectives.csv"
    private val nounFile = csvPath + "hideout_nouns.csv"
    private val caretakerFirstNameFile = csvPath + "caretaker_firstname.csv"
    private val caretakerLastNameFile = csvPath + "caretaker_lastname.csv"
    private val scholarFirstNameFile = csvPath + "scholar_firstname.csv"
    private val scholarLastNameFile = csvPath + "scholar_lastname.csv"

    fun generateCaretakerFirstLastName(): String {
        val firstName = getRandomLine(caretakerFirstNameFile)
        val lastName = getRandomLine(caretakerLastNameFile)

        return "$firstName $lastName"
    }

    fun generateScholarFirstLastName(): String {
        val firstName = getRandomLine(scholarFirstNameFile)
        val lastName = getRandomLine(scholarLastNameFile)

        return "$firstName $lastName"
    }

    fun generateHideoutName(): String {
        var adjective: String
        var noun: String

        do {
            adjective = getRandomLine(adjectiveFile)
            noun = getRandomLine(nounFile)
        } while (checkGender(adjective) != checkGender(noun))

        return "${adjective.replaceFirstChar { it.titlecase(Locale.getDefault()) }} $noun"
    }

    private fun getRandomLine(path: String): String {
        var line = "empty line"
        try {
            val file: File = ResourceUtils.getFile(path)
            val reader = CSVReader(FileReader(file))
            val lines = reader.readAll()
            if (lines.isNotEmpty()) {
                val randomIndex = (0 until lines.size).random()
                line = lines[randomIndex][0]
            }
            reader.close()
        } catch (e: FileNotFoundException) {
            throw NamingException(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: CsvException) {
            throw NamingException(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return line
    }

    private fun checkGender(text: String): Gender {
        return when (text.last().lowercaseChar()) {
            'а', 'я', 'ь' -> Gender.FEMALE
            'е', 'о' -> Gender.NEUTER
            else -> Gender.MALE
        }
    }
}

private enum class Gender {
    MALE, FEMALE, NEUTER
}
