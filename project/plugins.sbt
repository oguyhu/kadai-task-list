addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.2")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.11.0")

//プラグインがあるリポジトリの場所の追加
resolvers += "Flyway" at "https://davidmweber.github.io/flyway-sbt.repo"

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")
