package energyuse

import java.time.Instant

@main def run(args: String*): Unit =
  if (args.length == 3) {
    val readings = Calculations.readEnergyData(args(0))
    val start    = Instant.parse(args(1))
    val end      = Instant.parse(args(2))
    val usage    = Calculations.calculateEnergyUsage(readings, start, end)
    println(usage)
  }
