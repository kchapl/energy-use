package energyuse

import munit.FunSuite
import java.time.Instant
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

class CalculationsSpec extends FunSuite {

  test("calculate energy usage between two instants") {
    // Create test data
    val testData = """timestamp,reading
2024-01-01T00:00:00Z,100.0
2024-01-01T01:00:00Z,150.0
2024-01-01T02:00:00Z,225.0
2024-01-01T03:00:00Z,300.0"""

    val tempFile = Files.createTempFile("energy-readings", ".csv")
    Files.write(tempFile, testData.getBytes(StandardCharsets.UTF_8))

    val start = Instant.parse("2024-01-01T01:00:00Z")
    val end   = Instant.parse("2024-01-01T02:00:00Z")

    val readings = Calculations.readEnergyData(tempFile.toString)
    val usage    = Calculations.calculateEnergyUsage(readings, start, end)

    assertEquals(usage, 75.0)

    Files.delete(tempFile)
  }

  test("return 0.0 when no readings in time range") {
    val readings = List(
      EnergyReading(Instant.parse("2024-01-01T00:00:00Z"), 100.0),
      EnergyReading(Instant.parse("2024-01-01T01:00:00Z"), 150.0)
    )

    val start = Instant.parse("2024-01-02T00:00:00Z")
    val end   = Instant.parse("2024-01-02T01:00:00Z")

    assertEquals(Calculations.calculateEnergyUsage(readings, start, end), 0.0)
  }
}
