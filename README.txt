

For more information about the project and a published add-on: http://vaadin.com/addon/scala-wrappers


Needed Tools
------------

* Eclipse
* Scala IDE for Eclipse (http://www.scala-ide.org/)
* SBT 0.11 (https://github.com/harrah/xsbt/wiki)

Generating Eclipse project files
--------------------------------

$ sbt eclipse

OR

$ sbt
> eclipse
[info] Successfully created Eclipse project files. Please select the appropriate Eclipse plugin for Scala 2.9.0-1!
>

After the above commands, it's possible to import scala-wrappers and scala-wrappers-demo projects into Eclipse.

Running the demo application
----------------------------

$ sbt "project demo" ~container:start

OR

$ sbt
> project demo
> ~container:start

Now the application is accessible in http://localhost:8080. ~ means that jetty will redeploy the application when changes are detected.
You may also need to increase the amount of permgen memory available to SBT. The simplest way to do this is to edit the script that launches SBT. 

Packaging jar
-------------

$ sbt package


Packaging war
-------------

$ sbt "project demo" package-war

