bookstore-starter-flow
==============

Template for a full-blown Vaadin application that only requires a Servlet 3.0 container to run (no other JEE dependencies).


Project Structure
=================

The project consists of the following three modules:

- parent project: common metadata and configuration
- bookstore-starter-flow-ui: main application module
- bookstore-starter-flow-backend: POJO classes and mock services being used in ui

Workflow
========

To compile the entire project, run `mvn install` in the parent project.

All basic workflow steps:

- compiling the whole project
  - run `mvn install` in parent project
- developing the application
  - edit code in the ui module
  - run `mvn jetty:run` in ui module
  - open http://localhost:8080/
- testing the production mode war
  - run `mvn jetty:run-war` in the ui module
- creating a production mode war
  - run `mvn -Pproduction package` in the ui module or in the parent module
- testing the production mode war
  - run `mvn -Pproduction jetty:run-war` in the ui module

Using Vaadin pre-releases
-------------------------

If Vaadin pre-releases are not enabled by default, use the Maven parameter
`-P vaadin-prerelease` or change the activation default value of the profile in pom.xml .
