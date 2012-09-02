Scaladin makes easier to use [Vaadin Framework](https://vaadin.com) with [Scala](http://www.scala-lang.org/) programming language. It's a wrapper library that provides a pure Scala API for Vaadin Framework. 

[![Build Status](https://secure.travis-ci.org/henrikerola/scaladin.png?branch=2.1)](http://travis-ci.org/henrikerola/scaladin)
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


## Authors

Scaladin is developed by Risto Yrjänä and Henri Kerola. Also, thanks to Henri Muurimaa for great ideas and feedback!

## License

Scaladin is licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).

## Developing the library

Wiki has a page about [Developing the Library](https://github.com/henrikerola/scaladin/wiki/Developing-the-Library).
