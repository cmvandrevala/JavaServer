require 'rubygems'
require 'cucumber'
require 'cucumber/rake/task'

task :default => [:test]

desc "Deploy the server to an AWS instance"
task :deploy do
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'rm -f java-server-1.0-SNAPSHOT.jar'"
  sh "mvn clean"
  sh "mvn package"
  sh "scp target/java-server-1.0-SNAPSHOT.jar ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com:/home/ec2-user"
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'nohup java -cp java-server-1.0-SNAPSHOT.jar JavaServerRunner &'"
  puts "The server has been successfully deployed!"
end

desc "Run the Maven and Cucumber tests"
task :test => [:maven_tests, :features] do
  puts "Tests have been run."
end


Cucumber::Rake::Task.new(:features) do |t|
  t.cucumber_opts = "features --format pretty"
end

task :maven_tests do
  sh "mvn test"
end