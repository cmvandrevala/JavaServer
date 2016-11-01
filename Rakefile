require 'rubygems'
require 'cucumber'
require 'cucumber/rake/task'
require 'net/ssh'

task :default => [:deploy]

desc "Deploy the server to an AWS instance"
task :deploy do
  sh "mvn clean"
  sh "mvn package"
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'rm -f java-server-1.0-SNAPSHOT.jar'"
  sh "scp target/java-server-1.0-SNAPSHOT.jar ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com:/home/ec2-user/"
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'nohup java -cp java-server-1.0-SNAPSHOT.jar JavaServerRunner &'"
end

Cucumber::Rake::Task.new(:features) do |t|
  t.cucumber_opts = "features --format pretty"
end

desc "Run the JUnit tests using Maven."
task :maven do
  sh "mvn test"
end

desc "Run fitnesse test suite"
task :fitnesse do
  Dir.chdir('cob_spec'){
    sh "java -jar fitnesse.jar -c 'HttpTestSuite?suite&format=text'"
  }
end

desc "Start an instance of the Java server on port 5000"
task :start_server do
  sh "mvn clean"
  sh "mvn package"
  sh "java -cp target/java-server-1.0-SNAPSHOT.jar JavaServerRunner &"
end
