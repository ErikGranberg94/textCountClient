# Compilation
This was implemented using `Java 24`, and can be compiled using `Maven`.

# How to run
This implementation accepts a list of arguments with the format: `ip:port`, but requires at least one server to be specified.

# Implementation
The client will create a thread for each server it connects to. Should it fail to connect to a server it will stop execution of only that thread and print an error message, the other threads will remain unaffected. I decided to make it possible for the client to connect to an arbitrary number of servers, rather than a set amount to keep my implementation flexible.

The client reads the output from the server one line at a time. The line is then split into a list of words, any special character found at the end of the word (excluding apostrophe) is removed, and the word is then counted (ignoring case).

Once all threads are done executing the client will output the top five most common words and the total amount of each of these words.
