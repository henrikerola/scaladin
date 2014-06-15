// sbt-vaadin-plugin
resolvers += "sbt-vaadin-plugin repo" at "http://henrikerola.github.io/repository/releases"

addSbtPlugin("org.vaadin.sbt" % "sbt-vaadin-plugin" % "1.0.0")

// sbteclipse
resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")