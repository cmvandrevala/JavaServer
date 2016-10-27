Feature: HTTP Verbs

Scenario: User makes a GET request for the index page
Given the java server is running
And the index page has a URL /
And the user performs a GET request on /
Then the user should receive an HTTP code of 200
And the user should receive an HTTP message of OK

Scenario: User makes a GET request for a page
Given the java server is running
And a hard coded page has a URL /foo and body text "foo"
And the user performs a GET request on /foo
Then the user should receive an HTTP code of 200
And the user should receive an HTTP message of OK
And the user should receive the body text "foo"

Scenario: User makes a GET request for a missing page
Given the java server is running
And the server does not have a page with URL /bar
And the user performs a GET request on /bar
Then the user should receive an HTTP code of 404
And the user should receive an HTTP message of Not Found


Scenario: User makes a HEAD request for the index page
Given the java server is running
And the index page has a URL /
And the user performs a HEAD request on /
Then the user should receive an HTTP code of 200
And the user should receive an HTTP message of OK
And the user should receive no body text

Scenario: User makes a HEAD request for a page
Given the java server is running
And a hard coded page has a URL /foo and body text "foo"
And the user performs a HEAD request on /foo
Then the user should receive an HTTP code of 200
And the user should receive an HTTP message of OK
And the user should receive no body text

Scenario: User makes a HEAD request for a missing page
Given the java server is running
And the server does not have a page with URL /bar
And the user performs a HEAD request on /bar
Then the user should receive an HTTP code of 404
And the user should receive an HTTP message of Not Found
And the user should receive no body text