package sidim.doma.undying.util

import java.security.SecureRandom
import java.util.Date
import org.springframework.stereotype.Service

@Service
class GeneratorRandomValuesUtil {
    fun generateRandomValues(count: Int, minValue: Int, targetSum: Int): List<Int> {
        val randomValues = mutableListOf<Int>()
        for (i in 1 until count) {
            var randomValue: Int
            do {
                randomValue = generateRandomInteger(minValue, targetSum - randomValues.sum())
            } while (randomValue > targetSum / count)
            randomValues.add(randomValue)
        }
        randomValues.add(targetSum - randomValues.sum())

        return randomValues.shuffled()
    }

    /**
     * Generates a random integer between the specified minimum and maximum values (inclusive).
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random integer within the specified range.
     */
    fun generateRandomInteger(min: Int, max: Int): Int {
        val random = SecureRandom()
        random.setSeed(Date().time)
        return random.nextInt((max - min) + 1) + min
    }
}
