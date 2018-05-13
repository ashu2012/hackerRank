name := "riskdent"

version := "0.1"

scalaVersion := "2.12.4"


resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)


libraryDependencies += {
  val version = scalaBinaryVersion.value match {
    case "2.10" => "1.0.3"
    case _ ⇒ "1.1.0"
  }
  "com.lihaoyi" % "ammonite" % version % "test" cross CrossVersion.full
}

sourceGenerators in Test += Def.task {
  val file = (sourceManaged in Test).value / "amm.scala"
  IO.write(file, """object amm extends App { ammonite.Main.main(args) }""")
  Seq(file)
}.taskValue

exportJars := true

mainClass in Compile := Some("riskdent")
mainClass in(Compile, run) := Some("riskdent")
mainClass in(Compile, packageBin) := Some("riskdent")
mainClass in assembly := Some("riskdent")
