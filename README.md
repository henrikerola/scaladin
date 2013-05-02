# Scaladin

Scaladin makes easier to use [Vaadin Framework](https://vaadin.com) with [Scala](http://www.scala-lang.org/) programming language. It's a wrapper library that provides a pure Scala API for Vaadin Framework. 

[![Build Status](https://secure.travis-ci.org/henrikerola/scaladin.png?branch=3.0)](http://travis-ci.org/henrikerola/scaladin)

## Discussion

[Vaadin Forum](https://vaadin.com/forum) is the place for discussion about Scaladin. You can use the existing [Vaadin & Scala](https://vaadin.com/forum#!/thread/530127) thread or create your own.

## How to use it?

Scaladin 2 is for Vaadin 6 and Scaladin 3 for Vaadin 7. Scaladin 3 is still under development and not yet released but you can build your own version from sources. Scaladin 3 requires Scala 2.10. The JAR file and a Maven dependency for Scaladin 2 and Scala 2.9 can be found from the [Vaadin Directory](http://vaadin.com/addon/scaladin).

The following listing shows what Scaladin 3 code looks like:

    package com.example

    import vaadin.scala._

    class ScaladinExampleUI extends UI {
        content = new Button {
            caption = "Click me!"
            icon = ThemeResource("../runo/icons/16/globe.png")
            clickListeners += Notification.show("Hello World!")
        }
    }

The easiest way to test Scaladin 2 is to use Risto's [giter8 template](https://github.com/ripla/vaadin-scala.g8) that generates a sbt project:

    > g8 ripla/vaadin-scala
    <answer questions, enter for defaults>
    > cd <project dir>
    > sbt container:start ~aux-compile

After the above commands you have a working Scaladin application running on http://localhost:8080. You can even edit the generated Application class and changes are affected to the browser. In order to import your project into Eclipse, you have to say:

    > sbt eclipse

That generates needed Eclipse configuration files into the project.

## Credits

The following people have contributed code or ideas to Scaladin:

 * Matti Heinola
 * Henri Kerola
 * Henri Muurimaa
 * Risto Yrjänä

## License

Scaladin is licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).

## Developing the library

Wiki has a page about [Developing the Library](https://github.com/henrikerola/scaladin/wiki/Developing-the-Library).
