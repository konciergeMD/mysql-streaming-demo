name := "mysql-streaming-demo"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.avaje.ebeanorm" % "avaje-ebeanorm" % "3.3.3",
  "mysql" % "mysql-connector-java" % "5.1.30"
)

play.Project.playJavaSettings
