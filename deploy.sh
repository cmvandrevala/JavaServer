#!/usr/bin/env bash

ssh ec2-user@52.15.206.185 "pkill -f 'java -jar'"
ssh ec2-user@52.15.206.185 "rm -f ~/JavaServer.jar server.log"
scp out/artifacts/JavaServer_jar/JavaServer.jar ec2-user@52.15.206.185:~
ssh -n -f ec2-user@52.15.206.185 "sh -c 'nohup java -jar JavaServer.jar > /dev/null 2>&1 &'"
