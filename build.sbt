import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

name := "universal-recommender"

name := "template-scala-parallel-universal-recommendation"
scalaVersion := "2.10.6"
version := "0.5.0"

organization := "com.actionml"

val mahoutVersion = "0.13.0"

libraryDependencies ++= Seq(
  "org.apache.hbase" % "hbase-client" % "1.3.1",
  "org.elasticsearch" % "elasticsearch" % "5.3.2",
  //   "org.apache.lucene" % "lucene-core" % "6.4.2",
  "org.apache.predictionio" %% "apache-predictionio-core" % "0.11.0-incubating" % "provided"
    exclude("com.chuusai", "shapeless_2.10")
    exclude("io.spray", "spray-routing_2.10")
    excludeAll ExclusionRule("com.chuusai", "shapeless"),
  "org.apache.predictionio" %% "apache-predictionio-data-elasticsearch1" % "0.11.0-incubating" % "provided"
    exclude("com.chuusai", "shapeless_2.10")
    exclude("io.spray", "spray-routing_2.10")
    excludeAll ExclusionRule("com.chuusai", "shapeless"),
  "org.apache.spark" %% "spark-core" % "1.6.3" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.6.3" % "provided",
  "org.xerial.snappy" % "snappy-java" % "1.1.1.7",
  // Mahout's Spark libs
  "org.apache.mahout" %% "mahout-math-scala" % mahoutVersion,
  "org.apache.mahout" %% "mahout-spark" % mahoutVersion
    exclude("org.apache.spark", "spark-core_2.10"),
  "org.apache.mahout"  % "mahout-math" % mahoutVersion,
  "org.apache.mahout"  % "mahout-hdfs" % mahoutVersion
    exclude("com.thoughtworks.xstream", "xstream")
    exclude("org.apache.hadoop", "hadoop-client"),
  // other external libs
  "com.thoughtworks.xstream" % "xstream" % "1.4.4"
    exclude("xmlpull", "xmlpull"),
  "org.elasticsearch" % "elasticsearch-spark_2.10" % "2.1.2"
    exclude("org.apache.spark", "spark-catalyst_2.10")
    exclude("org.apache.spark", "spark-sql_2.10"),
  "org.json4s" %% "json4s-native" % "3.2.10")
  //  .map(_.exclude("org.apache.lucene","lucene-core"))
  .map(_.exclude("org.apache.lucene","lucene-analyzers-common"))

resolvers += Resolver.mavenLocal

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(DanglingCloseParenthesis, Prevent)
  .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)

assemblyMergeStrategy in assembly := {
  case "plugin.properties" => MergeStrategy.discard
  case PathList(ps @ _*) if ps.last endsWith "package-info.class" =>
    MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
