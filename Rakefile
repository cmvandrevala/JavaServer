require 'rubygems'
require 'cucumber'
require 'cucumber/rake/task'

task :default => [:deploy]

desc "Deploy the server to an AWS instance"
task :deploy do
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'rm -f java-server-1.0-SNAPSHOT.jar'"
  sh "mvn clean"
  sh "mvn package"
  sh "scp target/java-server-1.0-SNAPSHOT.jar ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com:/home/ec2-user"
  puts "The jar file has been copied to the Amazon EC2 instance. Log in to start the server."
end

Cucumber::Rake::Task.new(:features) do |t|
  t.cucumber_opts = "features --format pretty"
end

desc "Run the JUnit tests using Maven."
task :maven do
  sh "mvn test"
end