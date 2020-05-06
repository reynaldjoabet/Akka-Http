import sbt.librarymanagement.ModuleID
import sbt._
object Dependencies {
  val scalaTest= "org.scalatest" %% "scalatest" % "3.1.1"
  val commonDependencies: Seq[ModuleID]=Seq(scalaTest % Test,
    "com.typesafe.akka" %% "akka-http"   % "10.1.11",
    "com.typesafe.akka" %% "akka-stream" % "2.5.26",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11"
  )
}
