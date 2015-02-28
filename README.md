# JerseySpringAnnotation

Sample project with Jersey framework and Spring JDBC together using annotations.

MySQL is database used in project, but can be easily changed with different DB dependency in pom.xml. If DB dependency is changed, you have to change applicationContext.xml accordingly.

Run with mvn package and it will generate .war file that can be deployed to server container.

You can also run it via IDE (e.g. Intellij with Server configuration).

To see it in action go to http://localhost:8082/myresource
