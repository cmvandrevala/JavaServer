require 'rubygems'
require 'cucumber'
require 'cucumber/rake/task'
require 'net/ssh'

task :default => [:test]

desc "Deploy the server to an AWS instance"
task :deploy do
  sh "mvn clean"
  sh "mvn package"
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'rm -f java-server-1.0-SNAPSHOT.jar'"
  sh "scp out/artifacts/JavaServer_jar/java-server-1.0-SNAPSHOT.jar ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com:/home/ec2-user/"
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'nohup java -jar java-server-1.0-SNAPSHOT.jar &'"
end

desc "Run all tests"
task :test do
  sh "mvn test"
  Dir.chdir('cob_spec'){
    sh "java -jar fitnesse.jar -c 'AugmentedTestSuite?suite&format=text'"
  }
  Dir.chdir('cob_spec'){
    sh "java -jar fitnesse.jar -c 'PassingTestSuite?suite&format=text'"
  }
end
