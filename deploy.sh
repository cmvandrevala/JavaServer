#!/bin/bash

ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'rm java-server-1.0-SNAPSHOT.jar'

mvn clean
mvn package

scp target/java-server-1.0-SNAPSHOT.jar ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com:/home/ec2-user
ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'java -cp java-server-1.0-SNAPSHOT.jar JavaServerRunner &'

echo "The server has been successfully deployed!"