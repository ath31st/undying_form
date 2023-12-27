package sidim.doma.undying.service

import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.util.Locale
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils

@Service
class NamingService {
    private val adjectivePath = "classpath:csv_data/adjectives.csv"
    private val nounPath = "classpath:csv_data/nouns.csv"

    fun generateHideoutName(): String {
        var adjective: String
        var noun: String

        do {
            adjective = getRandomLine(adjectivePath)
            noun = getRandomLine(nounPath)
        } while (checkGender(adjective) != checkGender(noun))

        return "${adjective.replaceFirstChar { it.titlecase(Locale.getDefault()) }} $noun"
    }

    private fun getRandomLine(path: String): String {
        var line = ""
        try {
            val file: File = ResourceUtils.getFile(path)
            val reader = CSVReader(FileReader(file))
            val lines = reader.readAll()
            if (lines.isNotEmpty()) {
                val randomIndex = (0 until lines.size).random()
                line = lines[randomIndex][0]
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: CsvException) {
            e.printStackTrace()
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
