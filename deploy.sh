ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com "pkill -f 'java -jar'"
ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com '(cd JavaServer && git fetch --all)'
ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com '(cd JavaServer && git reset --hard origin/master)'
ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'rm -rf JavaServer/src/test'
ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com '(cd JavaServer && mvn clean)'
ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com '(cd JavaServer && mvn package)'
ssh -n -f ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com "sh -c 'cd JavaServer; nohup java -jar out/artifacts/JavaServer_jar/JavaServer.jar > /dev/null 2>&1 &'"
