require 'net/http'

Given(/^the java server is running$/) do
  begin
    response = Net::HTTP.get('localhost', '/', 5000)
  rescue
    puts "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    puts "~~~ The Java server is not running. You can turn on the Java server ~~~"
    puts "~~~~~~ by running \"java -cp /path/to/jar/file JavaServerRunner\". ~~~~~~"
    puts "~~~~~~~~~~~~~~~~~~~~~~~~~~ Have a great day! ~~~~~~~~~~~~~~~~~~~~~~~~~~"
    puts "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    raise
  end
end

Given(/^a hard coded page has a url \/foo and body text foo$/) do
end

Given(/^the user performs a GET request on \/foo$/) do
  @get_response = Net::HTTP.get_response('localhost', '/foo', 5000)
end

Then(/^the user should receive an HTTP code of 200$/) do
  expect(@get_response.code).to eq "200"
end

Then(/^the user should receive an HTTP message of OK$/) do
  expect(@get_response.message).to eq "OK"
end

Then(/^the user should recieve the body text \"foo\"$/) do
  expect(@get_response.body).to eq "foo"
end

Given(/^the server does not have a page with URL \/bar$/) do
end

Given(/^the user performs a GET request on \/bar$/) do
  @missing_response = Net::HTTP.get_response('localhost', '/bar', 5000)
end

Then(/^the user should receive an HTTP message of Not Found$/) do
  expect(@missing_response.message).to eq "Not Found"
end

Then(/^the user should receive an HTTP code of 404$/) do
  expect(@missing_response.code).to eq "404"
end