# jaxrsdemo
A mock project. Manage a list of persons. 
Used frameworks: jax-rs, gson, jquery, jquery-ui, jquery-datatables
Can be run as standalone application with embedded Jetty server, or as a regular war in any J2EE container.

## building
Run ant in /java

## running
- build and deploy /release/jaxrsdemo.war, go to http://localhost/<app name>/person.html
- or build and run /bin/run.sh, go to http://localhost:8080/person.html

## Guide to java packages
- bwf.jaxrsdemo.api -- datamodel objects and DAO interfaces (Person, Db)
- bwf.jaxrsdemo.app -- Main class for standalone app
- bwf.jaxrsdemo.db -- Mock Db implementation
- bwf.jaxrsdemo.rest -- JAX-RS endpoint and application class

## html/js files
- wwwroot/person.html -- person manager; has links to jquery CDN
- wwwroot/api.js -- database access functions
- wwwroot/util.js -- misc functions

