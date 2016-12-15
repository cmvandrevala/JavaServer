# JavaServer

[![Build Status](https://travis-ci.org/cmvandrevala/JavaServer.svg?branch=master)](https://travis-ci.org/cmvandrevala/JavaServer)

## Background

This Java HTTP server is my second major project as an apprentice at 8th Light. The objective of this assignment is to build a simple HTTP server, similar to [Sinatra](http://www.sinatrarb.com/) that can handle basic HTTP requests.

## Development

Apache Maven is used to organize the dependencies of this project. Additionally, this project includes the ```cob_spec``` repository as a git submodule. Thus, a few extra steps need to be taken when cloning the project and commiting to the master branch of the repository. A full description of handling git submodules can be found [here](https://git-scm.com/book/en/v2/Git-Tools-Submodules).

## Testing

This project uses [JUnit](http://junit.org/junit4/) for unit tests and [FitNesse](http://fitnesse.org/) for higher-level acceptance tests. The JUnit test suite can be run within the IntelliJ IDE using the ```Run``` command (````⌃⌥R```` on a Mac). Alternatively, the unit tests can be run using the Maven command ```mvn test```. The Fitnesse test suite (named cob_spec by the 8th Light community) is included as a submodule with the project. In order to run the Fitnesse test suite, navigate into the cob_spec directory and run:

```
java -jar fitnesse.jar -p 9090
```

Fitnesse will be started on localhost:9090. You can navigate to the ```HTTPTestSuite``` to see the specs mandated by 8th Light or the ```AugmentedTestSuite``` to see my more detailed unit tests. The ```Suite``` button at the top of the page runs the full test suite while the ```Test``` button runs an individual test when you navigate to it. Further details about FitNesse are provided in the included documentation.

## Build and Deployment

The IntelliJ IDE has been set up to build a jar file for the project when ```Build > Build Artifacts... > JavaServer.jar > Build``` is selected. Alternatively, Maven can build a jar file using the following commands: ```mvn clean; mvn package```. The output file is located at ```out/artifacts/JavaServer_jar/JavaServer.jar```.

A jar file for the server is deployed on an EC2 instance at IP address http://52.15.206.185:5000/ and can accept requests. A simple deploy script (deploy.sh) can be customized and used to copy a local jar file to the production machine.
