// xsbt-web-plugin
resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"  

libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-web-plugin" % "0.11.1-0.2.10")

// sbteclipse
resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse" % "1.5.0")
