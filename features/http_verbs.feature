Feature: HTTP Verbs

Scenario: User makes a GET request for a page on the server
Given the java server is running
And a hard coded page has a url /foo and body text foo
And the user performs a GET request on /foo
Then the user should receive an HTTP code of 200
And the user should receive an HTTP message of OK
And the user should recieve the body text "foo"

Scenario: User makes a GET request for a missing page on the server
Given the java server is running
And the server does not have a page with URL /bar
And the user performs a GET request on /bar
Then the user should receive an HTTP code of 404
And the user should receive an HTTP message of Not Found