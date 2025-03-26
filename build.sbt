val scala3Version = "3.3.5"

lazy val root = project
  .in(file("."))
  .settings(
    name                                   := "energy-use",
    scalaVersion                           := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "1.1.0" % Test
  )
