# Fullstack Challenge

A full-stack technical challenge starter. Mostly generated using Luminus version "3.91".

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

You can package the project into a Docker container by running:

    lein uberjar
    docker build . -t fcs

Then to run it with:

    docker run -p 8080:3000 fcs:latest

The app will be available at http://localhost:8080

Alternatiely, to start a web server for the application in dev mode, run:

    lein run

To start the frontend app server, run:

    lein figwheel

## Testing

To run the included backend tests (hopefuly I wrote some ¯\\\_(ツ)\_/¯) run the following command:

    lein test

To run the included frontend tests in Firefox, run:

    lein with-profile test doo firefox-headless once

You might need to install karma and a bunch of its plugins:

    npm install --global karma karma-cljs-test karma-firefox-launcher
