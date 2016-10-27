require 'net/http'

Given(/^the java server is running$/) do
  begin
    @http = Net::HTTP.new("localhost", 5000).start
  rescue
    puts "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    puts "~~~ The Java server is not running. You can turn on the Java server ~~~"
    puts "~~~~~~ by running \"java -cp /path/to/jar/file JavaServerRunner\". ~~~~~~"
    puts "~~~~~~~~~~~~~~~~~~~~~~~~~~ Have a great day! ~~~~~~~~~~~~~~~~~~~~~~~~~~"
    puts "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    raise
  end
end

Given(/^the index page has a URL \/$/) do
end

Given(/^a hard coded page has a URL \/foo and body text "foo"$/) do
end

Given(/^the server does not have a page with URL \/bar$/) do
end

Given(/^the user performs a GET request on \/$/) do
  request = Net::HTTP::Get.new("/")
  @response = @http.request(request)
end

Given(/^the user performs a GET request on \/foo$/) do
  request = Net::HTTP::Get.new("/foo")
  @response = @http.request(request)
end

Given(/^the user performs a GET request on \/bar$/) do
  request = Net::HTTP::Get.new("/bar")
  @response = @http.request(request)
end

Given(/^the user performs a HEAD request on \/$/) do
  @response = @http.send_request('HEAD', '/')
end

Given(/^the user performs a HEAD request on \/foo$/) do
  @response = @http.send_request('HEAD', '/foo')
end

Given(/^the user performs a HEAD request on \/bar$/) do
  @response = @http.send_request('HEAD', '/bar')
end

Then(/^the user should receive an HTTP code of 200$/) do
  expect(@response.code).to eq "200"
end

Then(/^the user should receive an HTTP code of 404$/) do
  expect(@response.code).to eq "404"
end

Then(/^the user should receive an HTTP message of OK$/) do
  expect(@response.message).to eq "OK"
end

Then(/^the user should receive an HTTP message of Not Found$/) do
  expect(@response.message).to eq "Not Found"
end

Then(/^the user should receive the body text "foo"$/) do
  expect(@response.body).to eq "foo"
end

Then(/^the user should receive no body text$/) do
  expect(@response.body).to eq nil
end












