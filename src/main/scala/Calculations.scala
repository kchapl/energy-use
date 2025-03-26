package energyuse

import scala.io.Source
import java.time.Instant

case class EnergyReading(timestamp: Instant, reading: Double)

object Calculations {

  def readEnergyData(filepath: String): List[EnergyReading] = {
    val source = Source.fromFile(filepath)
    try
      source
        .getLines()
        .drop(1) // Skip header
        .map { line =>
          val Array(timestamp, reading) = line.split(",").map(_.trim)
          EnergyReading(Instant.parse(timestamp), reading.toDouble)
        }
        .toList
        .sortBy(_.timestamp)
    finally
      source.close()
  }

  def calculateEnergyUsage(readings: List[EnergyReading], start: Instant, end: Instant): Double =
    readings
      .filter(r => !r.timestamp.isBefore(start) && !r.timestamp.isAfter(end))
      .sliding(2)
      .map { case List(r1, r2) => r2.reading - r1.reading }
      .sum

}
