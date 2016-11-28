task :default => [:test]

desc "Deploy the server to an AWS instance"
task :deploy do
  sh "mvn clean"
  sh "mvn package"
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'cd JavaServer; git pull'"
  sh "ssh ec2-user@ec2-52-15-103-218.us-east-2.compute.amazonaws.com 'nohup java -jar JavaServer/out/artifacts/ &'"
end

desc "Run all tests"
task :test do
  sh "mvn clean"
  sh "mvn package"
  sh "(cd cob_spec && java -jar fitnesse.jar -c 'PassingTestSuite?suite&format=text')"
end
