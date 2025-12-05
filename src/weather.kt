import java.io.File

/************************************************************
 * Name: Juan Aguayo
 * Date: 11/13/2024
 * Assignment: Weather Hashmap Database Assignment
 * Class Number: 282
 * Description: Create a menu driven program to interact with a database of Spokane weather records
 ************************************************************/
data class Weather(
    val month: Int,
    val day: Int,
    val sunrise: String,
    val sunset: String,
    val meanTemp: Int,
    val avgHighTemp: Int,
    val avgLowTemp: Int,
    val recHighTemp: Int,
    val recHighYear: Int,
    val recLowTemp: Int,
    val recLowYear: Int,
    val avgPrecip: Double
)

val WeatherDBFile = "src/weather.db"


fun main() {

    val weatherDB: MutableMap<String, Weather> = mutableMapOf()
    val headers = mutableListOf<String>()

    val lines = File(WeatherDBFile).readLines()
    var firstLine = true

    for (line in lines) {
        if (firstLine) {
            firstLine = false
        } else {
            val record = line.split(",")
            val oneWeatherRecord = Weather(
                month = record[0].toInt(),
                day = record[1].toInt(),
                sunrise = record[2],
                sunset = record[3],
                meanTemp = record[4].toInt(),
                avgHighTemp = record[5].toInt(),
                avgLowTemp = record[6].toInt(),
                recHighTemp = record[7].toInt(),
                recHighYear = record[8].toInt(),
                recLowTemp = record[9].toInt(),
                recLowYear = record[10].toInt(),
                avgPrecip = record[11].toDouble()
            )
            val key = "${record[0]}-${record[1]}"
            weatherDB[key] = oneWeatherRecord
        }
    }

    var userChoice = readLine()!!.toInt()
    do {
        println("1. Print All Records")
        println("2. Print Only Records from a single month")
        println("3. Print Record with Highest Recorded Temperature")
        println("4. Print Record with Lowest Recorded Temperature")
        println("5. Print Record with Highest Recorded Temperature from a single month")
        println("6. Print Record with Lowest Recorded Temperature from a single month")
        println("7. Print Average of all precipitation from a single month")
        println("8. Quit")
        userChoice = readLine()!!.toInt()




        when (userChoice) {
            1 -> printAllRecords(weatherDB)
            2 -> {
                println("Enter month (1-12): ")
                val month = readLine()!!.toInt()
                printRecordsFromMonth(month, weatherDB)
            }
            3 -> highestRecordedTemperature(weatherDB)
            4 -> lowestTempRecord(weatherDB)
            5 -> {
                println("Enter month (1-12): ")
                val month = readLine()!!.toInt()
                highestTempInMonth(month, weatherDB)
            }
            6 -> {
                println("Enter month (1-12): ")
                val month = readLine()!!.toInt()
                lowestTempInMonth(month, weatherDB)
            }
            7 -> {
                println("Enter month (1-12): ")
                val month = readLine()!!.toInt()
                averagePrecipitationInMonth(month, weatherDB)
            }
        }
    } while (userChoice != 8)


}
//1
fun printAllRecords(weatherDB: Map<String, Weather>) {
    for ((_, weather) in weatherDB) {
        println(weather)
    }
}
//2
fun printRecordsFromMonth(month: Int, weatherDB: Map<String, Weather>) {
    val records = weatherDB.values.filter { it.month == month }
    if (records.isNotEmpty()) {
        records.forEach { println(it) }
    } else {
        println("No records found for month $month.")
    }
}
//3
fun highestRecordedTemperature(weatherDB: Map<String, Weather>) {
    val maxRecord = weatherDB.values.maxByOrNull { it.recHighTemp }
    println("Record with Highest Recorded Temperature: $maxRecord")
}
//4
fun lowestTempRecord(weatherDB: Map<String, Weather>) {
    val minRecord = weatherDB.values.minByOrNull { it.recLowTemp }
    if (minRecord != null) {
        println("Record with the lowest recorded temperature: $minRecord")
    } else {
        println("No records found.")
    }
}
//5
fun highestTempInMonth(month: Int, weatherDB: Map<String, Weather>) {
    val recordsInMonth = weatherDB.values.filter { it.month == month }
    val maxTempRecord = recordsInMonth.maxByOrNull { it.recHighTemp }
    if (maxTempRecord != null) {
        println("Record with the highest recorded temperature in month $month: $maxTempRecord")
    } else {
        println("No records found for month $month.")
    }
}
//6
fun lowestTempInMonth(month: Int, weatherDB: Map<String, Weather>) {
    val records = weatherDB.values.filter { it.month == month }
    val minTempRecord = records.minByOrNull { it.recLowTemp }
    if (minTempRecord != null) {
        println("Record with the lowest temperature in month $month: $minTempRecord")
    } else {
        println("No records found for month $month.")
    }
}
//7
fun averagePrecipitationInMonth(month: Int, weatherDB: Map<String, Weather>) {
    val records = weatherDB.values.filter { it.month == month }
    val averagePrecipitation = records.map { it.avgPrecip }.average()
    if (records.isNotEmpty()) {
        println("Average precipitation for month $month: $averagePrecipitation")
    } else {
        println("No records found for month $month.")
    }
}
