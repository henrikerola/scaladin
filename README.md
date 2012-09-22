Scaladin makes easier to use [Vaadin Framework](https://vaadin.com) with [Scala](http://www.scala-lang.org/) programming language. It's a wrapper library that provides a pure Scala API for Vaadin Framework. 

[![Build Status](https://secure.travis-ci.org/henrikerola/scaladin.png?branch=3.0)](http://travis-ci.org/henrikerola/scaladin)
## How to use it?

The JAR file and a Maven dependency can be found from the [Vaadin Directory](http://vaadin.com/addon/scaladin).

The following listing shows what Scaladin code looks like:

    package com.example

    import vaadin.scala._

    class ScaladinExampleApplication extends Application("Scaladin Example") {
      override val main = new VerticalLayout {
        add(new Button {
          caption = "Click me!"
          icon = new ThemeResource("../runo/icons/16/globe.png")
          clickListeners += { mainWindow.showNotification("Hello World!") }
        })
      }
    }

The easiest way to test Scaladin is to use Risto's [giter8 template](https://github.com/ripla/vaadin-scala.g8) that generates a sbt project:

    > g8 ripla/vaadin-scala
    <answer questions, enter for defaults>
    > cd <project dir>
    > sbt container:start ~aux-compile

After the above commands you have a working Scaladin application running on http://localhost:8080. You can even edit the generated Application class and changes are affected to the browser. In order to import your project into Eclipse, you have to say:

    > sbt eclipse

That generates needed Eclipse configuration files into the project.

## Authors

Scaladin is developed by Risto Yrjänä and Henri Kerola. Also, thanks to Henri Muurimaa for great ideas and feedback!

## License

Scaladin is licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).

## Developing the library

Wiki has a page about [Developing the Library](https://github.com/henrikerola/scaladin/wiki/Developing-the-Library).
