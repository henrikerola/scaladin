# Scaladin

Scaladin makes easier to use [Vaadin Framework](https://vaadin.com) with [Scala](http://www.scala-lang.org/) programming language. It's a wrapper library that provides a pure Scala API for Vaadin Framework. 

[![Build Status](https://secure.travis-ci.org/henrikerola/scaladin.png?branch=3.2)](http://travis-ci.org/henrikerola/scaladin)

## Discussion

[Vaadin Forum](https://vaadin.com/forum) is the place for discussion about Scaladin. You can use the existing [Vaadin & Scala](https://vaadin.com/forum#!/thread/530127) thread or create your own.

## How to use it?

Scaladin 3.2 requires Vaadin 7.5 and Scala 2.11.

1\. Add dependencies to Scaladin and Vaadin to your Scala project (using [sbt](http://www.scala-sbt.org/) here):

```sbt
resolvers += "Scaladin Snapshots" at "http://henrikerola.github.io/repository/snapshots/"

libraryDependencies ++= Seq(
  "org.vaadin.addons" %% "scaladin" % "3.2-SNAPSHOT",
  "com.vaadin" % "vaadin-server" % "7.5.10",
  "com.vaadin" % "vaadin-client-compiled" % "7.5.10",
  "com.vaadin" % "vaadin-themes" % "7.5.10"
)
```

2\. Scaladin applications are deployed as servlets, during the development time you could use [xsb-web-plugin](http://earldouglas.com/projects/xsbt-web-plugin/).

3\. Define a servlet and a Scaladin UI:

```scala
package com.example

import javax.servlet.annotation.WebServlet
import vaadin.scala._
import vaadin.scala.server.ScaladinServlet

@WebServlet(urlPatterns = Array("/*"))
class Servlet extends ScaladinServlet(
  ui = classOf[HelloWorldUI]
)

class HelloWorldUI extends UI(theme = ValoTheme.ThemeName) {

  content = new VerticalLayout { layout =>
    margin = true
    spacing = true

    addComponent(new Label {
      value = "Hello World!"
      styleNames += ValoTheme.LabelH1
    })

    addComponent(Button("Click Me", { e =>
      layout.addComponent(Label("Thanks for clicking!"))
    }))
  }
}
```

4\. If you use xsbt-web-plugin, start a web server by saying `sbt ~jetty:start` and your Scaladin application should be available at http://localhost:8080:

![Screenshot](http://henrikerola.github.io/images/scaladin-helloworld.png)

## License

Scaladin is licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).
