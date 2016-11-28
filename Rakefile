task :default => [:test]

desc "Deploy the server to an AWS instance"
task :deploy do
  sh "./deploy.sh"
end

desc "Run all tests"
task :test do
  sh "mvn clean"
  sh "mvn package"
  sh "(cd cob_spec && mvn package)"
  sh "(cd cob_spec && java -jar fitnesse.jar -c 'PassingTestSuite?suite&format=text')"
end
