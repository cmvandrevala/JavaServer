#!/usr/bin/env bash

ssh ec2-user@52.15.176.188 "pkill -f 'java -jar'"
ssh ec2-user@52.15.176.188 '(cd JavaServer && git fetch --all)'
ssh ec2-user@52.15.176.188 '(cd JavaServer && git reset --hard origin/master)'
ssh ec2-user@52.15.176.188 'rm -rf JavaServer/src/test'
ssh ec2-user@52.15.176.188 '(cd JavaServer && mvn clean)'
ssh ec2-user@52.15.176.188 '(cd JavaServer && mvn package)'
ssh -n -f ec2-user@52.15.176.188 "sh -c 'cd JavaServer; nohup java -jar out/artifacts/JavaServer_jar/JavaServer.jar > /dev/null 2>&1 &'"
