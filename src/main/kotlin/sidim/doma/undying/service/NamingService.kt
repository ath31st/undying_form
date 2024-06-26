package sidim.doma.undying.service

import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import sidim.doma.undying.exceptionhandler.exception.NamingException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.util.*

@Service
class NamingService {
    private val csvPath = "classpath:csv_data/"
    private val hideoutAdjectiveFile = csvPath + "hideout_adjectives.csv"
    private val hideoutNounFile = csvPath + "hideout_nouns.csv"
    private val caretakerFirstNameFile = csvPath + "caretaker_firstname.csv"
    private val caretakerLastNameFile = csvPath + "caretaker_lastname.csv"
    private val scholarFirstNameFile = csvPath + "scholar_firstname.csv"
    private val scholarLastNameFile = csvPath + "scholar_lastname.csv"
    private val recipeBookAdjectiveFile = csvPath + "recipe_book_adjectives.csv"
    private val recipeBookNounFile = csvPath + "recipe_book_nouns.csv"

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

    fun generateRecipeBookTitle(): String {
        val adjective = getRandomLine(recipeBookAdjectiveFile)
        val noun = getRandomLine(recipeBookNounFile)

        return "$adjective $noun"
    }

    fun generateHideoutName(): String {
        var adjective: String
        var noun: String

        do {
            adjective = getRandomLine(hideoutAdjectiveFile)
            noun = getRandomLine(hideoutNounFile)
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
